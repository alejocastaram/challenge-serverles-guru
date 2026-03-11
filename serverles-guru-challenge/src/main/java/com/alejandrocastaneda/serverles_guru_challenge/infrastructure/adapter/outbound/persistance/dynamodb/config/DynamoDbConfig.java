package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {

        return DynamoDbAsyncClient.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }
}
