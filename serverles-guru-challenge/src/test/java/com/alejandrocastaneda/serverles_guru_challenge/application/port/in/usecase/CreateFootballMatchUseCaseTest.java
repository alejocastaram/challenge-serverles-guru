package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;

import reactor.core.publisher.Mono;

class CreateFootballMatchUseCaseTest {
    private FootballMatchRepository repository;
    private CreateFootballMatchUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FootballMatchRepository.class);
        useCase = new CreateFootballMatchUseCase(repository);
    }

    @Test
    void shouldMapDtoAndCallRepository() {
        AtomicReference<FootballMatch> captured = new AtomicReference<>();

        when(repository.create(any(FootballMatch.class)))
                .thenAnswer(invocation -> {
                    FootballMatch match = invocation.getArgument(0);
                    captured.set(match);
                    return Mono.empty();
                });

        CreateMatchRequestDTO dto = getCreateMatchDtoExample();

        useCase.execute(dto).block();

        FootballMatch saved = captured.get();

        verify(repository, times(1)).create(any(FootballMatch.class));

        assertThat(saved).isNotNull();
        assertThat(saved.localTeam()).isEqualTo("Local FC");
        assertThat(saved.awayTeam()).isEqualTo("Away FC");
        assertThat(saved.localScore()).isZero();
        assertThat(saved.awayScore()).isZero();
    }

    private CreateMatchRequestDTO getCreateMatchDtoExample() {
        CreateMatchRequestDTO dto = new CreateMatchRequestDTO();
        dto.setLocalTeam("Local FC");
        dto.setLocalTeamImageUrl("http://local.png");
        dto.setAwayTeam("Away FC");
        dto.setAwayTeamImageUrl("http://away.png");
        dto.setStadium("My Stadium");
        return dto;
    }
}

