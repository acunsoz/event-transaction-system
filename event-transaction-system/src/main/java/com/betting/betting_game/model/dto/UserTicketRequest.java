package com.betting.betting_game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTicketRequest {
    private String mail;
    private Long betRoundId;
}
