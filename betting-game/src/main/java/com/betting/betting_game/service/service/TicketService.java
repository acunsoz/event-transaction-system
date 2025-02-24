package com.betting.betting_game.service.service;


import com.betting.betting_game.model.dto.TicketRequest;
import com.betting.betting_game.model.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket createTicket(TicketRequest ticketRequest);

    Ticket getTicketById(Long id);

    //butun oynanan playdatetime oncesi ve planned olanlar
    List<Ticket> getAllPlayTicket();

    //List<Ticket> getAllPlayTicketGames();
    //sadece ticket game değişebilir
    Ticket updateTicket(Long id, Ticket ticket);

    void deleteTicket(Long id);
}
