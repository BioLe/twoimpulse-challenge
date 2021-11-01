package com.twoimpulse.challenge.loan;

import com.twoimpulse.challenge.exception.ApiRequestException;
import com.twoimpulse.challenge.exception.EntityNotAvailable;
import com.twoimpulse.challenge.exception.LibraryCardExpiredException;
import com.twoimpulse.challenge.librarycardsubscription.LibraryCardSubscriptionService;
import com.twoimpulse.challenge.loanstate.LoanState;
import com.twoimpulse.challenge.loanstate.LoanStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/library/{libraryId}/loans")
public class LoanController {


    private final LoanService loanService;
    private final LibraryCardSubscriptionService libraryCardSubscriptionService;

    public LoanController(LoanService loanService, LibraryCardSubscriptionService libraryCardSubscriptionService) {
        this.loanService = loanService;
        this.libraryCardSubscriptionService = libraryCardSubscriptionService;
    }

    @Transactional
    @PostMapping("/borrow/{inventoryId}")
    public void loanBook(
                    @PathVariable("libraryId") long libraryId,
                    @PathVariable("inventoryId") long inventoryId,
                    @RequestParam("libraryCardId") long libraryCardId){

        //1. Check if user library card is valid
        Optional<Boolean> isValid = libraryCardSubscriptionService.isSubscriptionActive(libraryCardId);
        if(isValid.isEmpty()){ // Never subbed
            throw new LibraryCardExpiredException("No subscription found");
        } else if(!isValid.get()){ //Subs exist, just expired
            throw new LibraryCardExpiredException("Library card expired");
        }

        //2. Check if book user is trying to borrow is available
        Optional<Loan> loan = loanService.getLastEntryOfBookByInventoryId(libraryId, inventoryId);
        if(loan.isPresent()){
            LoanStateEnum loanState = loan.get().getLoanState().getState();
            if(loanState == LoanStateEnum.BORROWED)
                throw new EntityNotAvailable("Book is currently loaned.");
        }

        //3. Borrow the book
        loanService.insertLoan(libraryCardId, inventoryId, LoanStateEnum.BORROWED);
    }

    @Transactional
    @PostMapping("/return/{inventoryId}")
    public void returnBook(
            @PathVariable("libraryId") long libraryId,
            @PathVariable("inventoryId") long inventoryId,
            @RequestParam("libraryCardId") long libraryCardId){

        loanService.insertLoan(libraryCardId, inventoryId, LoanStateEnum.RETURNED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Page<Loan>> getLoans(@PathVariable("libraryId") long libraryId,
                                               @PathVariable("id") String id,
                                               @RequestParam(defaultValue = "loanid") String idType,
                                               @RequestParam(required = false) String loanState,
                                               @RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "date") String sortBy){


        LoanStateEnum loanStateEnum = null;
        if(loanState != null){
            try{
                loanStateEnum = LoanStateEnum.valueOf(loanState.toUpperCase());
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid loanState.");
            }
        }

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        LoanSearch searchOptions = new LoanSearch(libraryId, id, loanStateEnum, paging);

        Optional<Page<Loan>> loans;

        switch (idType.toLowerCase()) {
            case "isbn": //ISBN
                loans = loanService.getLoansByISBN(searchOptions);
                break;

            case "librarycardid": //LibraryCardId
                loans = loanService.getLoansByLibraryCardId(searchOptions);
                break;

            case "inventoryid": //InventoryId
                loans = loanService.getLoansByInventoryId(searchOptions);
                break;

            case "loanid": // LoanId
                loans = loanService.getLoansById(searchOptions);
                break;

            case "libraryid":
                loans = loanService.getLoansByLibraryId(searchOptions);
                break;

            default:
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid idType");
        }

        if(loans.isPresent()) return ResponseEntity.ok(loans.get());
        else return ResponseEntity.notFound().build();
    }




}
