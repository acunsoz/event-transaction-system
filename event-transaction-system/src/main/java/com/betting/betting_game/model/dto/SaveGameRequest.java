package com.betting.betting_game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaveGameRequest {

    private String firstTeamName;
    private String secondTeamName;
    private Long betRoundId;
}
