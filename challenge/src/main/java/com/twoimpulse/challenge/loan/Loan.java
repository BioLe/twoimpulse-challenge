package com.twoimpulse.challenge.loan;

import com.twoimpulse.challenge.inventory.Inventory;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import com.twoimpulse.challenge.loanstate.LoanState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "loan")
@Entity(name = "loan")
@Getter
@Setter
@ToString
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "loan_id")
    private long loanId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_id", nullable = false)
    private Inventory inventory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ls_id", nullable = false)
    private LoanState loanState;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lc_id", nullable = false)
    private LibraryCard libraryCard;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    //@CreationTimestamp //Not active for testing purposes
    private LocalDateTime date;

    public Loan(){}

    public Loan(Inventory inventory, LoanState loanState, LibraryCard libraryCard) {
        this.inventory = inventory;
        this.loanState = loanState;
        this.libraryCard = libraryCard;
    }

    public Loan(Inventory inventory, LoanState loanState, LibraryCard libraryCard, LocalDateTime date) {
        this.inventory = inventory;
        this.loanState = loanState;
        this.libraryCard = libraryCard;
        this.date = date;
    }


}
