package com.twoimpulse.challenge.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Optional<Library> getLibrary(long libraryId) {
        return libraryRepository.findById(libraryId);
    }

    public Page<Library> getAll(Pageable paging) { return libraryRepository.findAll(paging);
    }
}
