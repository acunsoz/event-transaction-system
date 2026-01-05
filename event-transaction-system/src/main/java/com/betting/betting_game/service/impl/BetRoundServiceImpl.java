package com.betting.betting_game.service.impl;

import com.betting.betting_game.exception.InvalidCredentialsException;
import com.betting.betting_game.model.dto.SaveBetRoundRequest;
import com.betting.betting_game.model.dto.UpdateBetRoundRequest;
import com.betting.betting_game.model.entity.BetRound;
import com.betting.betting_game.model.entity.Game;
import com.betting.betting_game.model.entity.UserTicket;
import com.betting.betting_game.model.entity.enums.BetRoundStatus;
import com.betting.betting_game.repository.BetRoundRepository;
import com.betting.betting_game.repository.GameRepository;
import com.betting.betting_game.repository.UserTicketRepository;
import com.betting.betting_game.service.service.BetRoundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BetRoundServiceImpl implements BetRoundService {

    private BetRoundRepository betRoundRepository;

    private GameRepository gameRepository;
    private UserTicketRepository userTicketRepository;

    private UserTicketServiceImpl userTicketService;

    @Override
    public BetRound createBetRound(SaveBetRoundRequest saveBetRoundRequest) {
        return betRoundRepository.save(BetRound.create(saveBetRoundRequest.getTitle(), saveBetRoundRequest.getPlayDateTime()));

    }

    @Override
    public BetRound getBetRoundById(Long id) {

        return betRoundRepository.getById(id);
    }

    // Burayı tamamlamayı unutma
    @Override
    public List<BetRound> getAllBetRound() {

        return List.of();
    }

    @Override
    public BetRound updateBetround(Long id, UpdateBetRoundRequest betRound) {
        //veriler dolu mu kontrol edilir
        validateBetRound(betRound);
        //veritabanında var mı
        BetRound existingBetRound = betRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BetRound not found with id: " + id));

        return betRoundRepository.save(existingBetRound.update(betRound));
    }

    @Override
    public BetRound updateBetRoundStatusPlanned(Long id/*, UpdateBetRoundStatusRequest updateBetRoundStatusRequest*/) {
        BetRound existingBetRound = betRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BetRound not found with id: " + id));

        // Eğer status DRAFT ise, güncellenmeden önce games listesi dolu olmalı
        if (existingBetRound.getStatus() != BetRoundStatus.DRAFT) {
            throw new IllegalArgumentException("Cannot update status to PLANNED");
        }

        //geçmiş zaman kullanılamaz
        if (existingBetRound.getPlayDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("PlayDateTime cannot be in the past.");
        }

        // Eğer status DRAFT ise, güncellenmeden önce games listesi dolu olmalı
        if (existingBetRound.getGames().isEmpty()) {
            throw new IllegalArgumentException("Games List is empty.");
        }

        return betRoundRepository.save(existingBetRound.plan());
    }

    @Override
    public BetRound updateBetRoundStatusEnded(Long id/*, UpdateBetRoundStatusRequest updateBetRoundStatusRequest*/) {
        BetRound existingBetRound = betRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BetRound not found with id: " + id));

        //oyun oynanacagı tarih ileri bir tarihse hata verir
        if (existingBetRound.getPlayDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("The games are not over yet");
        }

        List<Game> games = gameRepository.findAllByBetRound_Id(id);
        Boolean allScoresFilled = true;
        for (Game game : games) {
            if (game.getScore() == null) {
                allScoresFilled = false;
                break;
            }
        }
        if (!allScoresFilled) {
            throw new IllegalArgumentException("All games must have a score before finalizing the BetRound.");
        }

        List<UserTicket> userTickets = userTicketRepository.findByBetRound_Id(id);
        // burdaki listeyi processUserTicket içinde kontrol edecez
        for (UserTicket userTicket : userTickets) {
            userTicketService.processUserTicket(userTicket.getId());
        }


        return betRoundRepository.save(existingBetRound.finish());
    }


    @Override
    public void deleteBetRound(Long id) {

        betRoundRepository.deleteById(id);
    }

    private void validateBetRound(UpdateBetRoundRequest updateBetRoundRequest) {
        if (updateBetRoundRequest.getTitle() == null || updateBetRoundRequest.getTitle().trim().isEmpty()) {
            throw new InvalidCredentialsException("Title is required");
        }
        if (updateBetRoundRequest.getPlayDateTime() == null) {
            throw new InvalidCredentialsException("PlayDateTime is required");
        }
    }


}
