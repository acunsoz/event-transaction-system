package com.betting.betting_game.repository;

import com.betting.betting_game.model.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    List<UserTicket> findByBetRound_Id(Long id);
}
