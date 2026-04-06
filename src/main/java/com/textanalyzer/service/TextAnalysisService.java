package com.textanalyzer.service;

import com.textanalyzer.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TextAnalysisService {

    public WordCountResponse getWordCount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new WordCountResponse(0);
        }
        String[] words = text.trim().split("\\s+");
        return new WordCountResponse(words.length);
    }

    public CharacterCountResponse getCharacterCount(String text) {
        if (text == null) {
            return new CharacterCountResponse(0, 0);
        }
        int total = text.length();
        int withoutSpaces = text.replaceAll("\\s", "").length();
        return new CharacterCountResponse(total, withoutSpaces);
    }

    public FrequentWordsResponse getFrequentWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new FrequentWordsResponse(Collections.emptyMap(), 0);
        }

        String[] words = text.toLowerCase().trim().split("\\s+");
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            // Strip punctuation from word boundaries for cleaner counting
            String cleaned = word.replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
            if (!cleaned.isEmpty()) {
                frequencyMap.merge(cleaned, 1, Integer::sum);
            }
        }

        // Sort by frequency descending, take top 10
        Map<String, Integer> topWords = frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return new FrequentWordsResponse(topWords, frequencyMap.size());
    }

    public SpecialCharCountResponse getSpecialCharCount(String text) {
        if (text == null) {
            return new SpecialCharCountResponse(0, Collections.emptyMap());
        }

        Map<Character, Integer> breakdown = new LinkedHashMap<>();
        int total = 0;

        for (char c : text.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                breakdown.merge(c, 1, Integer::sum);
                total++;
            }
        }

        return new SpecialCharCountResponse(total, breakdown);
    }
}
