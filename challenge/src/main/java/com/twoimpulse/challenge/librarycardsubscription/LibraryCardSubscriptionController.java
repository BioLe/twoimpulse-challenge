package com.twoimpulse.challenge.librarycardsubscription;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/library/{libraryId}/library-card/{libraryCardId}/subscription")
public class LibraryCardSubscriptionController {

    private final LibraryCardSubscriptionService libraryCardSubscriptionService;

    @Autowired
    public LibraryCardSubscriptionController(LibraryCardSubscriptionService libraryCardSubscriptionService) {
        this.libraryCardSubscriptionService = libraryCardSubscriptionService;
    }

    @PostMapping
    public ResponseEntity subscribe(@PathVariable("libraryCardId") long libraryCardId,
                                    @RequestParam(defaultValue = "1") int subscriptionTimeInMonths){

        libraryCardSubscriptionService.subscribe(libraryCardId, subscriptionTimeInMonths);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<LibraryCardSubscription>> getSubscriptionHistory(
            @PathVariable("libraryCardId") long libraryCardId){

        List<LibraryCardSubscription> subscriptionHistory = libraryCardSubscriptionService
                                                                .getSubscriptionHistory(libraryCardId);
        return ResponseEntity.ok(subscriptionHistory);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> isSubscriptionActive(
            @PathVariable("libraryCardId") long libraryCardId){

        Optional<Boolean> isActive = libraryCardSubscriptionService.isSubscriptionActive(libraryCardId);
        if(isActive.isPresent()){
            return ResponseEntity.ok(isActive.get());
        }
        return ResponseEntity.ok(false);
    }

}
