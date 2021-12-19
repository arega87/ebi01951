package com.technicalassignment.ebi01951.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.technicalassignment.ebi01951.model.Ortholog;

@Repository
public interface OrthologRepository extends JpaRepository<Ortholog, Long> {
    List<Ortholog> findAllByMouseGeneId(Long mouseGeneId);
}
