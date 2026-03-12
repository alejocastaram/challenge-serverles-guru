package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.entity.FootballMatchDynamoEntity;

import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

class FootballMatchDynamoRepositoryAdapterTest {

    @Test
    void createShouldPutItemInTable() throws Exception {
        DynamoDbAsyncClient client = mock(DynamoDbAsyncClient.class);
        FootballMatchDynamoRepositoryAdapter adapter = new FootballMatchDynamoRepositoryAdapter(client);

        @SuppressWarnings("unchecked")
        DynamoDbAsyncTable<FootballMatchDynamoEntity> table = mock(DynamoDbAsyncTable.class);
        when(table.putItem(any(FootballMatchDynamoEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        Field tableField = FootballMatchDynamoRepositoryAdapter.class.getDeclaredField("table");
        tableField.setAccessible(true);
        tableField.set(adapter, table);

        FootballMatch match = new FootballMatch(
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
}

