package com.twoimpulse.challenge.librarycardsubscription;

import com.twoimpulse.challenge.librarycard.LibraryCard;
import com.twoimpulse.challenge.librarycard.LibraryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryCardSubscriptionService {

    private final LibraryCardSubscriptionRepository libraryCardSubscriptionRepository;
    private final LibraryCardRepository libraryCardRepository;

    @Autowired
    public LibraryCardSubscriptionService(LibraryCardSubscriptionRepository libraryCardSubscriptionRepository, LibraryCardRepository libraryCardRepository) {
        this.libraryCardSubscriptionRepository = libraryCardSubscriptionRepository;
        this.libraryCardRepository = libraryCardRepository;
    }

    public void subscribe(long libraryCardId, int subscriptionDurationInMonths) {

        LibraryCardSubscription newSub = new LibraryCardSubscription();

        // 1. Is there a subscription?
        Optional<List<LibraryCardSubscription>> libraryCardSubscription =
                libraryCardSubscriptionRepository.findByLibraryCardIdOrderByStartDateDesc(libraryCardId);

        if(libraryCardSubscription.isPresent()){
            List<LibraryCardSubscription> libSubs = libraryCardSubscription.get();
            // Yes - Add subscriptionDurationInMonths to previous SubscriptionDate
            if(!libSubs.isEmpty()){
                LibraryCardSubscription libSub = libSubs.get(0);
                newSub.setLibraryCard( libSub.getLibraryCard() );
                newSub.setStartDate( libSub.getStartDate().plusMonths(subscriptionDurationInMonths) );
                newSub.setEndDate( newSub.getStartDate().plusMonths(subscriptionDurationInMonths) );
            }
            else { // No - Create one
                LibraryCard libCard = libraryCardRepository.getById(libraryCardId);
                newSub.setLibraryCard(libCard);
                newSub.setStartDate(LocalDateTime.now());
                newSub.setEndDate(LocalDateTime.now().plusMonths(subscriptionDurationInMonths));
            }
            libraryCardSubscriptionRepository.save(newSub);
        }

    }

    public List<LibraryCardSubscription> getSubscriptionHistory(long subscriptionId) {
        return libraryCardSubscriptionRepository.findAllByLibraryCardId(subscriptionId);
    }

    public Optional<Boolean> isSubscriptionActive(long libraryCardId) {
        return libraryCardSubscriptionRepository.isSubscriptionActive(libraryCardId);
    }
}
