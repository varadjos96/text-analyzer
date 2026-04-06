package com.textanalyzer.dto;

public class WordCountResponse {

    private int wordCount;

    public WordCountResponse() {}

    public WordCountResponse(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
}
