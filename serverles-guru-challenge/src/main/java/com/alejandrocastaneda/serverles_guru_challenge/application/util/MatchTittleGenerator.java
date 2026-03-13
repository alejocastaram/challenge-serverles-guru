package com.alejandrocastaneda.serverles_guru_challenge.application.util;

public final class MatchTittleGenerator {

    private MatchTittleGenerator() {
        //empty because is an utility class
    }

    public static String generate(String localTeam, String awayTeam, String matchDate) {
        String localTeamWithoutBlankSpaces = localTeam.replace(" ", "-");
        String awayTeamWithoutBlankSpaces = awayTeam.replace(" ", "-");
        return localTeamWithoutBlankSpaces +
                "-VS-" +
                awayTeamWithoutBlankSpaces +
                "-AT-" +
                matchDate;
    }
}
