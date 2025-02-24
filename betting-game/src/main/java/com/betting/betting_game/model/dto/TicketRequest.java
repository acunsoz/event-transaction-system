package com.betting.betting_game.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TicketRequest {

    private Long userTicketId;
    private Long gameId;
    private String scorePrediction;

}
