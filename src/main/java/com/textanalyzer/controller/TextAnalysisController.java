package com.textanalyzer.controller;

import com.textanalyzer.dto.*;
import com.textanalyzer.service.TextAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/text")
public class TextAnalysisController {

    private final TextAnalysisService textAnalysisService;

    public TextAnalysisController(TextAnalysisService textAnalysisService) {
        this.textAnalysisService = textAnalysisService;
    }

    @PostMapping("/word-count")
    public ResponseEntity<WordCountResponse> getWordCount(@Valid @RequestBody TextRequest request) {
        return ResponseEntity.ok(textAnalysisService.getWordCount(request.getText()));
    }

    @PostMapping("/character-count")
    public ResponseEntity<CharacterCountResponse> getCharacterCount(@Valid @RequestBody TextRequest request) {
        return ResponseEntity.ok(textAnalysisService.getCharacterCount(request.getText()));
    }

    @PostMapping("/frequent-words")
    public ResponseEntity<FrequentWordsResponse> getFrequentWords(@Valid @RequestBody TextRequest request) {
        return ResponseEntity.ok(textAnalysisService.getFrequentWords(request.getText()));
    }

    @PostMapping("/special-char-count")
    public ResponseEntity<SpecialCharCountResponse> getSpecialCharCount(@Valid @RequestBody TextRequest request) {
        return ResponseEntity.ok(textAnalysisService.getSpecialCharCount(request.getText()));
    }
}
