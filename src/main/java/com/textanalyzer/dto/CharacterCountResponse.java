package com.textanalyzer.dto;

public class CharacterCountResponse {

    private int totalCharacters;
    private int charactersWithoutSpaces;

    public CharacterCountResponse() {}

    public CharacterCountResponse(int totalCharacters, int charactersWithoutSpaces) {
        this.totalCharacters = totalCharacters;
        this.charactersWithoutSpaces = charactersWithoutSpaces;
    }

    public int getTotalCharacters() {
        return totalCharacters;
    }

    public void setTotalCharacters(int totalCharacters) {
        this.totalCharacters = totalCharacters;
    }

    public int getCharactersWithoutSpaces() {
        return charactersWithoutSpaces;
    }

    public void setCharactersWithoutSpaces(int charactersWithoutSpaces) {
        this.charactersWithoutSpaces = charactersWithoutSpaces;
    }
}
