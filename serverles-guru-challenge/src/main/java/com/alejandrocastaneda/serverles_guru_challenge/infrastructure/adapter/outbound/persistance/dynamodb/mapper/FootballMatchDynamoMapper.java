package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.mapper;

import java.util.UUID;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.outbound.persistance.dynamodb.entity.FootballMatchDynamoEntity;

public final class FootballMatchDynamoMapper {
    private FootballMatchDynamoMapper() {
    }

    public static FootballMatchDynamoEntity toDynamoEntity(FootballMatch footballMatch) {
        FootballMatchDynamoEntity entity = new FootballMatchDynamoEntity();

        entity.setPk(UUID.randomUUID().toString());
        entity.setSk(footballMatch.localTeam() + "#" + footballMatch.awayTeam() + "#" +
                footballMatch.matchDate().toLocalDate());

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
}
