package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.rest;

import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.CreateFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.GetFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.UpdateScoreUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase.DeleteFootballMatchUseCase;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.DeleteMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.UpdateScoreRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/football-match")
public class FootballMatchController {
    private final CreateFootballMatchUseCase createFootballMatchUseCase;
    private final GetFootballMatchUseCase getFootballMatchUseCase;
    private final UpdateScoreUseCase updateScoreUseCase;
    private final DeleteFootballMatchUseCase deleteFootballMatchUseCase;

    public FootballMatchController(CreateFootballMatchUseCase createFootballMatchUseCase,
                                   GetFootballMatchUseCase getFootballMatchUseCase,
                                   UpdateScoreUseCase updateScoreUseCase,
                                   DeleteFootballMatchUseCase deleteFootballMatchUseCase) {
        this.createFootballMatchUseCase = createFootballMatchUseCase;
        this.getFootballMatchUseCase = getFootballMatchUseCase;
        this.updateScoreUseCase = updateScoreUseCase;
        this.deleteFootballMatchUseCase = deleteFootballMatchUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createMatch(@Valid @RequestBody CreateMatchRequestDTO createMatchRequestDTO) {
        return createFootballMatchUseCase.execute(createMatchRequestDTO);
    }

    @GetMapping("/{localTeam}/{awayTeam}/{matchDate}")
    public Mono<FootballMatchDTO> getMatch(@PathVariable String localTeam, @PathVariable String awayTeam,
                                           @PathVariable String matchDate) {
        return getFootballMatchUseCase.execute(localTeam, awayTeam, matchDate);
    }

    @PutMapping
    public Mono<FootballMatchDTO> updateScore(@Valid @RequestBody UpdateScoreRequestDTO updateScoreRequestDTO) {
        return updateScoreUseCase.execute(updateScoreRequestDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteMatch(@Valid @RequestBody DeleteMatchRequestDTO deleteMatchRequestDTO) {
        return deleteFootballMatchUseCase.execute(deleteMatchRequestDTO);
    }

}
