package com.betting.betting_game.controller;

import com.betting.betting_game.model.dto.UserTicketRequest;
import com.betting.betting_game.model.entity.UserTicket;
import com.betting.betting_game.service.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userticket")
public class UserTicketController {

    @Autowired
    private UserTicketService userTicketService;

    @PostMapping("/create")
    public ResponseEntity<UserTicket> createUserTicket(@RequestBody UserTicketRequest userTicketRequest) {
        try {

            //userticket olusturuyoruz
            UserTicket userTicket = userTicketService.createUserTicket(userTicketRequest);

            return new ResponseEntity<>(userTicket, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
