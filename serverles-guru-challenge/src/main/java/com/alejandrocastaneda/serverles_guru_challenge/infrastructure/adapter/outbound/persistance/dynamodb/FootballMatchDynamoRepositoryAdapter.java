package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.entity.FootballMatchDynamoEntity;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.mapper.FootballMatchDynamoMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Repository
public class FootballMatchDynamoRepositoryAdapter implements FootballMatchRepository {
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
}
