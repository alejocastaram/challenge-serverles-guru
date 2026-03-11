package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.rest;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.CreateFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/football-match")
public class FootballMatchController {
    private final CreateFootballMatchUseCase createFootballMatchUseCase;

    public FootballMatchController(CreateFootballMatchUseCase createFootballMatchUseCase) {
        this.createFootballMatchUseCase = createFootballMatchUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createMatch(@Valid @RequestBody CreateMatchDTO createMatchDTO) {
        return createFootballMatchUseCase.execute(createMatchDTO);
    }

}
