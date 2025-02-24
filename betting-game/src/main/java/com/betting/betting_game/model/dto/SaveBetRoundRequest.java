package com.betting.betting_game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class SaveBetRoundRequest {
    private String title;
    private LocalDateTime playDateTime;
}
