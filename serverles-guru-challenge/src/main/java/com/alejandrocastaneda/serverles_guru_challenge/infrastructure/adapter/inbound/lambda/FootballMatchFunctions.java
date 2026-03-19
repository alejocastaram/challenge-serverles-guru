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
import reactor.core.publisher.Mono;

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
    public Function<Mono<APIGatewayProxyRequestEvent>, Mono<APIGatewayProxyResponseEvent>> createMatch() {
        return requestMono -> requestMono
                .flatMap(request -> Mono.fromCallable(
                        () -> objectMapper.readValue(request.getBody(), CreateMatchRequestDTO.class)))
                .flatMap(createFootballMatchUseCase::execute)
                .thenReturn(response(201, "{\"message\":\"Match created\"}"))
                .onErrorResume(e -> Mono.just(handleError(e)));
    }

    @Bean
    public Function<Mono<APIGatewayProxyRequestEvent>, Mono<APIGatewayProxyResponseEvent>> getMatch() {
        return requestMono -> requestMono
                .flatMap(request -> {
                    Map<String, String> params = request.getPathParameters();
                    return getFootballMatchUseCase.execute(
                            params.get("localTeam"), params.get("awayTeam"), params.get("matchDate"));
                })
                .flatMap(dto -> Mono.fromCallable(() -> response(200, objectMapper.writeValueAsString(dto))))
                .onErrorResume(e -> Mono.just(handleError(e)));
    }

    @Bean
    public Function<Mono<APIGatewayProxyRequestEvent>, Mono<APIGatewayProxyResponseEvent>> updateScore() {
        return requestMono -> requestMono
                .flatMap(request -> Mono.fromCallable(
                        () -> objectMapper.readValue(request.getBody(), UpdateScoreRequestDTO.class)))
                .flatMap(updateScoreUseCase::execute)
                .flatMap(result -> Mono.fromCallable(() -> response(200, objectMapper.writeValueAsString(result))))
                .onErrorResume(e -> Mono.just(handleError(e)));
    }

    @Bean
    public Function<Mono<APIGatewayProxyRequestEvent>, Mono<APIGatewayProxyResponseEvent>> deleteMatch() {
        return requestMono -> requestMono
                .flatMap(request -> Mono.fromCallable(
                        () -> objectMapper.readValue(request.getBody(), DeleteMatchRequestDTO.class)))
                .flatMap(deleteFootballMatchUseCase::execute)
                .thenReturn(response(200, "{\"message\":\"Match deleted\"}"))
                .onErrorResume(e -> Mono.just(handleError(e)));
    }

    private APIGatewayProxyResponseEvent handleError(Throwable e) {
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
