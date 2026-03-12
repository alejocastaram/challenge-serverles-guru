package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.BusinessException;
import com.alejandrocastaneda.serverles_guru_challenge.application.mapper.FootballMatchMapper;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.UpdateScoreRequestDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
public class UpdateScoreUseCase {
    private final FootballMatchRepository footballMatchRepository;

    public UpdateScoreUseCase(FootballMatchRepository footballMatchRepository) {
        this.footballMatchRepository = footballMatchRepository;
    }

    public Mono<FootballMatchDTO> execute(UpdateScoreRequestDTO updateScoreRequestDTO) {
        return Mono.just(updateScoreRequestDTO)
                .filter(validationScorerTeamPredicate())
                .switchIfEmpty(
                        Mono.error(new BusinessException(
                                "Scorer team: " +
                                updateScoreRequestDTO.getScorerTeam() +
                                " is not participating in the game"
                                )
                        )
                ).flatMap(dto ->
                        footballMatchRepository.updateScore(updateScoreRequestDTO.getLocalTeam(),
                                updateScoreRequestDTO.getAwayTeam(), updateScoreRequestDTO.getMatchDate(),
                                updateScoreRequestDTO.getScorerTeam())
                ).map(FootballMatchMapper::toDTO);

    }

    private static @NonNull Predicate<UpdateScoreRequestDTO> validationScorerTeamPredicate() {
        return dto -> dto.getLocalTeam().equals(dto.getScorerTeam()) || dto.getAwayTeam().equals(dto.getScorerTeam());
    }
}
