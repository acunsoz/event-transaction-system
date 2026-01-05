package com.betting.betting_game.model.entity;


import com.betting.betting_game.model.dto.TicketMailSendRequest;
import com.betting.betting_game.model.entity.enums.Score;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    @JsonProperty("ticketid")
    private long id;

    @JsonProperty("iswinticket")
    @Column(name = "is_win")
    @Nullable
    private Boolean isWin;

    //Burda ticket hangi userticket ile ilişkilendirildiğini belirtmek için ManyToOne eklendi
    @ManyToOne
    @JoinColumn(name = "user_ticket_id")
    private UserTicket userTicket;

    // Her Ticket bir Game'e ait olmalı
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "score_prediction")
    @JsonProperty("score_prediction")
    @Enumerated(EnumType.STRING)
    private Score scorePrediction;

    public static Ticket create(UserTicket userTicket, Game game, Score scorePrediction) {

        Ticket ticket = new Ticket();
        ticket.isWin = null;
        ticket.userTicket = userTicket;
        ticket.game = game;
        ticket.scorePrediction = scorePrediction;
        return ticket;

    }

    public TicketMailSendRequest ticketSending() {
        TicketMailSendRequest ticketMail = new TicketMailSendRequest();
        ticketMail.setIsWin(this.isWin);
        ticketMail.setScorePrediction(this.scorePrediction);
        ticketMail.setFirstTeamName(this.game.getFirstTeamName());
        ticketMail.setSecondTeamName(this.game.getSecondTeamName());
        ticketMail.setScore(this.game.getScore());
        return ticketMail;
    }

}
