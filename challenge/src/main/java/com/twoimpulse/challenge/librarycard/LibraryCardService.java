package com.twoimpulse.challenge.librarycard;

import com.twoimpulse.challenge.library.Library;
import com.twoimpulse.challenge.library.LibraryRepository;
import com.twoimpulse.challenge.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryCardService {

    private LibraryCardRepository libraryCardRepository;
    private LibraryRepository libraryRepository;

    @Autowired
    public LibraryCardService(LibraryCardRepository libraryCardRepository, LibraryRepository libraryRepository) {
        this.libraryCardRepository = libraryCardRepository;
        this.libraryRepository = libraryRepository;
    }

    public LibraryCard createLibraryCard(long libraryId, Person person) {
        Library library = libraryRepository.getById(libraryId);
        LibraryCard libraryCard = new LibraryCard(library, person);

        libraryCardRepository.save(libraryCard);

        return libraryCard;
    }

    public LibraryCard getLibraryCard(long id) {
        return libraryCardRepository.getById(id);
    }

    public Optional<List<LibraryCard>> getLibraryCardsByPersonId(long personId) {
        return libraryCardRepository.findAllByPerson_PersonId(personId);
    }

    public List<LibraryCard> getLibraryCardsById(List<Long> ids) {
        return libraryCardRepository.findAllById(ids);
    }

    public Optional<LibraryCard> getLibraryCardByLibraryIdAndPersonId(long libraryId, long personId) {
        return libraryCardRepository.findByLibraryIdAndPersonId(libraryId, personId);
    }
}
