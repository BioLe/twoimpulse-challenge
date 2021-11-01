package com.twoimpulse.challenge.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {


    @Query("select c from campaign c where c.library.L_ID = ?1")
    Optional<List<Campaign>> findAllByLibraryId(long libraryId);

    @Query("select c from campaign c where c.library.L_ID = ?1 and c.campaignId = ?2")
    Optional<Campaign> findByIdAndLibraryId(long libraryId, long campaignId);
}
