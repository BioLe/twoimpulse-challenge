package com.twoimpulse.challenge.campaignsubscriber;

import com.twoimpulse.challenge.campaign.Campaign;
import com.twoimpulse.challenge.campaign.CampaignService;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignSubscriberService {

    private final CampaignSubscriberRepository campaignSubscriberRepository;
    private final CampaignService campaignService;

    public CampaignSubscriberService(CampaignSubscriberRepository campaignSubscriberRepository, CampaignService campaignService) {
        this.campaignSubscriberRepository = campaignSubscriberRepository;
        this.campaignService = campaignService;
    }

    public Optional<List<CampaignSubscriber>> getAllSubscribers(long libraryId, long campaignId) {
        return campaignSubscriberRepository.findAllByLibraryIdAndCampaignId(libraryId, campaignId);
    }

    public Optional<CampaignSubscriber> getSubscriberByCampaignIdAndLibraryCardId(long campaignId, long libraryCardId){
        return campaignSubscriberRepository.findByCampaignIdAndAndLibraryCardId(campaignId, libraryCardId);
    }

    public void subscribe(long libraryId, long campaignId, LibraryCard libraryCard) {

        Optional<CampaignSubscriber> cSub =
                getSubscriberByCampaignIdAndLibraryCardId(campaignId, libraryCard.getLibraryCardId());

        if(cSub.isEmpty()){
            CampaignSubscriber subscription = new CampaignSubscriber();
            subscription.setLibraryCard(libraryCard);

            Optional<Campaign> campaign = campaignService.getCampaign(libraryId, campaignId);
            if(campaign.isPresent()){
                subscription.setCampaign(campaign.get());
                campaignSubscriberRepository.save(subscription);
            }
        }
    }

    public CampaignSubscriber getSubscription(long libraryId, long campaignId, long subscriptionId) {
        return campaignSubscriberRepository
                .findByLibraryIdAndCampaignIdAndSubscriptionId(libraryId, campaignId, subscriptionId);
    }
}
