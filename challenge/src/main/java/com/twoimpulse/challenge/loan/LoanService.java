package com.twoimpulse.challenge.loan;

import com.twoimpulse.challenge.loanstate.LoanStateEnum;
import com.twoimpulse.challenge.loanstate.LoanStateService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanStateService loanStateService;

    public LoanService(LoanRepository loanRepository, LoanStateService loanStateService) {
        this.loanRepository = loanRepository;
        this.loanStateService = loanStateService;
    }

    public Optional<Page<Loan>> getLoansByISBN(LoanSearch searchOptions) {

        Optional<Page<Loan>> loans;

        if(searchOptions.getLoanState() != null){
            loans = loanRepository.findAllByLibraryIdAndBookISBNAndLoanState(
                        searchOptions.getLibraryId(),
                        searchOptions.getId(),
                        searchOptions.getLoanState(),
                        searchOptions.getPaging()
            );
        }
        else{
            loans = loanRepository.findAllByLibraryIdAndBookISBN(
                        searchOptions.getLibraryId(),
                        searchOptions.getId(),
                        searchOptions.getPaging());
        }

        return loans;
    }

    public Optional<Page<Loan>> getLoansByLibraryId(LoanSearch searchOptions){

        Optional<Page<Loan>> loans;

        if(searchOptions.getLoanState() != null){
            loans = loanRepository.findAllByLibraryIdAndLoanState(
                    searchOptions.getLibraryId(),
                    searchOptions.getLoanState(),
                    searchOptions.getPaging());
        }
        else{
            loans = loanRepository.findAllByLibraryId(
                    searchOptions.getLibraryId(),
                    searchOptions.getPaging());
        }

        return loans;

    }

    public Optional<Page<Loan>> getLoansByLibraryCardId(LoanSearch searchOptions) {

        Optional<Page<Loan>> loans;

        if(searchOptions.getLoanState() != null){
            loans = loanRepository.findAllByLibraryIdAndLibraryCardIdAndLoanState(
                        searchOptions.getLibraryId(),
                        Long.parseLong(searchOptions.getId()),
                        searchOptions.getLoanState(),
                        searchOptions.getPaging());
        }
        else{
            loans = loanRepository.findAllByLibraryIdAndLibraryCardId(
                        searchOptions.getLibraryId(),
                        Long.parseLong(searchOptions.getId()),
                        searchOptions.getPaging());
        }

        return loans;

    }

    public Optional<Page<Loan>> getLoansByInventoryId(LoanSearch searchOptions) {
        Optional<Page<Loan>> loans;

        if(searchOptions.getLoanState() != null){
            loans = loanRepository.findAllByLibraryIdAndInventoryIdAndLoanState(
                        searchOptions.getLibraryId(),
                        Long.parseLong(searchOptions.getId()),
                        searchOptions.getLoanState(),
                        searchOptions.getPaging());
        }
        else{
            loans = loanRepository.findAllByLibraryIdAndInventoryId(
                        searchOptions.getLibraryId(),
                        Long.parseLong(searchOptions.getId()),
                        searchOptions.getPaging());
        }

        return loans;
    }

    public Optional<Page<Loan>> getLoansById(LoanSearch searchOptions) {
        Optional<Page<Loan>> loans;

        if(searchOptions.getLoanState() != null){
            loans = loanRepository.findAllByLibraryIdAndLoanIdAndLoanState(
                        searchOptions.getLibraryId(),
                        Long.parseLong(searchOptions.getId()),
                        searchOptions.getLoanState(),
                        searchOptions.getPaging());
        }
        else{
            loans = loanRepository.findAllByLibraryIdAndLoanId(
                        searchOptions.getLibraryId(),
                        Long.parseLong(searchOptions.getId()),
                        searchOptions.getPaging());
        }

        return loans;
    }


    public Optional<Loan> getLastEntryOfBookByInventoryId(long libraryId, long inventoryId){
        return loanRepository.findLastLoanByInventoryId(libraryId, inventoryId);
    }


    public void insertLoan(long libraryCardId, long inventoryId, LoanStateEnum ls) {
        long loanStateId = loanStateService.getLoanStateId(ls);
        loanRepository.insertLoan(libraryCardId, inventoryId, loanStateId);
    }
}
