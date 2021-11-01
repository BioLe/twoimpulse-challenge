package com.twoimpulse.challenge.loanstate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStateRepository extends JpaRepository<LoanState, Integer> {

    @Query("select l.loanStateID from loan_state l where l.state = ?1")
    long findIdByState(LoanStateEnum loanState);
}
