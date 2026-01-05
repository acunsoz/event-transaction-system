package com.betting.betting_game.service.impl;

import com.betting.betting_game.model.dto.TicketMailSendRequest;
import com.betting.betting_game.service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmaiServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Mail gönderilemedi");
        }

    }

    @Override
    public String createMessage(String title, List<TicketMailSendRequest> tickets) {

        StringBuilder message = new StringBuilder();

        message.append("<table border='1' cellpadding='5' cellspacing='0'>")
                .append("<tr>")
                .append("<th>Takım</th>")
                .append("<th>Skor</th>")
                .append("<th>Tahmin</th>")
                .append("<th>Durum</th>")
                .append("</tr>");

        for (TicketMailSendRequest ticket : tickets) {
            String status = ticket.getIsWin() ? "Kazandı" : "Kaybetti";
            String statusColor = ticket.getIsWin() ? "background-color: green; color: white;" : "background-color: red; color: white;";
            message.append("<tr>")
                    .append("<td>").append(ticket.getFirstTeamName()).append("-").append(ticket.getSecondTeamName()).append("</td>")
                    .append("<td>").append(ticket.getScore()).append("</td>")
                    .append("<td>").append(ticket.getScorePrediction()).append("</td>")
                    .append("<td style='").append(statusColor).append("'>").append(status).append("</td>")
                    .append("</tr>");
        }

        message.append("</table>");

        return message.toString();
    }
}
