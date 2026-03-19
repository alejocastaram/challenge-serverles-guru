package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.lambda;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.BusinessException;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.CreateFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.DeleteFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.GetFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.UpdateScoreUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.DeleteMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.UpdateScoreRequestDTO;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class FootballMatchFunctions {

    private final CreateFootballMatchUseCase createFootballMatchUseCase;
    private final GetFootballMatchUseCase getFootballMatchUseCase;
    private final UpdateScoreUseCase updateScoreUseCase;
    private final DeleteFootballMatchUseCase deleteFootballMatchUseCase;
    private final ObjectMapper objectMapper;

    public FootballMatchFunctions(
            CreateFootballMatchUseCase createFootballMatchUseCase,
            GetFootballMatchUseCase getFootballMatchUseCase,
            UpdateScoreUseCase updateScoreUseCase,
            DeleteFootballMatchUseCase deleteFootballMatchUseCase,
            ObjectMapper objectMapper) {
        this.createFootballMatchUseCase = createFootballMatchUseCase;
        this.getFootballMatchUseCase = getFootballMatchUseCase;
        this.updateScoreUseCase = updateScoreUseCase;
        this.deleteFootballMatchUseCase = deleteFootballMatchUseCase;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createMatch() {
        return request -> {
            try {
                CreateMatchRequestDTO dto = objectMapper.readValue(request.getBody(), CreateMatchRequestDTO.class);
                createFootballMatchUseCase.execute(dto).block();
                return response(201, "{\"message\":\"Match created\"}");
            } catch (Exception e) {
                return handleError(e);
            }
        };
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> getMatch() {
        return request -> {
            try {
                Map<String, String> params = request.getPathParameters();
                FootballMatchDTO dto = getFootballMatchUseCase
                        .execute(params.get("localTeam"), params.get("awayTeam"), params.get("matchDate"))
                        .block();
                return response(200, objectMapper.writeValueAsString(dto));
            } catch (Exception e) {
                return handleError(e);
            }
        };
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> updateScore() {
        return request -> {
            try {
                UpdateScoreRequestDTO dto = objectMapper.readValue(request.getBody(), UpdateScoreRequestDTO.class);
                FootballMatchDTO result = updateScoreUseCase.execute(dto).block();
                return response(200, objectMapper.writeValueAsString(result));
            } catch (Exception e) {
                return handleError(e);
            }
        };
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> deleteMatch() {
        return request -> {
            try {
                DeleteMatchRequestDTO dto = objectMapper.readValue(request.getBody(), DeleteMatchRequestDTO.class);
                deleteFootballMatchUseCase.execute(dto).block();
                return response(200, "{\"message\":\"Match deleted\"}");
            } catch (Exception e) {
                return handleError(e);
            }
        };
    }

    private APIGatewayProxyResponseEvent handleError(Exception e) {
        Throwable cause = e.getCause() != null ? e.getCause() : e;
        int status = cause instanceof BusinessException ? 400 : 500;
        return response(status, "{\"error\":\"" + cause.getMessage() + "\"}");
    }

    private APIGatewayProxyResponseEvent response(int statusCode, String body) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(Map.of("Content-Type", "application/json"))
                .withBody(body);
    }
}
