package com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import reactor.core.publisher.Mono;

public interface FootballMatchRepository {
    Mono<Void> create(FootballMatch footballMatch);
}
