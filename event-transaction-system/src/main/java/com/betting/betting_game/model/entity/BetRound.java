package com.betting.betting_game.model.entity;

import com.betting.betting_game.model.dto.UpdateBetRoundRequest;
import com.betting.betting_game.model.entity.enums.BetRoundStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "betround")
public class BetRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "betround_id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "betround_playdatetime")
    private LocalDateTime playDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "betround_status")
    private BetRoundStatus status;

    @OneToMany(mappedBy = "betRound", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;

    public static BetRound create(String title, LocalDateTime playDateTime) {
        BetRound betRound = new BetRound();
        betRound.title = title;
        betRound.playDateTime = playDateTime;
        betRound.status = BetRoundStatus.DRAFT;
        betRound.games = null;
        return betRound;
    }

    public BetRound update(UpdateBetRoundRequest updateBetRoundRequest) {
        this.title = updateBetRoundRequest.getTitle();
        this.playDateTime = updateBetRoundRequest.getPlayDateTime();
        return this;
    }

    public BetRound plan() {
        this.status = BetRoundStatus.PLANNED;
        return this;
    }

    public BetRound finish() {
        this.status = BetRoundStatus.ENDED;
        return this;
    }


}
