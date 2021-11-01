package com.twoimpulse.challenge.loan;

import com.twoimpulse.challenge.loanstate.LoanStateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.inventory.book.isbn = ?2")
    Optional<Page<Loan>> findAllByLibraryIdAndBookISBN(long libraryId, String ISBN, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.inventory.book.isbn = ?2 and l.loanState.state = ?3 ")
    Optional<Page<Loan>> findAllByLibraryIdAndBookISBNAndLoanState(long libraryId, String ISBN, LoanStateEnum state, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.libraryCard.libraryCardId = ?2")
    Optional<Page<Loan>> findAllByLibraryIdAndLibraryCardId(long libraryId, long libraryCardID, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.libraryCard.libraryCardId = ?2 and l.loanState.state = ?3")
    Optional<Page<Loan>> findAllByLibraryIdAndLibraryCardIdAndLoanState(long libraryId, long libraryCardID, LoanStateEnum state, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.inventory.inventoryId = ?2")
    Optional<Page<Loan>> findAllByLibraryIdAndInventoryId(long libraryId, long inventoryId, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.inventory.inventoryId = ?2 and l.loanState.state = ?3")
    Optional<Page<Loan>> findAllByLibraryIdAndInventoryIdAndLoanState(long libraryId, long inventoryId, LoanStateEnum state, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.loanId = ?2")
    Optional<Page<Loan>> findAllByLibraryIdAndLoanId(long libraryId, long loanId, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.loanId = ?2 and l.loanState.state = ?3")
    Optional<Page<Loan>> findAllByLibraryIdAndLoanIdAndLoanState(long libraryId, long loanId, LoanStateEnum state, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1")
    Optional<Page<Loan>> findAllByLibraryId(long libraryId, Pageable paging);

    @Query("select l from loan l where l.inventory.library.L_ID = ?1 and l.loanState.state = ?2")
    Optional<Page<Loan>> findAllByLibraryIdAndLoanState(long libraryId,LoanStateEnum loanState, Pageable paging);

    @Query(
            value = "SELECT * FROM loan l INNER JOIN inventory i ON l.i_id = i.i_id " +
                    "WHERE i.i_id = :inventoryId " +
                    "AND i.l_id = :libraryId " +
                    "ORDER BY l.date DESC limit 1",
            nativeQuery = true
    )
    Optional<Loan> findLastLoanByInventoryId(long libraryId, long inventoryId);

    @Modifying
    @Query(
            value =
                    "insert into loan (i_id, ls_id, lc_id, date) values (:inventoryId, :loanStateId, :libraryCardId, NOW())",
            nativeQuery = true)
    void insertLoan(long libraryCardId, long inventoryId, long loanStateId);
}
