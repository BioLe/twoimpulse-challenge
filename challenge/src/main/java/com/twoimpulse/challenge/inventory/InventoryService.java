package com.twoimpulse.challenge.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Optional<Inventory> getBookInventory(long inventoryId, long libraryId) {
        return inventoryRepository.findByISBNAndLibraryId(inventoryId, libraryId);
    }

    public Optional<Page<Inventory>> getBooks(long libraryId, Pageable paging) {
        return inventoryRepository.findAllByLibraryId(libraryId, paging);
    }

    public Optional<Page<Inventory>> getAvailableBooks(long libraryId, Pageable paging){
        return inventoryRepository.findAvailableInventory(libraryId, paging);
    }

    public Optional<Page<Inventory>> getBorrowedBooks(long libraryId, Pageable paging) {
        return inventoryRepository.findBorrowedInventory(libraryId, paging);
    }
}
