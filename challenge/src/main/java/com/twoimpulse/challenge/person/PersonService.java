package com.twoimpulse.challenge.person;

import com.twoimpulse.challenge.common.Utils;
import com.twoimpulse.challenge.exception.EmailAlreadyTakenException;
import com.twoimpulse.challenge.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final Utils utils;

    @Value("${app.salt}")
    private String salt;

    @Autowired
    public PersonService(PersonRepository personRepository, Utils utils) {
        this.personRepository = personRepository;
        this.utils = utils;
    }

    public Optional<Person> getPerson(long personId){
        return personRepository.findById(personId);
    }

    public Person addNewPerson(Person person){

        Optional<Person> personOptional = personRepository.findByEmail(person.getEmail());
        if(personOptional.isPresent()){
            throw new EmailAlreadyTakenException(person.getEmail() + " is currently in use.");
        }

        if(person.getEmail() == null
                || person.getEmail().length() <= 0
                || !utils.validateEmail(person.getEmail())){
            log.error("Email is not valid");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email is not valid.");
        }

        if(person.getName() == null
                || person.getName().length() <= 0){
            log.error("Name is not valid");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Name is not valid.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(salt + person.getPassword());
        person.setPassword(hashedPassword);

        person.setRegistrationDate(LocalDateTime.now());

        personRepository.save(person);

        log.error("Person was saved");

        return person;
    }

    public void deletePerson(long personId){

        boolean exists = personRepository.existsById(personId);
        if(!exists){
            log.error("Couldn't delete person " + personId);
            throw new EntityNotFoundException("Person with id " + personId + " does not exist.");
        }
        personRepository.deleteById(personId);
    }

    public Optional<Person> signIn(Person person){
        Optional<Person> personOptional = personRepository.findByEmail(person.getEmail());
        if(personOptional.isPresent()){
            Person personFound = personOptional.get();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(!passwordEncoder.matches(salt + person.getPassword(), personFound.getPassword())){
                log.error("signIn Password didn't match");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong password");
            }
        }
        else {
            log.error("signIn email is not assigned to anyone");
            throw new EntityNotFoundException("Email " + person.getEmail() + " is not registered.");
        }
        return personOptional;
    }


}
