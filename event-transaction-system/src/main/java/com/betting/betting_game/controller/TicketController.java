package com.betting.betting_game.controller;

import com.betting.betting_game.model.dto.TicketRequest;
import com.betting.betting_game.model.entity.Ticket;
import com.betting.betting_game.service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketService.createTicket(ticketRequest);

            // Başarılı bir şekilde oluşturulduğunda HTTP 201 döndürüyoruz
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Eğer BetRound bulunamazsa hata döndürüyoruz
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
