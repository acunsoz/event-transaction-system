package com.betting.betting_game.service.impl;

import com.betting.betting_game.exception.BetRoundNotFoundException;
import com.betting.betting_game.model.dto.GameScoreRequest;
import com.betting.betting_game.model.dto.SaveGameRequest;
import com.betting.betting_game.model.dto.TicketResultRequest;
import com.betting.betting_game.model.entity.BetRound;
import com.betting.betting_game.model.entity.Game;
import com.betting.betting_game.model.entity.Ticket;
import com.betting.betting_game.model.entity.enums.BetRoundStatus;
import com.betting.betting_game.repository.BetRoundRepository;
import com.betting.betting_game.repository.GameRepository;
import com.betting.betting_game.repository.TicketRepository;
import com.betting.betting_game.service.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private BetRoundRepository betRoundRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public Game createGame(SaveGameRequest saveGameRequest) {

        BetRound betRound = betRoundRepository.findById(saveGameRequest.getBetRoundId())
                .orElseThrow(() -> new RuntimeException("Betround not found"));

        Game game = Game.create(saveGameRequest.getFirstTeamName(), saveGameRequest.getSecondTeamName(), betRound);

        return gameRepository.save(game);
    }

    @Override
    public Game getGameById(Long id) {
        return null;
    }

    @Override
    public List<Game> getAllGame() {
        return List.of();
    }

    @Override
    public Game updateGame(Long id, Game game) {
        return null;
    }

    @Override
    public void deleteGame(Long id) {

    }

    @Override
    public List<Game> findAllByBetRound_StatusAndBetRound_PlayDateTimeAfterAndId(String status, LocalDateTime dateTime, Optional<Long> id) {

        List<Game> gamePlannedBetRoundsAndDateTime;

        if (id.isPresent()) {
            //planned ve localdatetime simdiden playdate time a kadar olanları listeler ve id ye göre filtreler
            gamePlannedBetRoundsAndDateTime = gameRepository.findAllByBetRound_StatusAndBetRound_PlayDateTimeAfterAndId(status, dateTime, id.get());
        } else {
            //planned ve localdatetime simdiden playdate time a kadar olanları listeler
            gamePlannedBetRoundsAndDateTime = gameRepository.findAllByBetRound_StatusAndBetRound_PlayDateTimeAfter(status, dateTime);
        }

        if (gamePlannedBetRoundsAndDateTime.isEmpty()) {
            throw new BetRoundNotFoundException("No BetRounds found with PLANNED status.");
        }
        return gamePlannedBetRoundsAndDateTime;

    }

    @Override
    public void updateGameScore(Long gameId, GameScoreRequest gameScoreRequest) {

        //game  id ile bul
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        game.updateScore(gameScoreRequest.getScore());

        gameRepository.save(game);

        //compareTicketResult(gameId);

    }

    //Buaraya status ended olamdan erişim sağlanmamalı
    @Override
    public List<TicketResultRequest> compareTicketResult(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        if (!game.getBetRound().getStatus().equals(BetRoundStatus.ENDED)) {
            throw new IllegalArgumentException("The games are not over yet");
        }


        List<TicketResultRequest> ticketResult = new ArrayList<>();

        //oyunla iliskili biletleri al
        List<Ticket> tickets = ticketRepository.findByGameId(gameId);
        //Game game = gameRepository.findById(gameId).orElseThrow();

        for (Ticket ticket : tickets) {
            TicketResultRequest ticketResultRequest = new TicketResultRequest();
            ticketResultRequest.setTicketId(ticket.getId());

            // Eğer biletin tahmini skor, maçın gerçek skoruna eşitse bilet kazanır
            ticketResultRequest.setIsWin(ticket.getScorePrediction().equals(game.getScore()));

            //biletin soucu veritabanına kaydedilir

            ticket.setIsWin(ticketResultRequest.getIsWin());
            ticketRepository.save(ticket);

            ticketResult.add(ticketResultRequest);

        }


        return ticketResult;
    }
}
