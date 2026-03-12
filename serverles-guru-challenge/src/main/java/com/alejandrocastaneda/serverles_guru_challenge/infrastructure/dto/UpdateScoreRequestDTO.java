package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class UpdateScoreRequestDTO implements Serializable {
    @NotBlank
    private String localTeam;
    @NotBlank
    private String awayTeam;
    @NotBlank
    private String matchDate;
    @NotBlank
    private String scorerTeam;

    public @NotBlank String getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(@NotBlank String localTeam) {
        this.localTeam = localTeam;
    }

    public @NotBlank String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(@NotBlank String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public @NotBlank String getScorerTeam() {
        return scorerTeam;
    }

    public void setScorerTeam(@NotBlank String scorerTeam) {
        this.scorerTeam = scorerTeam;
    }

    public @NotBlank String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(@NotBlank String matchDate) {
        this.matchDate = matchDate;
    }
}
