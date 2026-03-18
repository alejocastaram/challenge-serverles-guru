package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.entity.FootballMatchDynamoEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

class FootballMatchDynamoRepositoryAdapterTest {

    private DynamoDbAsyncClient client;
    private FootballMatchDynamoRepositoryAdapter adapter;
    private DynamoDbAsyncTable<FootballMatchDynamoEntity> table;

    @BeforeEach
    void setUp() throws Exception {
        client = mock(DynamoDbAsyncClient.class);
        adapter = new FootballMatchDynamoRepositoryAdapter(client);

        @SuppressWarnings("unchecked")
        DynamoDbAsyncTable<FootballMatchDynamoEntity> mockedTable = mock(DynamoDbAsyncTable.class);
        this.table = mockedTable;

        Field tableField = FootballMatchDynamoRepositoryAdapter.class.getDeclaredField("table");
        tableField.setAccessible(true);
        tableField.set(adapter, mockedTable);
    }

    @Test
    void createShouldPutItemInTable() {
        when(table.putItem(any(FootballMatchDynamoEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        FootballMatch match = new FootballMatch(
                "Local-FC-VS-Away-FC-AT-2024-01-01",
                "Local FC",
                "http://local.png",
                "Away FC",
                "http://away.png",
                LocalDateTime.of(2024, 1, 1, 20, 0),
                "My Stadium",
                2,
                1
        );

        Mono<Void> result = adapter.create(match);

        assertThatCode(result::block).doesNotThrowAnyException();
        verify(table).putItem(any(FootballMatchDynamoEntity.class));
    }

    @Test
    void updateScoreShouldIncrementLocalScoreAndPersist() {
        FootballMatchDynamoEntity entity = new FootballMatchDynamoEntity();
        entity.setLocalTeam("Local FC");
        entity.setAwayTeam("Away FC");
        entity.setMatchDate(LocalDateTime.of(2024, 1, 1, 20, 0));
        entity.setStadium("My Stadium");
        entity.setLocalScore(0);
        entity.setAwayScore(0);

        DynamoDbAsyncIndex<FootballMatchDynamoEntity> index = mock(DynamoDbAsyncIndex.class);
        when(table.index(any())).thenReturn(index);

        Page<FootballMatchDynamoEntity> page = Page.create(Collections.singletonList(entity));
        SdkPublisher<Page<FootballMatchDynamoEntity>> publisher = s -> Flux.just(page).subscribe(s);

        when(index.query(any(QueryConditional.class)))
                .thenReturn(publisher);

        when(table.putItem(any(FootballMatchDynamoEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        Mono<FootballMatch> result = adapter.updateScore("Local FC", "Away FC", "2024-01-01T20:00", "Local FC");

        StepVerifier.create(result)
                .assertNext(match -> {
                    assertThat(match.localTeam()).isEqualTo("Local FC");
                    assertThat(match.awayTeam()).isEqualTo("Away FC");
                    assertThat(match.localScore()).isEqualTo(1);
                    assertThat(match.awayScore()).isEqualTo(0);
                })
                .verifyComplete();

        verify(table).putItem(any(FootballMatchDynamoEntity.class));
    }
}


