package com.twoimpulse.challenge.person;

import com.twoimpulse.challenge.exception.EntityNotFoundException;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import com.twoimpulse.challenge.librarycard.LibraryCardService;
import com.twoimpulse.challenge.loan.Loan;
import com.twoimpulse.challenge.loan.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

    private final PersonService personService;
    private final LibraryCardService libraryCardService;

    @Autowired
    public PersonController(PersonService personService, LibraryCardService libraryCardService) {
        this.personService = personService;
        this.libraryCardService = libraryCardService;
    }

    @GetMapping("/{personId}")
    public Person getPerson(@PathVariable("personId") long personId) {

        Optional<Person> person = personService.getPerson(personId);
        if(person.isPresent()){
            return person.get();
        }

        throw new EntityNotFoundException("No user with id = " + personId);
    }

    @DeleteMapping(path = "/{personId}")
    public void deletePerson(@PathVariable("personId") long personId){
        personService.deletePerson(personId);
        log.debug("User with id {} deleted.", personId);
    }

    @PostMapping(path = "/auth/sign-in")
    public ResponseEntity<Person> signIn(@RequestBody Person person){

        Optional<Person> personFound = personService.signIn(person);
        if(personFound.isPresent()){
            Person p = personFound.get();
            log.debug("User {} signedIn.", p);
            return ResponseEntity.ok(p);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping(path = "/auth/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person){

        log.debug("User {} signUp.", person);

        Person newPerson = personService.addNewPerson(person);

        return ResponseEntity.status(201).body(newPerson);

    }

    @GetMapping("/{personId}/library-cards")
    public ResponseEntity<List<LibraryCard>> getLibraryCards(@PathVariable("personId") long personId){

        Optional<List<LibraryCard>> libraryCards = libraryCardService.getLibraryCardsByPersonId(personId);
        if(libraryCards.isPresent()){
            return ResponseEntity.ok(libraryCards.get());
        }
        log.error("No cards available for person id " + personId);
        throw new EntityNotFoundException("No library cards associated with person " + personId);
    }






}
