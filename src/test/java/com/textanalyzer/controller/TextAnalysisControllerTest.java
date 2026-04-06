package com.textanalyzer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textanalyzer.dto.*;
import com.textanalyzer.service.TextAnalysisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TextAnalysisController.class)
class TextAnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TextAnalysisService textAnalysisService;

    @Autowired
    private ObjectMapper objectMapper;

    // ==================== Word Count Endpoint Tests ====================

    @Test
    @DisplayName("POST /api/text/word-count should return word count")
    void wordCountShouldReturnCount() throws Exception {
        when(textAnalysisService.getWordCount(anyString()))
                .thenReturn(new WordCountResponse(5));

        TextRequest request = new TextRequest("Hello world from Spring Boot");

        mockMvc.perform(post("/api/text/word-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordCount").value(5));
    }

    @Test
    @DisplayName("POST /api/text/word-count should return 400 for blank text")
    void wordCountShouldReturn400ForBlankText() throws Exception {
        TextRequest request = new TextRequest("");

        mockMvc.perform(post("/api/text/word-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/text/word-count should return 400 for missing body")
    void wordCountShouldReturn400ForMissingBody() throws Exception {
        mockMvc.perform(post("/api/text/word-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // ==================== Character Count Endpoint Tests ====================

    @Test
    @DisplayName("POST /api/text/character-count should return character counts")
    void characterCountShouldReturnCounts() throws Exception {
        when(textAnalysisService.getCharacterCount(anyString()))
                .thenReturn(new CharacterCountResponse(11, 10));

        TextRequest request = new TextRequest("Hello World");

        mockMvc.perform(post("/api/text/character-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCharacters").value(11))
                .andExpect(jsonPath("$.charactersWithoutSpaces").value(10));
    }

    @Test
    @DisplayName("POST /api/text/character-count should return 400 for blank text")
    void characterCountShouldReturn400ForBlankText() throws Exception {
        TextRequest request = new TextRequest("  ");

        mockMvc.perform(post("/api/text/character-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ==================== Frequent Words Endpoint Tests ====================

    @Test
    @DisplayName("POST /api/text/frequent-words should return frequent words")
    void frequentWordsShouldReturnWords() throws Exception {
        Map<String, Integer> words = new LinkedHashMap<>();
        words.put("the", 3);
        words.put("cat", 2);

        when(textAnalysisService.getFrequentWords(anyString()))
                .thenReturn(new FrequentWordsResponse(words, 5));

        TextRequest request = new TextRequest("the cat sat on the mat the cat");

        mockMvc.perform(post("/api/text/frequent-words")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.frequentWords.the").value(3))
                .andExpect(jsonPath("$.frequentWords.cat").value(2))
                .andExpect(jsonPath("$.totalUniqueWords").value(5));
    }

    @Test
    @DisplayName("POST /api/text/frequent-words should return 400 for blank text")
    void frequentWordsShouldReturn400ForBlankText() throws Exception {
        TextRequest request = new TextRequest("");

        mockMvc.perform(post("/api/text/frequent-words")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ==================== Special Char Count Endpoint Tests ====================

    @Test
    @DisplayName("POST /api/text/special-char-count should return special char count")
    void specialCharCountShouldReturnCount() throws Exception {
        Map<Character, Integer> breakdown = new LinkedHashMap<>();
        breakdown.put('!', 2);
        breakdown.put('@', 1);

        when(textAnalysisService.getSpecialCharCount(anyString()))
                .thenReturn(new SpecialCharCountResponse(3, breakdown));

        TextRequest request = new TextRequest("Hi!! @user");

        mockMvc.perform(post("/api/text/special-char-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSpecialCharacters").value(3))
                .andExpect(jsonPath("$.specialCharBreakdown.!").value(2))
                .andExpect(jsonPath("$.specialCharBreakdown.@").value(1));
    }

    @Test
    @DisplayName("POST /api/text/special-char-count should return 400 for null text")
    void specialCharCountShouldReturn400ForNullText() throws Exception {
        mockMvc.perform(post("/api/text/special-char-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": null}"))
                .andExpect(status().isBadRequest());
    }

    // ==================== General Endpoint Tests ====================

    @Test
    @DisplayName("Should return 415 for non-JSON content type")
    void shouldReturn415ForNonJsonContentType() throws Exception {
        mockMvc.perform(post("/api/text/word-count")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Hello World"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
