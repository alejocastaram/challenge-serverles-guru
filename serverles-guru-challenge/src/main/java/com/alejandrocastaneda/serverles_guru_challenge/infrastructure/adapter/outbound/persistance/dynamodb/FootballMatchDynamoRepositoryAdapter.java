package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.entity.FootballMatchDynamoEntity;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.mapper.FootballMatchDynamoMapper;
import com.alejandrocastaneda.serverles_guru_challenge.application.util.MatchTittleGenerator;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Repository
public class FootballMatchDynamoRepositoryAdapter implements FootballMatchRepository {
    private static final String INDEX = "matchId-index";
    private final DynamoDbAsyncTable<FootballMatchDynamoEntity> table;

    public FootballMatchDynamoRepositoryAdapter(DynamoDbAsyncClient client) {
        DynamoDbEnhancedAsyncClient enhancedClient = DynamoDbEnhancedAsyncClient.builder()
                        .dynamoDbClient(client)
                        .build();

        this.table = enhancedClient.table("football-match",
                TableSchema.fromBean(FootballMatchDynamoEntity.class));
    }

    @Override
    public Mono<Void> create(FootballMatch footballMatch) {
        FootballMatchDynamoEntity footballMatchDynamoEntity = FootballMatchDynamoMapper.toDynamoEntity(footballMatch);
        return Mono.fromFuture(table.putItem(footballMatchDynamoEntity));
    }

    @Override
    public Mono<FootballMatch> get (String localTeam, String awayTeam, String matchDate) {

        return getDynamoEntity(localTeam, awayTeam, matchDate)
                .map(FootballMatchDynamoMapper::toDomain);
    }

    private Mono<FootballMatchDynamoEntity> getDynamoEntity(String localTeam, String awayTeam, String matchDate) {
        DynamoDbAsyncIndex<FootballMatchDynamoEntity> index =
                table.index(INDEX);
        QueryConditional query = getQuery(localTeam, awayTeam, matchDate);

        return Flux.from(index.query(query))
                .flatMapIterable(Page::items)
                .next();
    }

    private  QueryConditional getQuery (String localTeam, String awayTeam, String matchDate) {
        String sk = MatchTittleGenerator.generate(localTeam, awayTeam, matchDate);

        return QueryConditional
                .keyEqualTo(Key.builder()
                        .partitionValue(sk)
                        .build());
    }

    @Override
    public Mono<FootballMatch> updateScore(String localTeam, String awayTeam, String matchDate, String scorerTeam) {
        return getDynamoEntity(localTeam, awayTeam, matchDate)
                .map(entity -> {
                    setScoreByTeam(scorerTeam, entity);
                    return entity;
                })
                .flatMap(entity -> Mono.fromFuture(table.putItem(entity)).thenReturn(entity))
                .map(FootballMatchDynamoMapper::toDomain);
    }

    private void setScoreByTeam(String scorerTeam, FootballMatchDynamoEntity footballMatchDynamoEntity) {
         if(scorerTeam.equals(footballMatchDynamoEntity.getLocalTeam())) {
             footballMatchDynamoEntity.setLocalScore(footballMatchDynamoEntity.getLocalScore() + 1);
         } else {
             footballMatchDynamoEntity.setAwayScore(footballMatchDynamoEntity.getAwayScore() + 1);
         }
    }

    @Override
    public Mono<FootballMatch> delete(String localTeam, String awayTeam, String matchDate) {
        return getDynamoEntity(localTeam, awayTeam, matchDate)
                .map(footballMatchDynamoEntity -> Key.builder()
                        .partitionValue(footballMatchDynamoEntity.getPk())
                        .sortValue(footballMatchDynamoEntity.getSk())
                        .build())
                .flatMap(key -> Mono.fromFuture(table.deleteItem(key)))
                .map(FootballMatchDynamoMapper::toDomain);
    }

}
