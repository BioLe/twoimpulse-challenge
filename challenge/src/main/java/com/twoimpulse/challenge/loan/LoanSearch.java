package com.twoimpulse.challenge.loan;

import com.twoimpulse.challenge.loanstate.LoanStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class LoanSearch {

    private long libraryId;
    private String id;
    private LoanStateEnum loanState;
    private Pageable paging;
}
