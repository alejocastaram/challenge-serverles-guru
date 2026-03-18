package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FootballMatchDTO implements Serializable {
    private String matchTittle;
    private String localTeam;
    private String localTeamImageUrl;
    private String awayTeam;
    private String awayTeamImageUrl;
    private LocalDateTime matchDate;
    private String stadium;
    private int localScore;
    private int awayScore;

    public String getMatchTittle() {
        return matchTittle;
    }

    public void setMatchTittle(String matchTittle) {
        this.matchTittle = matchTittle;
    }

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

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getAwayTeamImageUrl() {
        return awayTeamImageUrl;
    }

    public void setAwayTeamImageUrl(String awayTeamImageUrl) {
        this.awayTeamImageUrl = awayTeamImageUrl;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public int getLocalScore() {
        return localScore;
    }

    public void setLocalScore(int localScore) {
        this.localScore = localScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}
