package com.technicalassignment.ebi01951.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.technicalassignment.ebi01951.model.MouseGene;

@Repository
public interface MouseGeneRepository extends JpaRepository<MouseGene, Long> {

    MouseGene findBySymbol(String symbol);

    MouseGene findByIdentifier(String identifier);
}
