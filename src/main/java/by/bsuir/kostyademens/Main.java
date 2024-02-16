package by.bsuir.kostyademens;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {


    private static char[] maskedWord = new char[]{};
    private static String secretWord = getRandomWordFromFile();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        startGame();
    }


    public static void startGame() {
        System.out.println("Добро пожаловать в игру!\n" +
                "===============================\n" +
                "Для Начала игры нажимте - (Н)\n" +
                "Для Выхода из игры нажмите - (В)\n" +
                "===============================\n");
        scanner = new Scanner(System.in);
        String endOrBegin = scanner.nextLine();
        boolean correctInput = false;
        while (!correctInput) {
            if (endOrBegin.matches("[НВнв]")) {
                if (endOrBegin.equalsIgnoreCase("н")) {
                    mainLogic();
                    correctInput = true;

                } else if (endOrBegin.equalsIgnoreCase("в")) {
                    System.out.println("Приходите ещё");
                    correctInput = true;
                }
            } else {
                System.out.println("Вы ввели некорректный символ");
                endOrBegin = scanner.nextLine();
            }
        }
    }

    public static String getRandomWordFromFile() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("words.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File has not found");
        }
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(reader)) {
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

    public static char[] createMaskedWord(String secretWord) {
        maskedWord = new char[secretWord.length()];
        String symbolMarkedLetter = "_";
        Arrays.fill(maskedWord, symbolMarkedLetter.charAt(0));
        return maskedWord;
    }

    public static void openLetter(char letter) {
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                maskedWord[i] = letter;
            }
        }
    }

    public static String getPicture(int errorNum) {
        List<String> scaffoldLevels = new ArrayList<>(Arrays.asList(
                "  +---+\n" +
                        "  |   |\n" +
                        "      |\n" +
                        "      |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=========\n",

                "  +---+\n" +
                        "  |   |\n" +
                        "  O   |\n" +
                        "      |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=========\n",

                "  +---+\n" +
                        "  |   |\n" +
                        "  O   |\n" +
                        "  |   |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=========\n",

                "  +---+\n" +
                        "  |   |\n" +
                        "  O   |\n" +
                        " /|   |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=========\n",

                "  +---+\n" +
                        "  |   |\n" +
                        "  O   |\n" +
                        " /|\\  |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=========\n",

                "  +---+\n" +
                        "  |   |\n" +
                        "  O   |\n" +
                        " /|\\  |\n" +
                        " /    |\n" +
                        "      |\n" +
                        "=========\n",

                "  +---+\n" +
                        "  |   |\n" +
                        "  O   |\n" +
                        " /|\\  |\n" +
                        " / \\  |\n" +
                        "      |\n" +
                        "=========\n"
        ));

        return scaffoldLevels.get(errorNum);
    }

    public static void mainLogic() {
        int mistakeCounter = 0;
        Set<String> listOfLetters = new HashSet<>();
        secretWord = getRandomWordFromFile().toLowerCase();
        maskedWord = createMaskedWord(secretWord);
        boolean winFlag = true;
        while (winFlag) {
            System.out.println(getPicture(mistakeCounter));
            System.out.printf("Загаданное слово: %s\n" +
                    "Ошибки: (%d)\n" +
                    "Использованные буквы: %s\n" +
                    "Введите букву: ", getMaskedWord(maskedWord), mistakeCounter, listOfLetters);
            String letter = scanner.nextLine();
            System.out.println("\n \n \n \n \n \n \n \n \n \n");
            if (letter.matches("[а-я]")) {
                if (secretWord.contains(letter)) {
                    listOfLetters.add(letter);
                    openLetter(letter.charAt(0));
                    if (!Arrays.toString(maskedWord).contains("_")) {
                        System.out.println("\n \n \n \n \n \n \n \n \n \n");
                        getPicture(mistakeCounter);
                        System.out.println("**********\n" +
                                "GAME  WIN\n" +
                                "**********");
                        System.out.printf("Загаданное слово: %s\n\n\n", secretWord.toUpperCase());
                        winFlag = false;
                        startGame();
                    }
                } else {
                    if (!listOfLetters.contains(letter)) {
                        listOfLetters.add(letter);
                        mistakeCounter++;
                    }
                }
            }
            int maxNumberOfMistake = 6;
            if (mistakeCounter == maxNumberOfMistake) {
                System.out.println("\n \n \n \n \n \n \n \n \n \n");
                System.out.println(getPicture(maxNumberOfMistake));
                System.out.println("**********\n" +
                        "GAME  OVER\n" +
                        "**********");
                System.out.printf("Загаданное слово: %s\n\n\n", secretWord.toUpperCase());
                winFlag = false;
                startGame();
            }
        }
    }

    public static String getMaskedWord(char[] maskedWord) {
        StringBuilder sb = new StringBuilder();
        for (char c : maskedWord) {
            sb.append(c);
        }
        return sb.toString();

    }

}