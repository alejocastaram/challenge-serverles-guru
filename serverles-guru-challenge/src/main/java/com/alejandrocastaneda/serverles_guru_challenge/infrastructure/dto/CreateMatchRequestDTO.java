package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class CreateMatchRequestDTO implements Serializable {
    @NotBlank
    private String localTeam;

    @NotBlank
    private String localTeamImageUrl;

    @NotBlank
    private String awayTeam;

    @NotBlank
    private String awayTeamImageUrl;

    @NotBlank
    private String stadium;

    public String getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(String localTeam) {
        this.localTeam = localTeam;
    }

    public String getLocalTeamImageUrl() {
        return localTeamImageUrl;
    }

    public void setLocalTeamImageUrl(String localTeamImageUrl) {
        this.localTeamImageUrl = localTeamImageUrl;
    }

    public String getAwayTeamImageUrl() {
        return awayTeamImageUrl;
    }

    public void setAwayTeamImageUrl(String awayTeamImageUrl) {
        this.awayTeamImageUrl = awayTeamImageUrl;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

}
