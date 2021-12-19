package com.technicalassignment.ebi01951.service;

import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.technicalassignment.ebi01951.model.HumanGene;
import com.technicalassignment.ebi01951.model.MouseGene;
import com.technicalassignment.ebi01951.model.MouseGeneSynonym;
import com.technicalassignment.ebi01951.model.Ortholog;
import com.technicalassignment.ebi01951.repositories.MouseGeneRepository;
import com.technicalassignment.ebi01951.repositories.MouseGeneSynonymRepository;
import com.technicalassignment.ebi01951.repositories.OrthologRepository;
import com.technicalassignment.ebi01951.transport.MouseGeneResponse;
import com.technicalassignment.ebi01951.transport.MouseToHumanGeneRelationResponse;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MouseGeneServiceTest {

    private static final String HUMAN_GENE_SYMBOL_1 = "AOC2";
    private static final String HUMAN_GENE_SYMBOL_2 = "AOC3";
    private static final String MOUSE_GENE_IDENTIFIER_1 = "MGI:99604";
    private static final String MOUSE_GENE_SYMBOL_1 = "Fgf8";
    private static final long MOUSE_GENE_ID_1 = 1L;
    private static final String MOUSE_GENE_SYNONYM_1 = "Fgf-8";
    private static final String MOUSE_GENE_SYNONYM_2 = "Aigf";

    @Mock
    OrthologRepository orthologRepository;

    @Mock
    MouseGeneRepository mouseGeneRepository;

    @Mock
    MouseGeneSynonymRepository mouseGeneSynonymRepository;


    @InjectMocks
    private MouseGeneService underTest;


    @Test
    void findHumanToMouseGeneRelations_multipleRelationsFound() {
        HumanGene humanGene1 = buildHumanGene(HUMAN_GENE_SYMBOL_1);
        HumanGene humanGene2 = buildHumanGene(HUMAN_GENE_SYMBOL_2);
        MouseGene mouseGene = buildMouseGene();
        final Ortholog ortholog1 = buildOrtholog(humanGene1, mouseGene, 1L);
        final Ortholog ortholog2 = buildOrtholog(humanGene2, mouseGene, 12L);

        when(orthologRepository.findAllByMouseGeneId(1L)).thenReturn(List.of(ortholog1, ortholog2));
        when(mouseGeneRepository.findBySymbol(MOUSE_GENE_SYMBOL_1)).thenReturn(mouseGene);
        final List<MouseToHumanGeneRelationResponse> result = underTest.findHumanToMouseGeneRelations(MOUSE_GENE_SYMBOL_1, MOUSE_GENE_IDENTIFIER_1);

        assertEquals(2, result.size());
        assertThat(result)
                .extracting(MouseToHumanGeneRelationResponse::getHumanGeneSymbol,
                        MouseToHumanGeneRelationResponse::getSupportCount)
                .containsExactlyInAnyOrder(Tuple.tuple(HUMAN_GENE_SYMBOL_1, 1L),
                        Tuple.tuple(HUMAN_GENE_SYMBOL_2, 12L));
    }


    @Test
    void findHumanToMouseGeneRelations_notFound() {
        final List<MouseToHumanGeneRelationResponse> result = underTest.findHumanToMouseGeneRelations(MOUSE_GENE_SYMBOL_1, MOUSE_GENE_IDENTIFIER_1);

        assertEquals(1, result.size());
        assertEquals("Incorrect search parameters!", result.get(0).getErrorMessage());
    }


    @Test
    void findMouseGeneBySymbol() {
        MouseGene mouseGene = buildMouseGene();

        MouseGeneSynonym synonym1 = buildMouseGeneSynonym(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYNONYM_1);
        MouseGeneSynonym synonym2 = buildMouseGeneSynonym(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYNONYM_2);

        when(mouseGeneRepository.findBySymbol(MOUSE_GENE_SYMBOL_1)).thenReturn(mouseGene);
        when(mouseGeneSynonymRepository.findAllByIdentifier(MOUSE_GENE_IDENTIFIER_1)).thenReturn(List.of(synonym1, synonym2));

        final MouseGeneResponse result = underTest.findMouseGene(MOUSE_GENE_SYMBOL_1, null, null);

        assertThat(result)
                .extracting(MouseGeneResponse::getIdentifier, MouseGeneResponse::getSymbol, MouseGeneResponse::getSynonyms)
                .containsExactlyInAnyOrder(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYMBOL_1, List.of(synonym1.getSynonym(), synonym2.getSynonym()));
    }


    @Test
    void findMouseGeneBySymbol_noSynonymsFound() {
        MouseGene mouseGene = buildMouseGene();

        when(mouseGeneRepository.findBySymbol(MOUSE_GENE_SYMBOL_1)).thenReturn(mouseGene);
        when(mouseGeneSynonymRepository.findAllByIdentifier(MOUSE_GENE_IDENTIFIER_1)).thenReturn(emptyList());

        final MouseGeneResponse result = underTest.findMouseGene(MOUSE_GENE_SYMBOL_1, null, null);

        assertThat(result)
                .extracting(MouseGeneResponse::getIdentifier, MouseGeneResponse::getSymbol, MouseGeneResponse::getSynonyms)
                .containsExactlyInAnyOrder(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYMBOL_1, emptyList());
    }


    @Test
    void findMouseGeneBySymbol_emptyResult() {
        final MouseGeneResponse result = underTest.findMouseGene("XXXX", null, null);
        assertEquals("No results were found for given symbol, identifier, or synonym!", result.getErrorMessage());
    }


    @Test
    void findMouseGeneByIdentifier() {
        MouseGene mouseGene = buildMouseGene();

        MouseGeneSynonym synonym1 = buildMouseGeneSynonym(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYNONYM_1);
        MouseGeneSynonym synonym2 = buildMouseGeneSynonym(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYNONYM_2);

        when(mouseGeneRepository.findByIdentifier(MOUSE_GENE_IDENTIFIER_1)).thenReturn(mouseGene);
        when(mouseGeneSynonymRepository.findAllByIdentifier(MOUSE_GENE_IDENTIFIER_1)).thenReturn(List.of(synonym1, synonym2));

        final MouseGeneResponse result = underTest.findMouseGene(null, MOUSE_GENE_IDENTIFIER_1, null);

        assertThat(result)
                .extracting(MouseGeneResponse::getIdentifier, MouseGeneResponse::getSymbol, MouseGeneResponse::getSynonyms)
                .containsExactlyInAnyOrder(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYMBOL_1, List.of(synonym1.getSynonym(), synonym2.getSynonym()));
    }


    @Test
    void findMouseGeneBySynonym() {
        MouseGene mouseGene = buildMouseGene();

        MouseGeneSynonym synonym1 = buildMouseGeneSynonym(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYNONYM_1);
        MouseGeneSynonym synonym2 = buildMouseGeneSynonym(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYNONYM_2);

        when(mouseGeneSynonymRepository.findAllBySynonym(MOUSE_GENE_SYNONYM_1)).thenReturn(List.of(synonym1));
        when(mouseGeneRepository.findByIdentifier(MOUSE_GENE_IDENTIFIER_1)).thenReturn(mouseGene);
        when(mouseGeneSynonymRepository.findAllByIdentifier(MOUSE_GENE_IDENTIFIER_1)).thenReturn(List.of(synonym1, synonym2));

        final MouseGeneResponse result = underTest.findMouseGene(null, null, MOUSE_GENE_SYNONYM_1);

        assertThat(result)
                .extracting(MouseGeneResponse::getIdentifier, MouseGeneResponse::getSymbol, MouseGeneResponse::getSynonyms)
                .containsExactlyInAnyOrder(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYMBOL_1, List.of(synonym1.getSynonym(), synonym2.getSynonym()));
    }


    @Test
    void findMouseGeneBySynonym_mouseGeneByIdentifierNotFound() {
        MouseGeneSynonym synonym1 = buildMouseGeneSynonym(MOUSE_GENE_IDENTIFIER_1, MOUSE_GENE_SYNONYM_1);

        when(mouseGeneSynonymRepository.findAllBySynonym(MOUSE_GENE_SYNONYM_1)).thenReturn(List.of(synonym1));

        final MouseGeneResponse result = underTest.findMouseGene(null, null, MOUSE_GENE_SYNONYM_1);

        assertEquals("No results were found for given symbol, identifier, or synonym!", result.getErrorMessage());
    }


    @Test
    void findMouseGeneBySynonym_noMouseGeneSynonymFound() {
        when(mouseGeneSynonymRepository.findAllBySynonym(MOUSE_GENE_SYNONYM_1)).thenReturn(emptyList());

        final MouseGeneResponse result = underTest.findMouseGene(null, null, MOUSE_GENE_SYNONYM_1);

        assertEquals("No results were found for given symbol, identifier, or synonym!", result.getErrorMessage());
    }


    // ----------- Helper methods ------------


    private MouseGeneSynonym buildMouseGeneSynonym(final String identifier, final String synonym) {
        MouseGeneSynonym mouseGeneSynonym = new MouseGeneSynonym();
        mouseGeneSynonym.setIdentifier(identifier);
        mouseGeneSynonym.setSynonym(synonym);
        return mouseGeneSynonym;
    }


    private HumanGene buildHumanGene(final String humanGeneSymbol1) {
        HumanGene humanGene1 = new HumanGene();
        humanGene1.setSymbol(humanGeneSymbol1);
        return humanGene1;
    }


    private MouseGene buildMouseGene() {
        MouseGene mouseGene = new MouseGene();
        mouseGene.setId(1L);
        mouseGene.setIdentifier(MOUSE_GENE_IDENTIFIER_1);
        mouseGene.setSymbol(MOUSE_GENE_SYMBOL_1);
        return mouseGene;
    }


    private Ortholog buildOrtholog(final HumanGene humanGene, final MouseGene mouseGene, final Long supportCount) {
        final Ortholog ortholog = new Ortholog();
        ortholog.setHumanGene(humanGene);
        ortholog.setMouseGene(mouseGene);
        ortholog.setSupportCount(supportCount);
        return ortholog;
    }
}