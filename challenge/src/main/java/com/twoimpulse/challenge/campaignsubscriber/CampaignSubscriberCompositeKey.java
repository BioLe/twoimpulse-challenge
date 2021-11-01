package com.twoimpulse.challenge.campaignsubscriber;

import com.twoimpulse.challenge.campaign.Campaign;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class CampaignSubscriberCompositeKey implements Serializable {
    //private Campaign campaign;
    //private LibraryCard libraryCard;
    private long campaignId;
    private long libraryCardId;
}
