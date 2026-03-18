package com.alejandrocastaneda.serverles_guru_challenge.application.mapper;

import java.time.LocalDateTime;

import com.alejandrocastaneda.serverles_guru_challenge.application.util.MatchTittleGenerator;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;

public final class FootballMatchMapper {

    private FootballMatchMapper() {
        //empty because is a mapper
    }

    public static FootballMatch toDomain (CreateMatchRequestDTO dto) {
        String localTeam = dto.getLocalTeam();
        String awayTeam = dto.getAwayTeam();
        LocalDateTime now = LocalDateTime.now();
        String matchDate = now.toLocalDate().toString();
        return new FootballMatch(
                MatchTittleGenerator.generate(localTeam, awayTeam, matchDate),
                localTeam,
                dto.getLocalTeamImageUrl(),
                awayTeam,
                dto.getAwayTeamImageUrl(),
                now,
                dto.getStadium(),
                0,
                0
        );
    }

    public static FootballMatchDTO toDTO (FootballMatch footballMatch) {
        FootballMatchDTO footballMatchDTO = new FootballMatchDTO();
        footballMatchDTO.setMatchTittle(footballMatch.matchTittle());
        footballMatchDTO.setMatchDate(footballMatch.matchDate());
        footballMatchDTO.setAwayScore(footballMatch.awayScore());
        footballMatchDTO.setAwayTeam(footballMatch.awayTeam());
        footballMatchDTO.setStadium(footballMatch.stadium());
        footballMatchDTO.setLocalScore(footballMatch.localScore());
        footballMatchDTO.setLocalTeam(footballMatch.localTeam());
        footballMatchDTO.setAwayTeamImageUrl(footballMatch.awayTeamImageUrl());
        footballMatchDTO.setLocalTeamImageUrl(footballMatch.localTeamImageUrl());

        return footballMatchDTO;
    }
}

