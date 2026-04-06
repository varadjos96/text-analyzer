package com.textanalyzer.service;

import com.textanalyzer.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TextAnalysisServiceTest {

    private TextAnalysisService service;

    @BeforeEach
    void setUp() {
        service = new TextAnalysisService();
    }

    // ==================== Word Count Tests ====================

    @Nested
    @DisplayName("Word Count Tests")
    class WordCountTests {

        @Test
        @DisplayName("Should count words in a simple sentence")
        void shouldCountWordsInSimpleSentence() {
            WordCountResponse response = service.getWordCount("Hello world from Spring Boot");
            assertEquals(5, response.getWordCount());
        }

        @Test
        @DisplayName("Should return 1 for a single word")
        void shouldReturnOneForSingleWord() {
            WordCountResponse response = service.getWordCount("Hello");
            assertEquals(1, response.getWordCount());
        }

        @Test
        @DisplayName("Should return 0 for empty string")
        void shouldReturnZeroForEmptyString() {
            WordCountResponse response = service.getWordCount("");
            assertEquals(0, response.getWordCount());
        }

        @Test
        @DisplayName("Should return 0 for null input")
        void shouldReturnZeroForNull() {
            WordCountResponse response = service.getWordCount(null);
            assertEquals(0, response.getWordCount());
        }

        @Test
        @DisplayName("Should return 0 for whitespace-only string")
        void shouldReturnZeroForWhitespace() {
            WordCountResponse response = service.getWordCount("   ");
            assertEquals(0, response.getWordCount());
        }

        @Test
        @DisplayName("Should handle multiple spaces between words")
        void shouldHandleMultipleSpaces() {
            WordCountResponse response = service.getWordCount("Hello   world   test");
            assertEquals(3, response.getWordCount());
        }

        @Test
        @DisplayName("Should handle tabs and newlines")
        void shouldHandleTabsAndNewlines() {
            WordCountResponse response = service.getWordCount("Hello\tworld\nnew\rline");
            assertEquals(4, response.getWordCount());
        }
    }

    // ==================== Character Count Tests ====================

    @Nested
    @DisplayName("Character Count Tests")
    class CharacterCountTests {

        @Test
        @DisplayName("Should count all characters including spaces")
        void shouldCountAllCharacters() {
            CharacterCountResponse response = service.getCharacterCount("Hello World");
            assertEquals(11, response.getTotalCharacters());
        }

        @Test
        @DisplayName("Should count characters without spaces")
        void shouldCountCharactersWithoutSpaces() {
            CharacterCountResponse response = service.getCharacterCount("Hello World");
            assertEquals(10, response.getCharactersWithoutSpaces());
        }

        @Test
        @DisplayName("Should return 0 for empty string")
        void shouldReturnZeroForEmptyString() {
            CharacterCountResponse response = service.getCharacterCount("");
            assertEquals(0, response.getTotalCharacters());
            assertEquals(0, response.getCharactersWithoutSpaces());
        }

        @Test
        @DisplayName("Should return 0 for null input")
        void shouldReturnZeroForNull() {
            CharacterCountResponse response = service.getCharacterCount(null);
            assertEquals(0, response.getTotalCharacters());
            assertEquals(0, response.getCharactersWithoutSpaces());
        }

        @Test
        @DisplayName("Should count special characters")
        void shouldCountSpecialCharacters() {
            CharacterCountResponse response = service.getCharacterCount("Hi! @#");
            assertEquals(6, response.getTotalCharacters());
            assertEquals(5, response.getCharactersWithoutSpaces());
        }

        @Test
        @DisplayName("Should exclude tabs and newlines from without-spaces count")
        void shouldExcludeTabsAndNewlines() {
            CharacterCountResponse response = service.getCharacterCount("a\tb\nc");
            assertEquals(5, response.getTotalCharacters());
            assertEquals(3, response.getCharactersWithoutSpaces());
        }
    }

    // ==================== Frequent Words Tests ====================

    @Nested
    @DisplayName("Frequent Words Tests")
    class FrequentWordsTests {

        @Test
        @DisplayName("Should find most frequent words")
        void shouldFindMostFrequentWords() {
            FrequentWordsResponse response = service.getFrequentWords("the cat sat on the mat the cat");
            Map<String, Integer> words = response.getFrequentWords();
            assertEquals(3, words.get("the"));
            assertEquals(2, words.get("cat"));
        }

        @Test
        @DisplayName("Should be case insensitive")
        void shouldBeCaseInsensitive() {
            FrequentWordsResponse response = service.getFrequentWords("Hello HELLO hello");
            Map<String, Integer> words = response.getFrequentWords();
            assertEquals(3, words.get("hello"));
        }

        @Test
        @DisplayName("Should return correct unique word count")
        void shouldReturnCorrectUniqueWordCount() {
            FrequentWordsResponse response = service.getFrequentWords("one two three one two one");
            assertEquals(3, response.getTotalUniqueWords());
        }

        @Test
        @DisplayName("Should return empty map for empty string")
        void shouldReturnEmptyForEmptyString() {
            FrequentWordsResponse response = service.getFrequentWords("");
            assertTrue(response.getFrequentWords().isEmpty());
            assertEquals(0, response.getTotalUniqueWords());
        }

        @Test
        @DisplayName("Should return empty map for null input")
        void shouldReturnEmptyForNull() {
            FrequentWordsResponse response = service.getFrequentWords(null);
            assertTrue(response.getFrequentWords().isEmpty());
            assertEquals(0, response.getTotalUniqueWords());
        }

        @Test
        @DisplayName("Should strip punctuation from word boundaries")
        void shouldStripPunctuation() {
            FrequentWordsResponse response = service.getFrequentWords("hello, hello! hello.");
            Map<String, Integer> words = response.getFrequentWords();
            assertEquals(3, words.get("hello"));
            assertEquals(1, response.getTotalUniqueWords());
        }

        @Test
        @DisplayName("Should limit results to top 10")
        void shouldLimitToTop10() {
            String text = "a b c d e f g h i j k l m n o p";
            FrequentWordsResponse response = service.getFrequentWords(text);
            assertTrue(response.getFrequentWords().size() <= 10);
            assertEquals(16, response.getTotalUniqueWords());
        }

        @Test
        @DisplayName("Should sort by frequency descending")
        void shouldSortByFrequencyDescending() {
            FrequentWordsResponse response = service.getFrequentWords("a a a b b c");
            Map<String, Integer> words = response.getFrequentWords();
            int prev = Integer.MAX_VALUE;
            for (int count : words.values()) {
                assertTrue(count <= prev);
                prev = count;
            }
        }
    }

    // ==================== Special Character Count Tests ====================

    @Nested
    @DisplayName("Special Character Count Tests")
    class SpecialCharCountTests {

        @Test
        @DisplayName("Should count special characters")
        void shouldCountSpecialCharacters() {
            SpecialCharCountResponse response = service.getSpecialCharCount("Hello! How are you?");
            assertEquals(2, response.getTotalSpecialCharacters());
        }

        @Test
        @DisplayName("Should provide breakdown of special characters")
        void shouldProvideBreakdown() {
            SpecialCharCountResponse response = service.getSpecialCharCount("Hi!! @user #tag");
            Map<Character, Integer> breakdown = response.getSpecialCharBreakdown();
            assertEquals(2, breakdown.get('!'));
            assertEquals(1, breakdown.get('@'));
            assertEquals(1, breakdown.get('#'));
        }

        @Test
        @DisplayName("Should return 0 for text with no special characters")
        void shouldReturnZeroForNoSpecialChars() {
            SpecialCharCountResponse response = service.getSpecialCharCount("Hello World 123");
            assertEquals(0, response.getTotalSpecialCharacters());
            assertTrue(response.getSpecialCharBreakdown().isEmpty());
        }

        @Test
        @DisplayName("Should return 0 for null input")
        void shouldReturnZeroForNull() {
            SpecialCharCountResponse response = service.getSpecialCharCount(null);
            assertEquals(0, response.getTotalSpecialCharacters());
            assertTrue(response.getSpecialCharBreakdown().isEmpty());
        }

        @Test
        @DisplayName("Should return 0 for empty string")
        void shouldReturnZeroForEmptyString() {
            SpecialCharCountResponse response = service.getSpecialCharCount("");
            assertEquals(0, response.getTotalSpecialCharacters());
        }

        @Test
        @DisplayName("Should not count whitespace as special")
        void shouldNotCountWhitespace() {
            SpecialCharCountResponse response = service.getSpecialCharCount("hello world");
            assertEquals(0, response.getTotalSpecialCharacters());
        }

        @Test
        @DisplayName("Should handle string of only special characters")
        void shouldHandleOnlySpecialChars() {
            SpecialCharCountResponse response = service.getSpecialCharCount("!@#$%");
            assertEquals(5, response.getTotalSpecialCharacters());
        }

        @Test
        @DisplayName("Should count various punctuation marks")
        void shouldCountVariousPunctuation() {
            SpecialCharCountResponse response = service.getSpecialCharCount("a.b,c;d:e");
            assertEquals(4, response.getTotalSpecialCharacters());
            Map<Character, Integer> breakdown = response.getSpecialCharBreakdown();
            assertEquals(1, breakdown.get('.'));
            assertEquals(1, breakdown.get(','));
            assertEquals(1, breakdown.get(';'));
            assertEquals(1, breakdown.get(':'));
        }
    }
}
