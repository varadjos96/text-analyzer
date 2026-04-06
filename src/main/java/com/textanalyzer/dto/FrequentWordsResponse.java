package com.textanalyzer.dto;

import java.util.Map;

public class FrequentWordsResponse {

    private Map<String, Integer> frequentWords;
    private int totalUniqueWords;

    public FrequentWordsResponse() {}

    public FrequentWordsResponse(Map<String, Integer> frequentWords, int totalUniqueWords) {
        this.frequentWords = frequentWords;
        this.totalUniqueWords = totalUniqueWords;
    }

    public Map<String, Integer> getFrequentWords() {
        return frequentWords;
    }

    public void setFrequentWords(Map<String, Integer> frequentWords) {
        this.frequentWords = frequentWords;
    }

    public int getTotalUniqueWords() {
        return totalUniqueWords;
    }

    public void setTotalUniqueWords(int totalUniqueWords) {
        this.totalUniqueWords = totalUniqueWords;
    }
}
