package com.alejandrocastaneda.serverles_guru_challenge.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;

class FootballMatchMapperTest {

    @Test
    void shouldMapCreateMatchDtoToDomainWithDefaults() {
        CreateMatchRequestDTO dto = new CreateMatchRequestDTO();
        dto.setLocalTeam("Local FC");
        dto.setLocalTeamImageUrl("http://local.png");
        dto.setAwayTeam("Away FC");
        dto.setAwayTeamImageUrl("http://away.png");
        dto.setStadium("My Stadium");

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

    @Test
    void shouldMapDomainToDto() {
        FootballMatch match = new FootballMatch(
                "Local-FC-VS-Away-FC-AT-2024-01-01",
                "Local FC",
                "http://local.png",
                "Away FC",
                "http://away.png",
                LocalDateTime.of(2024, 1, 1, 20, 0),
                "My Stadium",
                3,
                2
        );

        FootballMatchDTO dto = FootballMatchMapper.toDTO(match);

        assertThat(dto.getLocalTeam()).isEqualTo("Local FC");
        assertThat(dto.getLocalTeamImageUrl()).isEqualTo("http://local.png");
        assertThat(dto.getAwayTeam()).isEqualTo("Away FC");
        assertThat(dto.getAwayTeamImageUrl()).isEqualTo("http://away.png");
        assertThat(dto.getStadium()).isEqualTo("My Stadium");
        assertThat(dto.getLocalScore()).isEqualTo(3);
        assertThat(dto.getAwayScore()).isEqualTo(2);
        assertThat(dto.getMatchDate()).isEqualTo(LocalDateTime.of(2024, 1, 1, 20, 0));
    }
}

