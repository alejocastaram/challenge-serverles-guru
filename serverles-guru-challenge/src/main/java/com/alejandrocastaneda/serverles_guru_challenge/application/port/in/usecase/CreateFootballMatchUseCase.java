package com.alejandrocastaneda.serverles_guru_challenge.application.port.in.usecase;

import com.alejandrocastaneda.serverles_guru_challenge.application.mapper.FootballMatchMapper;
import com.alejandrocastaneda.serverles_guru_challenge.application.port.out.repository.FootballMatchRepository;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateFootballMatchUseCase {
    private final FootballMatchRepository footballMatchRepository;

    public CreateFootballMatchUseCase(FootballMatchRepository footballMatchRepository) {
        this.footballMatchRepository = footballMatchRepository;
    }

    public Mono<Void> execute(CreateMatchRequestDTO createMatchRequestDTO) {
        return Mono.fromSupplier(() -> FootballMatchMapper.toDomain(createMatchRequestDTO))
                .flatMap(footballMatchRepository::create);
    }
}
