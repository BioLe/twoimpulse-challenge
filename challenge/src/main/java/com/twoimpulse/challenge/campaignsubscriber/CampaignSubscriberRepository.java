package com.twoimpulse.challenge.campaignsubscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignSubscriberRepository extends JpaRepository<CampaignSubscriber, CampaignSubscriberCompositeKey> {

    @Query("SELECT cs FROM campaign_subscriber cs WHERE cs.libraryCard.library.L_ID = ?1 AND cs.campaign.campaignId = ?2")
    Optional<List<CampaignSubscriber>> findAllByLibraryIdAndCampaignId(long libraryId, long campaignId);

    @Query("SELECT c FROM campaign_subscriber c WHERE c.campaign.campaignId = ?1 AND c.libraryCard.libraryCardId = ?2")
    Optional<CampaignSubscriber> findByCampaignIdAndAndLibraryCardId(long campaignId, long libraryCardId);

    @Query("SELECT c FROM campaign_subscriber c " +
            "WHERE c.campaign.library.L_ID = ?1 AND c.campaign.campaignId = ?2 AND c.campaignSubscriberId =?3")
    CampaignSubscriber findByLibraryIdAndCampaignIdAndSubscriptionId(long libraryId, long campaignId, long subscriptionId);

}
