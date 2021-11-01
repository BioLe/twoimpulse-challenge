package com.twoimpulse.challenge.inventory;

import com.twoimpulse.challenge.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/library/{libraryId}/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{inventoryId}")
    public Inventory getBookFromInventory(@PathVariable("inventoryId") long inventoryId, @PathVariable("libraryId") long libraryId) {

        Optional<Inventory> bookInventory = inventoryService.getBookInventory(inventoryId, libraryId);
        if(bookInventory.isPresent()){
            return bookInventory.get();
        }

        throw new EntityNotFoundException("No book with invId: " + inventoryId);
    }

    @GetMapping
    public ResponseEntity<Page<Inventory>> getBooks(@PathVariable("libraryId") long libraryId,
                                                    @RequestParam(name = "status", defaultValue = "all") String status,
                                                    @RequestParam(defaultValue = "0") Integer pageNo,
                                                    @RequestParam(defaultValue = "5") Integer pageSize){

        Pageable paging = PageRequest.of(pageNo, pageSize);

        // Status: All, Available, Borrowed
        switch (status){
            case "all":
                Optional<Page<Inventory>> booksInInventory = inventoryService.getBooks(libraryId, paging);
                if(booksInInventory.isPresent()){
                    return ResponseEntity.ok(booksInInventory.get());
                }
                break;

            case "available":
                Optional<Page<Inventory>> availableBooks = inventoryService.getAvailableBooks(libraryId, paging);
                if(availableBooks.isPresent()){
                    return ResponseEntity.ok(availableBooks.get());
                }
                break;

            case "borrowed":
                Optional<Page<Inventory>> borrowedBooks = inventoryService.getBorrowedBooks(libraryId, paging);
                if(borrowedBooks.isPresent()){
                    return ResponseEntity.ok(borrowedBooks.get());
                }
                break;

            default:
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid status");
        }




        return ResponseEntity.notFound().build();
    }
}