package com.technicalassignment.ebi01951.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.technicalassignment.ebi01951.model.MouseGene;
import com.technicalassignment.ebi01951.model.MouseGeneSynonym;
import com.technicalassignment.ebi01951.model.Ortholog;
import com.technicalassignment.ebi01951.repositories.MouseGeneRepository;
import com.technicalassignment.ebi01951.repositories.MouseGeneSynonymRepository;
import com.technicalassignment.ebi01951.repositories.OrthologRepository;
import com.technicalassignment.ebi01951.transport.MouseGeneResponse;
import com.technicalassignment.ebi01951.transport.MouseToHumanGeneRelationResponse;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MouseGeneService {

    private final Logger logger = getLogger(MouseGeneService.class);

    @Autowired
    MouseGeneRepository mouseGeneRepository;

    @Autowired
    MouseGeneSynonymRepository mouseGeneSynonymRepository;

    @Autowired
    OrthologRepository orthologRepository;


    public MouseGeneResponse findMouseGene(final String symbol, final String identifier, final String synonym) {
        Optional<MouseGene> mouseGene = findMouseGeneByFilterParameters(symbol, identifier, synonym);

        if (mouseGene.isEmpty()) {
            logger.info("No results were found for symbol : {} or identifier {}!", symbol, identifier);
            return handleMouseGeneError();

        } else {
            return buildMouseGeneResponseTO(mouseGene.get());
        }
    }


    public List<MouseToHumanGeneRelationResponse> findHumanToMouseGeneRelations(final String symbol, final String identifier) {
        Optional<MouseGene> mouseGene = findMouseGeneByFilterParameters(symbol, identifier, null);

        if (mouseGene.isEmpty()) {
            logger.info("No results were found for symbol : {} or identifier {}!", symbol, identifier);
            return handleMouseToHumanGeneRelationError();

        } else {
            final List<Ortholog> humanToMouseGeneRelations = orthologRepository.findAllByMouseGeneId(mouseGene.get().getId());
            logger.info("Found {} human to mouse gene relations", humanToMouseGeneRelations.size());

            return humanToMouseGeneRelations.stream()
                    .map(this::buildMouseToHumanGeneRelationResponse)
                    .collect(toList());
        }
    }


    private Optional<MouseGene> findMouseGeneBySynonym(final String synonym) {
        final Optional<MouseGeneSynonym> firstMouseGeneSynonymRelation = findFirstMouseGeneSynonymRelation(synonym);
        return firstMouseGeneSynonymRelation
                .map(mouseGeneSynonym -> mouseGeneRepository.findByIdentifier(mouseGeneSynonym.getIdentifier()));
    }


    private Optional<MouseGene> findMouseGeneByFilterParameters(final String symbol, final String identifier, final String synonym) {
        Optional<MouseGene> mouseGene = empty();

        if (symbol != null) {
            mouseGene = Optional.ofNullable(mouseGeneRepository.findBySymbol(symbol));
        } 
        
        if (mouseGene.isEmpty() && identifier != null) {
            mouseGene = Optional.ofNullable(mouseGeneRepository.findByIdentifier(identifier));
        }
        
        if (mouseGene.isEmpty() && synonym != null) {
            mouseGene = findMouseGeneBySynonym(synonym);
        }

        return mouseGene;
    }


    private MouseToHumanGeneRelationResponse buildMouseToHumanGeneRelationResponse(final Ortholog it) {
        MouseToHumanGeneRelationResponse relation = new MouseToHumanGeneRelationResponse();
        relation.setSupportCount(it.getSupportCount());
        relation.setHumanGeneSymbol(it.getHumanGene().getSymbol());
        return relation;
    }


    private Optional<MouseGeneSynonym> findFirstMouseGeneSynonymRelation(final String synonym) {
        List<MouseGeneSynonym> foundMouseGeneSynonym = mouseGeneSynonymRepository.findAllBySynonym(synonym);
        return foundMouseGeneSynonym.stream().findFirst();
    }


    private MouseGeneResponse buildMouseGeneResponseTO(final MouseGene foundMouseGene) {
        MouseGeneResponse mouseGeneResponseTO = new MouseGeneResponse();
        if (foundMouseGene != null) {
            mouseGeneResponseTO.setId(foundMouseGene.getId());
            mouseGeneResponseTO.setIdentifier(foundMouseGene.getIdentifier());
            mouseGeneResponseTO.setSynonyms(getSynonyms(foundMouseGene.getIdentifier()));
            mouseGeneResponseTO.setSymbol(foundMouseGene.getSymbol());
        }
        return mouseGeneResponseTO;
    }


    private List<String> getSynonyms(final String mouseGeneIdentifier) {
        final List<MouseGeneSynonym> allByIdentifier = mouseGeneSynonymRepository.findAllByIdentifier(mouseGeneIdentifier);
        final List<String> synonyms = allByIdentifier.stream().map(MouseGeneSynonym::getSynonym).collect(toList());
        logger.info("Found {} synonyms", synonyms.size());
        return synonyms;
    }


    private List<MouseToHumanGeneRelationResponse> handleMouseToHumanGeneRelationError() {
        MouseToHumanGeneRelationResponse relation = new MouseToHumanGeneRelationResponse();
        relation.setErrorMessage("Incorrect search parameters!");
        return singletonList(relation);
    }


    private MouseGeneResponse handleMouseGeneError() {
        MouseGeneResponse mouseGeneResponse = new MouseGeneResponse();
        mouseGeneResponse.setErrorMessage("No results were found for given symbol, identifier, or synonym!");
        return mouseGeneResponse;
    }
}
