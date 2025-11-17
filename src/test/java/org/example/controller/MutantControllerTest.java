package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    private static final String MUTANT_DNA_JSON = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";
    private static final String HUMAN_DNA_JSON = "{\"dna\":[\"ATGC\",\"TAGC\",\"CTAG\",\"GCAT\"]}";

    @Test
    void isMutant_WhenMutant_ShouldReturn200() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        when(mutantService.isMutant(dna)).thenReturn(true);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MUTANT_DNA_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void isMutant_WhenHuman_ShouldReturn403() throws Exception {
        String[] dna = {"ATGC", "TAGC", "CTAG", "GCAT"};
        when(mutantService.isMutant(dna)).thenReturn(false);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(HUMAN_DNA_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void isMutant_WhenInvalidDna_ShouldReturn400() throws Exception {
        String invalidDnaJson = "{\"dna\":[\"ATGC\",\"TAGC\",\"CTAG\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidDnaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStats_WhenDataExists_ShouldReturnStats() throws Exception {
        StatsResponse mockResponse = StatsResponse.builder()
                .countMutantDna(40L)
                .countHumanDna(100L)
                .ratio(0.4)
                .build();

        when(statsService.getStats()).thenReturn(mockResponse);

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }
}