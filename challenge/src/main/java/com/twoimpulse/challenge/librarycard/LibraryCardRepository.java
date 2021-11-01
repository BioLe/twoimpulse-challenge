package com.twoimpulse.challenge.librarycard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {

    @Query("select l from library_card l where l.person.personId = ?1")
    Optional<List<LibraryCard>> findAllByPerson_PersonId(long personId);

    @Query("SELECT lc FROM library_card lc WHERE lc.library.L_ID = ?1 AND lc.person.personId = ?2")
    Optional<LibraryCard> findByLibraryIdAndPersonId(long libraryId, long personId);
}
