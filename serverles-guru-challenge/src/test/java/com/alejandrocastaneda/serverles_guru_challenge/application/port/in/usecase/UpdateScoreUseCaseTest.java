package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.BusinessException;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.UpdateScoreRequestDTO;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UpdateScoreUseCaseTest {

    private FootballMatchRepository repository;
    private UpdateScoreUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FootballMatchRepository.class);
        useCase = new UpdateScoreUseCase(repository);
    }

    @Test
    void shouldUpdateScoreWhenScorerTeamIsValid() {
        UpdateScoreRequestDTO request = new UpdateScoreRequestDTO();
        request.setLocalTeam("Local FC");
        request.setAwayTeam("Away FC");
        request.setMatchDate("2024-01-01T20:00");
        request.setScorerTeam("Local FC");

        FootballMatch updatedMatch = new FootballMatch(
                "Local-FC-VS-Away-FC-AT-2024-01-01",
                "Local FC",
                "http://local.png",
                "Away FC",
                "http://away.png",
                LocalDateTime.of(2024, 1, 1, 20, 0),
                "My Stadium",
                1,
                0
        );

        when(repository.updateScore(any(), any(), any(), any()))
                .thenReturn(Mono.just(updatedMatch));

        Mono<FootballMatchDTO> result = useCase.execute(request);

        StepVerifier.create(result)
                .assertNext(dto -> {
                    assertThat(dto.getLocalTeam()).isEqualTo("Local FC");
                    assertThat(dto.getAwayTeam()).isEqualTo("Away FC");
                    assertThat(dto.getLocalScore()).isEqualTo(1);
                    assertThat(dto.getAwayScore()).isEqualTo(0);
                })
                .verifyComplete();
    }

    @Test
    void shouldErrorWhenScorerTeamIsNotInMatch() {
        UpdateScoreRequestDTO request = new UpdateScoreRequestDTO();
        request.setLocalTeam("Local FC");
        request.setAwayTeam("Away FC");
        request.setMatchDate("2024-01-01T20:00");
        request.setScorerTeam("Other FC");

        Mono<FootballMatchDTO> result = useCase.execute(request);

        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable).isInstanceOf(BusinessException.class);
                    assertThat(throwable.getMessage()).isEqualTo(
                            "Scorer team: Other FC is not participating in the game"
                    );
                })
                .verify();
    }
}

