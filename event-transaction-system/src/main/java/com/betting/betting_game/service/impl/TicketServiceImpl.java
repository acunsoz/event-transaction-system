package com.betting.betting_game.service.impl;

import com.betting.betting_game.exception.BetRoundNotFoundException;
import com.betting.betting_game.model.dto.TicketRequest;
import com.betting.betting_game.model.entity.Game;
import com.betting.betting_game.model.entity.Ticket;
import com.betting.betting_game.model.entity.UserTicket;
import com.betting.betting_game.model.entity.enums.Score;
import com.betting.betting_game.repository.GameRepository;
import com.betting.betting_game.repository.TicketRepository;
import com.betting.betting_game.repository.UserTicketRepository;
import com.betting.betting_game.service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Autowired
    private GameRepository gameRepository;


    @Override
    public Ticket createTicket(TicketRequest ticketRequest) {

        UserTicket userTicket = userTicketRepository.findById(ticketRequest.getUserTicketId())
                .orElseThrow(() -> new RuntimeException("UserTicket not found"));

        Game game = gameRepository.findById(ticketRequest.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Score score = Score.valueOf(ticketRequest.getScorePrediction());

        Ticket ticket = Ticket.create(userTicket, game, score);

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return null;
    }

    @Override
    public List<Ticket> getAllPlayTicket() {
        List<Ticket> plannedBetRoundsAndDateTime = ticketRepository.findAllByUserTicket_BetRound_StatusAndUserTicket_BetRound_PlayDateTimeAfter("PLANNED", LocalDateTime.now());

        if (plannedBetRoundsAndDateTime.isEmpty()) {
            throw new BetRoundNotFoundException("No BetRounds found with PLANNED status.");
        }

        return plannedBetRoundsAndDateTime;
    }

    @Override
    public Ticket updateTicket(Long id, Ticket ticket) {
        return null;
    }

    @Override
    public void deleteTicket(Long id) {

    }
}
