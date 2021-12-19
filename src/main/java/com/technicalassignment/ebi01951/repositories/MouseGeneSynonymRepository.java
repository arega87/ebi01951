package com.technicalassignment.ebi01951.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.technicalassignment.ebi01951.model.MouseGeneSynonym;

@Repository
public interface MouseGeneSynonymRepository extends JpaRepository<MouseGeneSynonym, Long> {
    List<MouseGeneSynonym> findAllByIdentifier(String symbol);

    List<MouseGeneSynonym> findAllBySynonym(String synonym);
}
