package com.betting.betting_game.model.dto;

import com.betting.betting_game.model.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateBetRoundStatusRequest {

    private List<Game> games;
}
