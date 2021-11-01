package com.twoimpulse.challenge.campaignsubscriber;

import com.twoimpulse.challenge.exception.EntityNotFoundException;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import com.twoimpulse.challenge.librarycard.LibraryCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/library/{libraryId}/campaign/{campaignId}/subscription")
public class CampaignSubscriberController {

    private final CampaignSubscriberService campaignSubscriberService;
    private final LibraryCardService libraryCardService;

    public CampaignSubscriberController(CampaignSubscriberService campaignSubscriberService, LibraryCardService libraryCardService) {
        this.campaignSubscriberService = campaignSubscriberService;
        this.libraryCardService = libraryCardService;
    }

    @GetMapping
    public ResponseEntity<List<CampaignSubscriber>> getCampaignSubscribers(@PathVariable long libraryId,
                                                                           @PathVariable long campaignId){

        Optional<List<CampaignSubscriber>> subscribers = campaignSubscriberService
                                                            .getAllSubscribers(libraryId, campaignId);

        if(subscribers.isPresent()){
            return ResponseEntity.ok(subscribers.get());
        }

        throw new EntityNotFoundException(
                String.format("No subscribers found for campaign %l from library %l", libraryId, campaignId));
    }

    @GetMapping("/{subscriptionId}")
    public CampaignSubscriber getSubscriber(@PathVariable long libraryId,
                                            @PathVariable long campaignId,
                                            @PathVariable long subscriptionId){

        return campaignSubscriberService.getSubscription(libraryId, campaignId, subscriptionId);

    }


    @Transactional
    @PostMapping("/{libraryCardId}")
    public ResponseEntity subscribeToCampaign(@PathVariable long libraryId,
                                              @PathVariable long campaignId,
                                              @PathVariable long libraryCardId){

        Optional<CampaignSubscriber> cSub = campaignSubscriberService.
                getSubscriberByCampaignIdAndLibraryCardId(campaignId, libraryCardId);

        if(cSub.isPresent())
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Subscription already exists for library card id " + libraryCardId);

        LibraryCard libraryCard = libraryCardService.getLibraryCard(libraryCardId);
        if(libraryCard == null)
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Library card does not belong do this library.");

        if(libraryCard.getLibrary().getL_ID() == libraryId){
            campaignSubscriberService.subscribe(libraryId, campaignId, libraryCard);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    }
}
