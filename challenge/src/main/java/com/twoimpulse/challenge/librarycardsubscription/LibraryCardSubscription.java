package com.twoimpulse.challenge.librarycardsubscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "library_card_subscription")
@Entity(name = "library_card_subscription")
@Getter
@Setter
public class LibraryCardSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lcs_id")
    private long libraryCardSubscriptionId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lc_id", nullable = false)
    private LibraryCard libraryCard;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

    public LibraryCardSubscription(){}

    public LibraryCardSubscription(LibraryCard libraryCard, LocalDateTime startDate){
        this.libraryCard = libraryCard;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(1);
    }

    public LibraryCardSubscription(LibraryCard libraryCard, LocalDateTime startDate, LocalDateTime endDate){
        this.libraryCard = libraryCard;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}