package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.rest;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.CreateFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.GetFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/football-match")
public class FootballMatchController {
    private final CreateFootballMatchUseCase createFootballMatchUseCase;
    private final GetFootballMatchUseCase getFootballMatchUseCase;

    public FootballMatchController(CreateFootballMatchUseCase createFootballMatchUseCase, GetFootballMatchUseCase getFootballMatchUseCase) {
        this.createFootballMatchUseCase = createFootballMatchUseCase;
        this.getFootballMatchUseCase = getFootballMatchUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createMatch(@Valid @RequestBody CreateMatchDTO createMatchDTO) {
        return createFootballMatchUseCase.execute(createMatchDTO);
    }

    @GetMapping("/{localTeam}/{awayTeam}/{matchDate}")
    public Mono<FootballMatchDTO> getMatch(@PathVariable String localTeam, @PathVariable String awayTeam,
                                           @PathVariable String matchDate) {
        return getFootballMatchUseCase.execute(localTeam, awayTeam, matchDate);
    }
}
