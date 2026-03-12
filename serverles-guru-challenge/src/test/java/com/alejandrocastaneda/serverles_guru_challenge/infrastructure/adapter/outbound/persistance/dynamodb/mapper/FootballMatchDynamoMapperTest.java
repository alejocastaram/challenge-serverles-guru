package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.entity.FootballMatchDynamoEntity;

class FootballMatchDynamoMapperTest {

    @Test
    void shouldMapDomainToDynamoEntity() {
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

        FootballMatchDynamoEntity entity = FootballMatchDynamoMapper.toDynamoEntity(match);

        assertThat(entity.getPk()).isNotNull().isNotEmpty();
        assertThat(entity.getSk()).isEqualTo("Local-FC-VS-Away-FC#2024-01-01");

        assertThat(entity.getLocalTeam()).isEqualTo("Local FC");
        assertThat(entity.getLocalTeamImageUrl()).isEqualTo("http://local.png");
        assertThat(entity.getAwayTeam()).isEqualTo("Away FC");
        assertThat(entity.getAwayTeamImageUrl()).isEqualTo("http://away.png");
        assertThat(entity.getMatchDate()).isEqualTo(LocalDateTime.of(2024, 1, 1, 20, 0));
        assertThat(entity.getStadium()).isEqualTo("My Stadium");
        assertThat(entity.getLocalScore()).isEqualTo(2);
        assertThat(entity.getAwayScore()).isEqualTo(1);
    }

    @Test
    void shouldMapDynamoEntityToDomain() {
        FootballMatchDynamoEntity entity = new FootballMatchDynamoEntity();
        entity.setLocalTeam("Local FC");
        entity.setLocalTeamImageUrl("http://local.png");
        entity.setAwayTeam("Away FC");
        entity.setAwayTeamImageUrl("http://away.png");
        entity.setMatchDate(LocalDateTime.of(2024, 1, 1, 20, 0));
        entity.setStadium("My Stadium");
        entity.setLocalScore(4);
        entity.setAwayScore(3);

        FootballMatch match = FootballMatchDynamoMapper.toDomain(entity);

        assertThat(match.localTeam()).isEqualTo("Local FC");
        assertThat(match.localTeamImageUrl()).isEqualTo("http://local.png");
        assertThat(match.awayTeam()).isEqualTo("Away FC");
        assertThat(match.awayTeamImageUrl()).isEqualTo("http://away.png");
        assertThat(match.matchDate()).isEqualTo(LocalDateTime.of(2024, 1, 1, 20, 0));
        assertThat(match.stadium()).isEqualTo("My Stadium");
        assertThat(match.localScore()).isEqualTo(4);
        assertThat(match.awayScore()).isEqualTo(3);
    }
}

