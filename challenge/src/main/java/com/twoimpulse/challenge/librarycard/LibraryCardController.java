package com.twoimpulse.challenge.librarycard;

import com.twoimpulse.challenge.exception.EntityNotFoundException;
import com.twoimpulse.challenge.library.Library;
import com.twoimpulse.challenge.librarycardsubscription.LibraryCardSubscriptionService;
import com.twoimpulse.challenge.person.Person;
import com.twoimpulse.challenge.person.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/library/{libraryId}/library-card")
public class LibraryCardController {

    private final PersonService personService;
    private final LibraryCardService libraryCardService;
    private final LibraryCardSubscriptionService libraryCardSubscriptionService;

    @Autowired
    public LibraryCardController(PersonService personService, LibraryCardService libraryCardService, LibraryCardSubscriptionService libraryCardSubscriptionService) {
        this.personService = personService;
        this.libraryCardService = libraryCardService;
        this.libraryCardSubscriptionService = libraryCardSubscriptionService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<LibraryCard> createLibraryCard(@PathVariable("libraryId") long libraryId, @RequestParam long personId){

        Optional<LibraryCard> libCard = libraryCardService.getLibraryCardByLibraryIdAndPersonId(libraryId, personId);
        if(libCard.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a library card");
        }

        Optional<Person> person = personService.getPerson(personId);
        if(person.isPresent()){
            LibraryCard libraryCard = libraryCardService.createLibraryCard(libraryId, person.get());
            //Free 1 month sub on card creation
            libraryCardSubscriptionService.subscribe(libraryCard.getLibraryCardId(), 1);

            return ResponseEntity.status(201).body(libraryCard);
        }

        throw new EntityNotFoundException("Person with given id, does not exist");

    }

    @GetMapping("/{libraryCardId}")
    public LibraryCard getLibraryCard(@PathVariable("libraryCardId") long libraryId){
        return libraryCardService.getLibraryCard(libraryId);
    }

    @GetMapping
    public ResponseEntity<List<LibraryCard>> getLibraryCards(@RequestParam List<Long> ids){

        List<LibraryCard> libCards = libraryCardService.getLibraryCardsById(ids);
        if(libCards != null && libCards.size() > 0){
            return ResponseEntity.ok(libCards);
        }

        throw new EntityNotFoundException("no library cards found with those ids");
    }

}
