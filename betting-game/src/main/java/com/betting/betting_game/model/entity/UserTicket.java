package com.betting.betting_game.model.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userticket_id")
    private long id;

    @Column(name = "userticket_create_datetime")
    private LocalDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(name = "userticket_betround")
    private BetRound betRound;

    @ManyToOne
    @JoinColumn(name = "userticket_user")
    private User user;

    //burda mappedby ticket sınıfındaki userTicket alanı, UserTicket sınıfındaki tickets koleksiyonunu yönetir
    //cascade = UserTicket nesnesi üzerinde yapılan işlemlerin Ticket nesnelerine de uygulanıp uygulanmayacağını belirtir. silme ekleme gibi
    //orphanRemoval özelliği, bir ilişkili nesne koleksiyonundan (bu örnekte tickets listesi) bir nesne çıkarıldığında bu nesnenin otomatik olarak silinip silinmeyeceğini belirler.
    @OneToMany(mappedBy = "userTicket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("tickets")
    private List<Ticket> tickets;


    public static UserTicket create(User user, BetRound betRound, LocalDateTime createdDateTime) {
        UserTicket userTicket = new UserTicket();
        userTicket.user = user;
        userTicket.betRound = betRound;
        userTicket.createdDateTime = createdDateTime;

        return userTicket;
    }

}
