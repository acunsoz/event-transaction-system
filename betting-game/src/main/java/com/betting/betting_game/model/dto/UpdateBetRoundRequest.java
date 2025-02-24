package com.betting.betting_game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
//tekrar kontrolu sağla
public class UpdateBetRoundRequest {

    private String title;
    private LocalDateTime playDateTime;

}
