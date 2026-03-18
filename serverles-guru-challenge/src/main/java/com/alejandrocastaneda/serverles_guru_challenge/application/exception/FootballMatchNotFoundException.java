package com.alejandrocastaneda.serverles_guru_challenge.application.exception;

public class FootballMatchNotFoundException extends RuntimeException {

    public FootballMatchNotFoundException(String localTeam, String awayTeam, String matchDate) {
        super("Football match not found: " + localTeam + " vs " + awayTeam + " at " + matchDate);
    }

    public FootballMatchNotFoundException() {
        super("Football match not found");
    }
}
