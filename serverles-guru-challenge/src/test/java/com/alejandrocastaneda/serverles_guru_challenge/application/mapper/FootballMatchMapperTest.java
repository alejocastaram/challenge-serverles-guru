package com.alejandrocastaneda.serverles_guru_challenge.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchDTO;

class FootballMatchMapperTest {

    @Test
    void shouldMapCreateMatchDtoToDomainWithDefaults() {
        CreateMatchDTO dto = new CreateMatchDTO();
        dto.setLocalTeam("Local FC");
        dto.setLocalTeamImageUrl("http://local.png");
        dto.setAwayTeam("Away FC");
        dto.setAwayTeamImageUrl("http://away.png");
        dto.setStadium("My Stadium");
        dto.setMatchDate(LocalDateTime.of(2020, 1, 1, 12, 0)); // should be ignored

        LocalDateTime before = LocalDateTime.now().minusSeconds(5);

        FootballMatch result = FootballMatchMapper.toDomain(dto);

        LocalDateTime after = LocalDateTime.now().plusSeconds(5);

        assertThat(result.localTeam()).isEqualTo("Local FC");
        assertThat(result.localTeamImageUrl()).isEqualTo("http://local.png");
        assertThat(result.awayTeam()).isEqualTo("Away FC");
        assertThat(result.awayTeamImageUrl()).isEqualTo("http://away.png");
        assertThat(result.stadium()).isEqualTo("My Stadium");

        assertThat(result.localScore()).isZero();
        assertThat(result.awayScore()).isZero();

        assertThat(result.matchDate()).isAfterOrEqualTo(before);
        assertThat(result.matchDate()).isBeforeOrEqualTo(after);
    }
}

