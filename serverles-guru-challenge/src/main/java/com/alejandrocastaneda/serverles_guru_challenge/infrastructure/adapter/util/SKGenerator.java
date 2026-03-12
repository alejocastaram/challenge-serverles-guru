package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.util;

public final class SKGenerator {

    private SKGenerator () {
        //empty because is an utility class
    }

    public static String generate(String localTeam, String awayTeam, String matchDate) {
        String localTeamWithoutBlankSpaces = localTeam.replace(" ", "-");
        String awayTeamWithoutBlankSpaces = awayTeam.replace(" ", "-");
        return localTeamWithoutBlankSpaces +
                "-VS-" +
                awayTeamWithoutBlankSpaces +
                "#" +
                matchDate;
    }
}
