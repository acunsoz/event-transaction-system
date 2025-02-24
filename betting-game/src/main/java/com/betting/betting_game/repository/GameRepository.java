package com.betting.betting_game.repository;

import com.betting.betting_game.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByBetRound_StatusAndBetRound_PlayDateTimeAfter(String status, LocalDateTime dateTime);

    //id ye göre filtrelenecekse
    List<Game> findAllByBetRound_StatusAndBetRound_PlayDateTimeAfterAndId(String status, LocalDateTime dateTime, Long id);

    //betround is sine göre tüm oyunları getirir
    List<Game> findAllByBetRound_Id(Long betroundId);
}
