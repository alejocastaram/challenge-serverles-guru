package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.lambda;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.CreateFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.DeleteFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.GetFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.UpdateScoreUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.DeleteMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.UpdateScoreRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class FootballMatchFunctions {

    private final CreateFootballMatchUseCase createFootballMatchUseCase;
    private final GetFootballMatchUseCase getFootballMatchUseCase;
    private final UpdateScoreUseCase updateScoreUseCase;
    private final DeleteFootballMatchUseCase deleteFootballMatchUseCase;

    public FootballMatchFunctions(
            CreateFootballMatchUseCase createFootballMatchUseCase,
            GetFootballMatchUseCase getFootballMatchUseCase,
            UpdateScoreUseCase updateScoreUseCase,
            DeleteFootballMatchUseCase deleteFootballMatchUseCase) {
        this.createFootballMatchUseCase = createFootballMatchUseCase;
        this.getFootballMatchUseCase = getFootballMatchUseCase;
        this.updateScoreUseCase = updateScoreUseCase;
        this.deleteFootballMatchUseCase = deleteFootballMatchUseCase;
    }

    @Bean
    public Function<CreateMatchRequestDTO, Void> createMatch() {
        return request -> {
            createFootballMatchUseCase.execute(request).block();
            return null;
        };
    }

    @Bean
    public Function<DeleteMatchRequestDTO, FootballMatchDTO> getMatch() {
        return request -> getFootballMatchUseCase
                .execute(request.getLocalTeam(), request.getAwayTeam(), request.getMatchDate())
                .block();
    }

    @Bean
    public Function<UpdateScoreRequestDTO, FootballMatchDTO> updateScore() {
        return request -> updateScoreUseCase.execute(request).block();
    }

    @Bean
    public Function<DeleteMatchRequestDTO, Void> deleteMatch() {
        return request -> {
            deleteFootballMatchUseCase.execute(request).block();
            return null;
        };
    }
}
