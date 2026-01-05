package com.betting.betting_game.service.service;


import com.betting.betting_game.model.dto.SaveBetRoundRequest;
import com.betting.betting_game.model.dto.UpdateBetRoundRequest;
import com.betting.betting_game.model.entity.BetRound;

import java.util.List;

public interface BetRoundService {
    BetRound createBetRound(SaveBetRoundRequest saveBetRoundRequest);

    BetRound getBetRoundById(Long id);

    List<BetRound> getAllBetRound();

    BetRound updateBetround(Long id, UpdateBetRoundRequest updateBetRoundRequest);

    BetRound updateBetRoundStatusPlanned(Long id/*, UpdateBetRoundStatusRequest updateBetRoundStatusRequest*/);

    BetRound updateBetRoundStatusEnded(Long id /*,UpdateBetRoundStatusRequest updateBetRoundStatusRequest*/);

    void deleteBetRound(Long id);
}
