package com.alejandrocastaneda.serverles_guru_challenge.domain.entity;

import java.time.LocalDateTime;

public record FootballMatch (
        String localTeam,
        String localTeamImageUrl,
        String awayTeam,
        String awayTeamImageUrl,
        LocalDateTime matchDate,
        String stadium,
        int localScore,
        int awayScore) {
}
