package com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface FootballMatchRepository {
    Mono<Void> create (FootballMatch footballMatch);

    Mono<FootballMatch> get (String localTeam, String awayTeam, String matchDate);

    Mono<FootballMatch> updateScore (String localTeam, String awayTeam, String matchDate, String scorerTeam);
}
