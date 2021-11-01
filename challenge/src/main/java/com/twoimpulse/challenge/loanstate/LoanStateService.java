package com.twoimpulse.challenge.loanstate;

import org.springframework.stereotype.Service;

@Service
public class LoanStateService {

    public LoanStateRepository loanStateRepository;

    public LoanStateService(LoanStateRepository loanStateRepository){
        this.loanStateRepository = loanStateRepository;
    }

    public long getLoanStateId(LoanStateEnum loanStateEnum){
        return loanStateRepository.findIdByState(loanStateEnum);
    }

}
