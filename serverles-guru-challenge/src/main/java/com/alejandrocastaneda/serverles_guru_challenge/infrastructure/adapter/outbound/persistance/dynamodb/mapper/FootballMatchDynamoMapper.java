package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.mapper;

import java.util.UUID;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.entity.FootballMatchDynamoEntity;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.util.SKGenerator;

public final class FootballMatchDynamoMapper {
    private FootballMatchDynamoMapper() {
    }

    public static FootballMatchDynamoEntity toDynamoEntity(FootballMatch footballMatch) {
        FootballMatchDynamoEntity entity = new FootballMatchDynamoEntity();

        entity.setPk(UUID.randomUUID().toString());
        entity.setSk(SKGenerator.generate(footballMatch.localTeam(), footballMatch.awayTeam(),
                footballMatch.matchDate().toLocalDate().toString()));

        entity.setLocalTeam(footballMatch.localTeam());
        entity.setLocalTeamImageUrl(footballMatch.localTeamImageUrl());
        entity.setAwayTeam(footballMatch.awayTeam());
        entity.setAwayTeamImageUrl(footballMatch.awayTeamImageUrl());
        entity.setMatchDate(footballMatch.matchDate());
        entity.setStadium(footballMatch.stadium());
        entity.setLocalScore(footballMatch.localScore());
        entity.setAwayScore(footballMatch.awayScore());

        return entity;
    }

    public static FootballMatch toDomain(FootballMatchDynamoEntity entity) {

        if (entity == null) {
            return null;
        }

        return new FootballMatch(
                entity.getLocalTeam(),
                entity.getLocalTeamImageUrl(),
                entity.getAwayTeam(),
                entity.getAwayTeamImageUrl(),
                entity.getMatchDate(),
                entity.getStadium(),
                entity.getLocalScore(),
                entity.getAwayScore()
        );
    }

}
