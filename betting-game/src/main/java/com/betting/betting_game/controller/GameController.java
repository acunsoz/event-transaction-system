package com.betting.betting_game.controller;

import com.betting.betting_game.model.dto.GameScoreRequest;
import com.betting.betting_game.model.dto.SaveGameRequest;
import com.betting.betting_game.model.dto.TicketResultRequest;
import com.betting.betting_game.model.entity.Game;
import com.betting.betting_game.service.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestBody SaveGameRequest saveGameRequest) {
        try {
            // SaveGameRequest içindeki bilgiler ile Game nesnesini oluştu
            Game game = gameService.createGame(saveGameRequest);

            // Başarılı bir şekilde oluşturulduğunda HTTP 201 döndürüyoruz
            return new ResponseEntity<>(game, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Eğer BetRound bulunamazsa hata döndürüyoruz
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/playfilter")
    public ResponseEntity<List<Game>> getGamesByBetRoundStatusPlayDateTimeAndId(
            @RequestParam("status") String status,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam("id") Optional<Long> id) {

        try {
            List<Game> games = gameService.findAllByBetRound_StatusAndBetRound_PlayDateTimeAfterAndId(status, dateTime, id);
            return ResponseEntity.ok(games);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/{gameId}/score")
    public ResponseEntity<String> updateScore(@PathVariable Long gameId, @RequestBody GameScoreRequest gameScoreRequest) {
        try {

            gameService.updateGameScore(gameId, gameScoreRequest);

            return new ResponseEntity<>("Game score updated successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            // Eğer hata oluşursa 400 (Bad Request) ile hata mesajını döndür
            return new ResponseEntity<>("Error updating game score: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{gameId}/results")
    public ResponseEntity<?> getTicketResults(@PathVariable long gameId) {
        try {
            List<TicketResultRequest> ticketResults = gameService.compareTicketResult(gameId);

            return new ResponseEntity<>(ticketResults, HttpStatus.OK);
        } catch (Exception e) {
            // Eğer hata oluşursa 400 (Bad Request) ile hata mesajını döndür
            return new ResponseEntity<>("Error retrieving ticket results: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
