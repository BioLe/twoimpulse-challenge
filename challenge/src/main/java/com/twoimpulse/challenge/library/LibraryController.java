package com.twoimpulse.challenge.library;

import com.twoimpulse.challenge.exception.EntityNotFoundException;
import com.twoimpulse.challenge.person.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/{id}")
    public Library getLibrary(@PathVariable("id") long libraryId) {

        Optional<Library> lib = libraryService.getLibrary(libraryId);
        if(lib.isPresent()){
            return lib.get();
        }

        throw new EntityNotFoundException("No library with id = " + libraryId);
    }

    @GetMapping
    public ResponseEntity<Page<Library>> getAllLibraries(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "5") Integer pageSize){

        Pageable paging = PageRequest.of(pageNo, pageSize);

        return ResponseEntity.ok(libraryService.getAll(paging));
    }
}
