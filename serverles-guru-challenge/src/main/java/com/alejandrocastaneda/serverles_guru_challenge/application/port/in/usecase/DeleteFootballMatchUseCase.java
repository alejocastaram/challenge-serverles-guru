package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.FootballMatchNotFoundException;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.DeleteMatchRequestDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteFootballMatchUseCase {
    private final FootballMatchRepository footballMatchRepository;

    public DeleteFootballMatchUseCase(FootballMatchRepository footballMatchRepository) {
        this.footballMatchRepository = footballMatchRepository;
    }

    public Mono<Void> execute(DeleteMatchRequestDTO deleteMatchRequestDTO) {
        return footballMatchRepository.delete(deleteMatchRequestDTO.getLocalTeam(),
                        deleteMatchRequestDTO.getAwayTeam(), deleteMatchRequestDTO.getMatchDate())
                .switchIfEmpty(Mono.error(new FootballMatchNotFoundException()))
                .then();
    }
}
