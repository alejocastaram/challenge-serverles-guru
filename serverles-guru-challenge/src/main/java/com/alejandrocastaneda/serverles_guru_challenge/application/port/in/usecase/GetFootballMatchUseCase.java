package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.FootballMatchNotFoundException;
import com.alejandrocastaneda.serverles_guru_challenge.application.mapper.FootballMatchMapper;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetFootballMatchUseCase {
    private final FootballMatchRepository footballMatchRepository;

    public GetFootballMatchUseCase(FootballMatchRepository footballMatchRepository) {
        this.footballMatchRepository = footballMatchRepository;
    }

    public Mono<FootballMatchDTO> execute (String localTeam, String awayTeam, String matchDate) {
        return footballMatchRepository.get(localTeam, awayTeam, matchDate)
                .map(FootballMatchMapper::toDTO)
                .switchIfEmpty(Mono.error(new FootballMatchNotFoundException(localTeam, awayTeam, matchDate)));
    }
}
