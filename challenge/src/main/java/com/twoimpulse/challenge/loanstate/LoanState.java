package com.twoimpulse.challenge.loanstate;

import com.twoimpulse.challenge.loan.Loan;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "loan_state")
@Entity(name = "loan_state")
@Getter @Setter
public class LoanState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ls_id")
    private long loanStateID;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private LoanStateEnum state;

    public LoanState(){}

    public LoanState(LoanStateEnum state) {
        this.state = state;
    }
}