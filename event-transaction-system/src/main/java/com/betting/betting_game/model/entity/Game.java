package com.betting.betting_game.model.entity;

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
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    @JsonProperty("gameid")
    private long id;

    @Column(name = "first_team_name")
    @JsonProperty("firstteamname")
    private String firstTeamName;

    @Column(name = "second_team_name")
    @JsonProperty("secondteamname")
    private String secondTeamName;

    @Nullable
    @Column(name = "score")
    @JsonProperty("score")
    @Enumerated(EnumType.STRING)
    private Score score;

	/*
	@Nullable
	@Column(name = "is_win_game")
	@JsonProperty("iswingame")
	private Boolean isWin;
	 */

    //betround nesnesi birden fazla game nesnesi içerebilir.
    @ManyToOne
    @JoinColumn(name = "bet_round_id")
    private BetRound betRound;

    public static Game create(String firstTeamName, String secondTeamName, BetRound betRound) {

        Game game = new Game();
        game.firstTeamName = firstTeamName;
        game.secondTeamName = secondTeamName;
        game.score = null;
        game.betRound = betRound;
        return game;

    }

    public void updateScore(Score score) {
        this.score = score;
    }


}
