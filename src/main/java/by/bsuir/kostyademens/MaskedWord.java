package by.bsuir.kostyademens;

import java.util.Arrays;

public class MaskedWord {
    private String secretWord;
    private char[] maskedWord;

    public MaskedWord(String secretWord) {
        this.secretWord = secretWord;
        String symbolMaskedLetter = "_";
        maskedWord = new char[secretWord.length()];
        Arrays.fill(maskedWord, symbolMaskedLetter.charAt(0));
    }

    public String getSecretWord() {
        return secretWord;
    }

    public char[] getMaskedWord() {
        return maskedWord;
    }


    public void openLetter(char letter) {
        String word = this.secretWord;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                maskedWord[i] = letter;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char c : maskedWord) {
            sb.append(c);
        }
        return sb.toString();
    }
}
