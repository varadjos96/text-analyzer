package com.textanalyzer.dto;

import java.util.Map;

public class SpecialCharCountResponse {

    private int totalSpecialCharacters;
    private Map<Character, Integer> specialCharBreakdown;

    public SpecialCharCountResponse() {}

    public SpecialCharCountResponse(int totalSpecialCharacters, Map<Character, Integer> specialCharBreakdown) {
        this.totalSpecialCharacters = totalSpecialCharacters;
        this.specialCharBreakdown = specialCharBreakdown;
    }

    public int getTotalSpecialCharacters() {
        return totalSpecialCharacters;
    }

    public void setTotalSpecialCharacters(int totalSpecialCharacters) {
        this.totalSpecialCharacters = totalSpecialCharacters;
    }

    public Map<Character, Integer> getSpecialCharBreakdown() {
        return specialCharBreakdown;
    }

    public void setSpecialCharBreakdown(Map<Character, Integer> specialCharBreakdown) {
        this.specialCharBreakdown = specialCharBreakdown;
    }
}
