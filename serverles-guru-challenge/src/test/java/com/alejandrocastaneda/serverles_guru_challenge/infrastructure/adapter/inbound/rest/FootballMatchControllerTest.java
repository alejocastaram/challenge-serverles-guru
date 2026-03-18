package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.rest;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.CreateFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.DeleteFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.GetFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.UpdateScoreUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.DeleteMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.UpdateScoreRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FootballMatchControllerTest {
    private FootballMatchController footballMatchController;
    private CreateFootballMatchUseCase createFootballMatchUseCase;
    private GetFootballMatchUseCase getFootballMatchUseCase;
    private UpdateScoreUseCase updateScoreUseCase;
    private DeleteFootballMatchUseCase deleteFootballMatchUseCase;

    @BeforeEach
    void setUp() {
        createFootballMatchUseCase = Mockito.mock(CreateFootballMatchUseCase.class);
        getFootballMatchUseCase = Mockito.mock(GetFootballMatchUseCase.class);
        updateScoreUseCase = Mockito.mock(UpdateScoreUseCase.class);
        deleteFootballMatchUseCase = Mockito.mock(DeleteFootballMatchUseCase.class);
        footballMatchController = new FootballMatchController(createFootballMatchUseCase,
                getFootballMatchUseCase, updateScoreUseCase, deleteFootballMatchUseCase);
    }

    @Test
    void unitTestCreateRestService() {
        CreateMatchRequestDTO dto = new CreateMatchRequestDTO();
        dto.setLocalTeam("Local FC");
        dto.setLocalTeamImageUrl("http://local.png");
        dto.setAwayTeam("Away FC");
        dto.setAwayTeamImageUrl("http://away.png");
        dto.setStadium("My Stadium");

        footballMatchController.createMatch(dto);

        verify(createFootballMatchUseCase, times(1)).execute(any(CreateMatchRequestDTO.class));
    }

    @Test
    void unitTestGetRestService() {
        FootballMatchDTO match = new FootballMatchDTO();
        match.setMatchTittle("Local-FC-VS-Away-FC-AT-2024-01-01");

        when(getFootballMatchUseCase.execute(anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(match));

        StepVerifier.create(footballMatchController.getMatch("Local FC", "Away FC", "2024-01-01T20:00"))
                .assertNext(dto -> {
                    assertThat(dto).isNotNull();
                    assertThat(dto.getMatchTittle()).isEqualTo(match.getMatchTittle());
                })
                .verifyComplete();
    }

    @Test
    void unitTestUpdateScoreRestService() {
        UpdateScoreRequestDTO request = new UpdateScoreRequestDTO();
        request.setLocalTeam("Local FC");
        request.setAwayTeam("Away FC");
        request.setMatchDate("2024-01-01T20:00");
        request.setScorerTeam("Local FC");

        FootballMatchDTO match = new FootballMatchDTO();
        match.setMatchTittle("Local-FC-VS-Away-FC-AT-2024-01-01");
        match.setLocalScore(1);

        when(updateScoreUseCase.execute(any(UpdateScoreRequestDTO.class)))
                .thenReturn(Mono.just(match));

        Mono<FootballMatchDTO> result = footballMatchController.updateScore(request);

        StepVerifier.create(result)
                .assertNext(dto -> {
                    assertThat(dto.getLocalScore()).isEqualTo(1);
                })
                .verifyComplete();
    }

    @Test
    void unitTestDeleteRestService() {
        DeleteMatchRequestDTO request = new DeleteMatchRequestDTO();
        request.setLocalTeam("Local FC");
        request.setAwayTeam("Away FC");
        request.setMatchDate("2024-01-01T20:00");

        when(deleteFootballMatchUseCase.execute(any(DeleteMatchRequestDTO.class)))
                .thenReturn(Mono.empty());

        Mono<Void> result = footballMatchController.deleteMatch(request);

        StepVerifier.create(result)
                .verifyComplete();
    }
}
