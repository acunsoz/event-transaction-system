package com.betting.betting_game.repository;

import com.betting.betting_game.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUserTicket_BetRound_StatusAndUserTicket_BetRound_PlayDateTimeAfter(String status, LocalDateTime dateTime);

    List<Ticket> findByGameId(Long gameId);

}
