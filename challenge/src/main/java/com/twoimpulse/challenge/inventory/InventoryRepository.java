package com.twoimpulse.challenge.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT i FROM inventory i WHERE i.inventoryId = ?1 AND i.library.L_ID = ?2")
    Optional<Inventory> findByISBNAndLibraryId(long inventoryId, long libraryId);

    @Query("Select i FROM inventory i WHERE i.library.L_ID = ?1")
    Optional<Page<Inventory>> findAllByLibraryId(long libraryId, Pageable paging);

    @Query(value =
            "SELECT * FROM inventory i WHERE i.l_id = :libraryId AND i.i_id " +
                    "NOT IN (SELECT inventoryId " +
                    "FROM (SELECT latestEntriesPerBook.l_id, ls.state, l2.i_id as inventoryId " +
                    "FROM (select max(loan_id) as l_id, i_id FROM loan l GROUP BY i_id) latestEntriesPerBook " +
                    "INNER JOIN loan l2 ON latestEntriesPerBook.l_id = l2.loan_id " +
                    "INNER JOIN loan_state ls ON l2.ls_id = ls.ls_id " +
                    "WHERE ls.state = 'BORROWED'" +
                    ") unavailableBooks) /*#{#paging}*/",
            nativeQuery = true)
    Optional<Page<Inventory>> findAvailableInventory(long libraryId, Pageable paging);

    @Query(value =
            "SELECT * FROM inventory i WHERE i.l_id = :libraryId AND i.i_id " +
                    "IN (SELECT inventoryId " +
                    "FROM (SELECT latestEntriesPerBook.l_id, ls.state, l2.i_id as inventoryId " +
                    "FROM (select max(loan_id) as l_id, i_id FROM loan l GROUP BY i_id) latestEntriesPerBook " +
                    "INNER JOIN loan l2 ON latestEntriesPerBook.l_id = l2.loan_id " +
                    "INNER JOIN loan_state ls ON l2.ls_id = ls.ls_id " +
                    "WHERE ls.state = 'BORROWED'" +
                    ") unavailableBooks) /*#{#paging}*/",
            nativeQuery = true)
    Optional<Page<Inventory>> findBorrowedInventory(long libraryId, Pageable paging);
}
