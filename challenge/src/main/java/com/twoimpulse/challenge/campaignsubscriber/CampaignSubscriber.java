package com.twoimpulse.challenge.campaignsubscriber;

import com.twoimpulse.challenge.campaign.Campaign;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Table(
    name = "campaign_subscriber",
    uniqueConstraints={
            @UniqueConstraint(columnNames = {"c_id", "lc_id"})
})
@Entity(name = "campaign_subscriber")
//@IdClass(CampaignSubscriberId.class)
@Getter
@Setter

public class CampaignSubscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cs_id")
    private long campaignSubscriberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lc_id")
    private LibraryCard libraryCard;

    @Transient
    private boolean isUserNew;

    public CampaignSubscriber(){};
    public CampaignSubscriber(Campaign campaign, LibraryCard libCard){
        this.campaign = campaign;
        this.libraryCard = libCard;
//        this.campaignSubscriberId = new CampaignSubscriberId(campaign.getCampaignId(), libraryCard.getLibraryCardId());
        //this.isUserNew = getIsUserNew();
    }

    public boolean getIsUserNew(){
        // A user is considered new if he joined the library less than 1 week

        long accountAgeInWeeks = ChronoUnit.WEEKS
                                    .between(
                                        libraryCard.getCreationDate(),
                                        LocalDateTime.now());

        return (accountAgeInWeeks < 1) ? true : false;
    }

    public void setIsUserNew(boolean isUserNew){
        this.isUserNew = isUserNew;
    }

}
