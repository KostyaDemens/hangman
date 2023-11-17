package by.bsuir.kostyademens;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dictionary {

    public String getRandomWordFromFile() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("words.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File has not found");
        }
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(reader)){
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        int index = random.nextInt(words.size());
        return words.get(index);
    }
}
