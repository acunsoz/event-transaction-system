package com.betting.betting_game.model.dto;

import com.betting.betting_game.model.entity.enums.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GameScoreRequest {

    private Score score;

}
