package com.betting.betting_game.model.dto;

import com.betting.betting_game.model.entity.enums.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketMailSendRequest {

    private String firstTeamName;
    private String secondTeamName;
    private Score score;
    private Score scorePrediction;
    private Boolean isWin;
}
