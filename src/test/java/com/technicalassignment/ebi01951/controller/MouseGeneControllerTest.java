package com.technicalassignment.ebi01951.controller;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import com.technicalassignment.ebi01951.service.MouseGeneService;
import com.technicalassignment.ebi01951.transport.MouseGeneResponse;
import com.technicalassignment.ebi01951.transport.MouseToHumanGeneRelationResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MouseGeneController.class)
class MouseGeneControllerTest {


    private static final String HUMAN_GENE_SYMBOL_1 = "AOC2";
    private static final String HUMAN_GENE_SYMBOL_2 = "AOC3";
    private static final String MOUSE_GENE_IDENTIFIER_1 = "MGI:99604";
    private static final String MOUSE_GENE_SYMBOL_1 = "Fgf8";
    private static final long MOUSE_GENE_ID_1 = 1L;
    private static final String MOUSE_GENE_SYNONYM_1 = "Fgf-8";
    private static final String MOUSE_GENE_SYNONYM_2 = "Aigf";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MouseGeneService mouseGeneService;


    @Test
    void searchMouseGene() throws Exception {
        final String expectedResult = "{\"symbol\":\"Fgf8\",\"identifier\":\"MGI:99604\",\"synonyms\":[\"Fgf-8\",\"Aigf\"]}";

        MouseGeneResponse mouseGeneResponse = new MouseGeneResponse();
        mouseGeneResponse.setIdentifier(MOUSE_GENE_IDENTIFIER_1);
        mouseGeneResponse.setSymbol(MOUSE_GENE_SYMBOL_1);
        mouseGeneResponse.setSynonyms(List.of(MOUSE_GENE_SYNONYM_1, MOUSE_GENE_SYNONYM_2));

        when(mouseGeneService.findMouseGene(MOUSE_GENE_SYMBOL_1, null, null)).thenReturn(mouseGeneResponse);

        final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/search/mousegene?symbol=Fgf8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertEquals(expectedResult, mvcResult.getResponse().getContentAsString());
    }


    @Test
    void searchMouseGene_noResultsFound() throws Exception {
        final MouseGeneResponse expected = new MouseGeneResponse();
        expected.setErrorMessage("this is an error message");

        when(mouseGeneService.findMouseGene("test", null, null)).thenReturn(expected);

        final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/search/mousegene?symbol=test"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertEquals("{\"errorMessage\":\"this is an error message\"}", mvcResult.getResponse().getContentAsString());
    }


    @Test
    void searchMouseGene_noSearchParameterInserted() throws Exception {
        this.mockMvc.perform(get("/api/v1/search/mousegene"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }


    @Test
    void searchHumanGeneRelation_noResultsFound() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/mousetohumangenerelation?symbol=Aoc2"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertEquals("[]", mvcResult.getResponse().getContentAsString());
    }


    @Test
    void searchHumanGeneRelation() throws Exception {
        final String expectedResult = "[{\"supportCount\":1,\"humanGeneSymbol\":\"AOC2\"}," +
                "{\"supportCount\":11,\"humanGeneSymbol\":\"AOC3\"}]";

        MouseToHumanGeneRelationResponse mouseToHumanGeneRelation1 = new MouseToHumanGeneRelationResponse();
        mouseToHumanGeneRelation1.setHumanGeneSymbol(HUMAN_GENE_SYMBOL_1);
        mouseToHumanGeneRelation1.setSupportCount(1L);
        MouseToHumanGeneRelationResponse mouseToHumanGeneRelation2 = new MouseToHumanGeneRelationResponse();
        mouseToHumanGeneRelation2.setHumanGeneSymbol(HUMAN_GENE_SYMBOL_2);
        mouseToHumanGeneRelation2.setSupportCount(11L);

        when(mouseGeneService.findHumanToMouseGeneRelations("Aoc2", null))
                .thenReturn(List.of(mouseToHumanGeneRelation1, mouseToHumanGeneRelation2));

        final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/mousetohumangenerelation?symbol=Aoc2"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertEquals(expectedResult, mvcResult.getResponse().getContentAsString());
    }
}