package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.FootballMatchNotFoundException;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class GetFootballMatchUseCaseTest {
    private GetFootballMatchUseCase useCase;
    private FootballMatchRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(FootballMatchRepository.class);
        useCase = new GetFootballMatchUseCase(repository);
    }

    @Test
    void shouldReturnMappedDtoWhenMatchExists() {
        FootballMatch match = new FootballMatch(
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

        when(repository.get(anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(match));

        StepVerifier.create(useCase.execute("Local FC", "Away FC", "2024-01-01T20:00"))
                .assertNext(dto -> {
                    assertThat(dto).isNotNull();
                    assertThat(dto.getLocalTeam()).isEqualTo("Local FC");
                    assertThat(dto.getAwayTeam()).isEqualTo("Away FC");
                    assertThat(dto.getLocalScore()).isEqualTo(1);
                    assertThat(dto.getAwayScore()).isEqualTo(0);
                })
                .verifyComplete();
    }

    @Test
    void shouldErrorWhenMatchDoesNotExist() {
        FootballMatchRepository repository = mock(FootballMatchRepository.class);

        when(repository.get(anyString(), anyString(), anyString()))
                .thenReturn(Mono.empty());

        GetFootballMatchUseCase useCase = new GetFootballMatchUseCase(repository);

        Mono<FootballMatchDTO> useCaseResponse =
                useCase.execute("Local FC", "Away FC", "2024-01-01T20:00");

        StepVerifier.create(useCaseResponse) // Create a StepVerifier for the Mono
                .expectErrorMatches(throwable -> {           // Use a predicate for advanced matching
                    return throwable instanceof FootballMatchNotFoundException &&
                            throwable.getMessage().equals("Football match not found: Local FC " +
                                    "vs Away FC at 2024-01-01T20:00");
                })
                .verify();
    }
}

