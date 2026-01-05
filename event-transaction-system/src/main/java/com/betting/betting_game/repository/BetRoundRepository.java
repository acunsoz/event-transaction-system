package com.betting.betting_game.repository;

import com.betting.betting_game.model.entity.BetRound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRoundRepository extends JpaRepository<BetRound, Long> {

    BetRound getById(Long id);
    //List<BetRound> findAllByStatus(BetRoundStatus status);

}
