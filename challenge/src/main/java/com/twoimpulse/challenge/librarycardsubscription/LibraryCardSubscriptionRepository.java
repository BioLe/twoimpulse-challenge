package com.twoimpulse.challenge.librarycardsubscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryCardSubscriptionRepository extends JpaRepository<LibraryCardSubscription, Long> {


    @Query("SELECT lcs FROM library_card_subscription lcs WHERE lcs.libraryCard.libraryCardId = ?1")
    List<LibraryCardSubscription> findAllByLibraryCardId(long libraryCardId);

    @Query("select l from library_card_subscription l where l.libraryCard.libraryCardId = ?1 order by l.startDate DESC")
    Optional<List<LibraryCardSubscription>> findByLibraryCardIdOrderByStartDateDesc(long libraryCardId);

    @Query(value = "SELECT max(end_date) > NOW() FROM library_card_subscription WHERE lc_id = ?1", nativeQuery = true)
    Optional<Boolean> isSubscriptionActive(long libraryCardId);

    // subscription_date lc_id
}
