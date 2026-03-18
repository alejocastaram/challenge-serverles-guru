package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.FootballMatchNotFoundException;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.DeleteMatchRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteFootballMatchUseCaseTest {
    private FootballMatchRepository repository;
    private DeleteFootballMatchUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FootballMatchRepository.class);
        useCase = new DeleteFootballMatchUseCase(repository);
    }

    @Test
    void shouldUpdateScoreWhenScorerTeamIsValid() {
        DeleteMatchRequestDTO request = new DeleteMatchRequestDTO();
        request.setLocalTeam("Local FC");
        request.setAwayTeam("Away FC");
        request.setMatchDate("2024-01-01T20:00");

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

        when(repository.delete(anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(updatedMatch));

        Mono<Void> result = useCase.execute(request);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void shouldErrorWhenScorerTeamIsNotInMatch() {
        DeleteMatchRequestDTO request = new DeleteMatchRequestDTO();
        request.setLocalTeam("Local FC");
        request.setAwayTeam("Away FC");
        request.setMatchDate("2024-01-01T20:00");

        when(repository.delete(anyString(), anyString(), anyString()))
                .thenReturn(Mono.empty());

        Mono<Void> result = useCase.execute(request);

        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable).isInstanceOf(FootballMatchNotFoundException.class);
                    assertThat(throwable.getMessage()).isEqualTo(
                            "Football match not found"
                    );
                })
                .verify();
    }
}
