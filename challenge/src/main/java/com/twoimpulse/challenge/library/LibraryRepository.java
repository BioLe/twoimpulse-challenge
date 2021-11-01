package com.twoimpulse.challenge.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

}
