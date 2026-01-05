package com.betting.betting_game.controller;

import com.betting.betting_game.model.dto.SaveBetRoundRequest;
import com.betting.betting_game.model.dto.UpdateBetRoundRequest;
import com.betting.betting_game.model.entity.BetRound;
import com.betting.betting_game.service.service.BetRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/betrounds")
public class BetRoundController {

    @Autowired
    private BetRoundService betRoundService;

    //@Autowired
    //private JwtTokenService jwtTokenService;

    @PostMapping("/create")
    public ResponseEntity<String> createBetRound(@RequestBody SaveBetRoundRequest saveBetRoundRequest) {
        try {
            BetRound createdBetRound = betRoundService.createBetRound(saveBetRoundRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("BetRound create successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<String> updateBetRoundUpdate(@PathVariable Long id, @RequestBody UpdateBetRoundRequest updateBetRoundRequest) {
        try {
            BetRound updated = betRoundService.updateBetround(id, updateBetRoundRequest);
            return ResponseEntity.ok(updateBetRoundRequest.toString());
            //return ResponseEntity.ok(updateBetRoundRequest.getStatus().toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status/planned")
    public ResponseEntity<String> updateBetRoundStatusPlanned(@PathVariable Long id/*, @RequestBody UpdateBetRoundStatusRequest updateBetRoundStatusRequest*/) {
        try {
            BetRound updatedStatus = betRoundService.updateBetRoundStatusPlanned(id/*,updateBetRoundStatusRequest*/);
            return ResponseEntity.ok("Status updated successfully");
            //return ResponseEntity.ok(updateBetRoundStatusRequest.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/actions/finish")
    public ResponseEntity<String> updateBetRoundStatusEnded(@PathVariable Long id /*, @RequestBody UpdateBetRoundStatusRequest updateBetRoundStatusRequest*/) {
        try {
            BetRound updatedStatus = betRoundService.updateBetRoundStatusEnded(id/*,updateBetRoundStatusRequest*/);
            return ResponseEntity.ok("Status updated successfully");
            //return ResponseEntity.ok(updateBetRoundStatusRequest.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
