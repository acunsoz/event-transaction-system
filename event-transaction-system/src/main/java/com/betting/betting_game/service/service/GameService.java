package com.betting.betting_game.service.service;


import com.betting.betting_game.model.dto.GameScoreRequest;
import com.betting.betting_game.model.dto.SaveGameRequest;
import com.betting.betting_game.model.dto.TicketResultRequest;
import com.betting.betting_game.model.entity.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GameService {
    Game createGame(SaveGameRequest saveGameRequest);

    Game getGameById(Long id);

    List<Game> getAllGame();

    Game updateGame(Long id, Game game);

    void deleteGame(Long id);

    //status ve playdatetime ve istege bağlı id ile listeleme
    List<Game> findAllByBetRound_StatusAndBetRound_PlayDateTimeAfterAndId(String status, LocalDateTime dateTime, Optional<Long> id);

    //score guncelleme biletle karsılastırma
    void updateGameScore(Long gameId, GameScoreRequest gameScoreRequest);

    List<TicketResultRequest> compareTicketResult(Long gameId);


}
