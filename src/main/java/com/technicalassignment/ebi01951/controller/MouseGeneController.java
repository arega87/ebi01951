package com.technicalassignment.ebi01951.controller;

import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.technicalassignment.ebi01951.service.MouseGeneService;
import com.technicalassignment.ebi01951.transport.MouseGeneResponse;
import com.technicalassignment.ebi01951.transport.MouseToHumanGeneRelationResponse;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
public class MouseGeneController {

    private final Logger logger = getLogger(MouseGeneController.class);

    @Autowired
    private MouseGeneService mouseGeneService;


    @GetMapping("/search/mousegene")
    public ResponseEntity<MouseGeneResponse> searchMouseGene(
            @RequestParam(value = "symbol", required = false) String symbol,
            @RequestParam(value = "identifier", required = false) String identifier,
            @RequestParam(value = "synonym", required = false) String synonym
    ) {
        if (isValid(symbol) || isValid(identifier) || isValid(synonym)) {
            return ok().body(mouseGeneService.findMouseGene(symbol, identifier, synonym));
        } else {
            logger.info("Incorrect search parameters!");
            return notFound().build();
        }
    }


    @GetMapping("/mousetohumangenerelation")
    public ResponseEntity<List<MouseToHumanGeneRelationResponse>> searchHumanGeneRelation(
            @RequestParam(value = "symbol", required = false) String symbol,
            @RequestParam(value = "identifier", required = false) String identifier
    ) {
        if (isValid(symbol) || isValid(identifier)) {
            return ok().body(mouseGeneService.findHumanToMouseGeneRelations(symbol, identifier));
        } else {
            logger.info("Incorrect search parameters!");
            return notFound().build();
        }
    }


    private boolean isValid(final String parameter) {
        return parameter != null && !parameter.equals("");
    }
}
