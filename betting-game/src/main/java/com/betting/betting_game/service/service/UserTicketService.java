package com.betting.betting_game.service.service;

import com.betting.betting_game.model.dto.UserTicketRequest;
import com.betting.betting_game.model.entity.UserTicket;

import java.util.List;

public interface UserTicketService {
    UserTicket createUserTicket(UserTicketRequest userTicketRequest);

    UserTicket getUserTicketById(Long id);

    List<UserTicket> getAllUserTicket();

    UserTicket updateUserTicket(Long id, UserTicketRequest updateUserTicketRequest);

    void deleteUserTicket(Long id);

    //sonuclar
    void processUserTicket(Long userTicketId);
}
