package com.betting.betting_game.service.impl;

import org.springframework.transaction.annotation.Transactional;
import com.betting.betting_game.model.dto.TicketMailSendRequest;
import com.betting.betting_game.model.dto.UserTicketRequest;
import com.betting.betting_game.model.entity.*;
import com.betting.betting_game.repository.BetRoundRepository;
import com.betting.betting_game.repository.TicketRepository;
import com.betting.betting_game.repository.UserRepository;
import com.betting.betting_game.repository.UserTicketRepository;
import com.betting.betting_game.service.service.EmailService;
import com.betting.betting_game.service.service.UserTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTicketServiceImpl implements UserTicketService {
    private final UserTicketRepository userTicketRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final BetRoundRepository betRoundRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public UserTicket createUserTicket(UserTicketRequest userTicketRequest) {

        User user = userRepository.findByMail(userTicketRequest.getMail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BetRound betRound = betRoundRepository.findById(userTicketRequest.getBetRoundId())
                .orElseThrow(() -> new RuntimeException("Betround not found"));

        UserTicket userTicketCreate = UserTicket.create(user, betRound, LocalDateTime.now());

        return userTicketRepository.save(userTicketCreate);
    }

    @Override
    public UserTicket getUserTicketById(Long id) {
        return null;
    }

    @Override
    public List<UserTicket> getAllUserTicket() {
        return List.of();
    }

    @Override
    public UserTicket updateUserTicket(Long id, UserTicketRequest updateUserTicketRequest) {
        return null;
    }

    @Override
    public void deleteUserTicket(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public void processUserTicket(Long userTicketId) {

        UserTicket userTicket = userTicketRepository.findById(userTicketId)
                .orElseThrow(() -> new RuntimeException("UserTicket not found"));

        //userticket içindeki ticketlar
        List<Ticket> tickets = userTicket.getTickets();

        //kazanan ve kaybedilen oyun listesi
        List<TicketMailSendRequest> ticketResults = new ArrayList<>();

        for (Ticket ticket : tickets) {
            Game game = ticket.getGame();
            if (game.getScore() != null && ticket.getScorePrediction().equals(game.getScore())) {
                ticket.setIsWin(true);

                TicketMailSendRequest winningTicketDto = new TicketMailSendRequest(
                        game.getFirstTeamName(),
                        game.getSecondTeamName(),
                        game.getScore(),
                        ticket.getScorePrediction(),
                        true
                );
                ticketResults.add(winningTicketDto);
            } else {
                ticket.setIsWin(false);

                TicketMailSendRequest losingTicketDTO = new TicketMailSendRequest(
                        game.getFirstTeamName(),
                        game.getSecondTeamName(),
                        game.getScore(),
                        ticket.getScorePrediction(),
                        false
                );
                ticketResults.add(losingTicketDTO);
            }
            ticketRepository.save(ticket);
        }
        String resultMessage = emailService.createMessage("Bilet Sonuçları", ticketResults);

        emailService.sendEmail(userTicket.getUser().getMail(), "Bilet Sonucları", resultMessage);
    }
}
