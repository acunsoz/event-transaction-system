package com.betting.betting_game.service.service;

import com.betting.betting_game.model.dto.TicketMailSendRequest;

import java.util.List;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    String createMessage(String title, List<TicketMailSendRequest> tickets);
}
