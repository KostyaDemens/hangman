package by.bsuir.kostyademens;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {

    private static final String FILE_WITH_WORDS = "words.txt";
    private static final int MAX_MISTAKES = 6;
    private static final char MASK_SYMBOL = '_';
    private static char[] maskedWord;
    private static String secretWord;
    private static Set<String> triedLetters;
    private static int mistakes = 0;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        play();
    }

    private static void play() {
        printInitialMenu();
        String startOrExit = scanner.nextLine();
        while (true) {
            if (startOrExit.equalsIgnoreCase("н")) {
                playHangmanRound();
                printInitialMenu();
            }
            if (startOrExit.equalsIgnoreCase("в")) {
                System.out.println("Приходите ещё");
                break;
            }
            printIncorrectInputAlert();
            startOrExit = scanner.nextLine();
        }
    }

    private static void playHangmanRound() {
        secretWord = getRandomWordFromFile().toLowerCase();
        triedLetters = new TreeSet<>();
        initMaskedWord();

        while (true) {
            printPicture();
            printCurrentStatus();
            inviteToEnterNextLetter();
            String letter = scanner.nextLine().toLowerCase();
            clearScreen();

            if (!letter.matches("^[а-я]$")) {
                printIncorrectInputAlert();
                continue;
            }

            triedLetters.add(letter);
            if (secretWord.contains(letter)) {
                openGuessedLetters(letter.charAt(0));
            } else {
                mistakes++;
            }
            if (isWordGuessed()) {
                onWin();
                break;
            }
            if (isNoMoreAttemptsLeft()) {
                onLose();
                break;
            }
        }
    }

    private static String getRandomWordFromFile() {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(FILE_WITH_WORDS))) {
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл со словарем не найден");
        }
        int index = new Random().nextInt(words.size());
        return words.get(index);
    }

    private static void initMaskedWord() {
        maskedWord = new char[secretWord.length()];
        Arrays.fill(maskedWord, MASK_SYMBOL);
    }

    private static void openGuessedLetters(char letter) {
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                maskedWord[i] = letter;
            }
        }
    }

    private static boolean isWordGuessed() {
        for (char c : maskedWord) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private static boolean isNoMoreAttemptsLeft() {
        return mistakes == MAX_MISTAKES;
    }

    private static void onWin() {
        onEnd("YOU WIN");
    }

    private static void onLose() {
        onEnd("GAME OVER");
    }

    private static void onEnd(String message) {
        clearScreen();
        printPicture();
        System.out.println("**********\n" + message + "\n**********");
        System.out.printf("Загаданное слово: %s\n\n\n", secretWord.toUpperCase());
    }

    private static void printInitialMenu() {
        System.out.println("Добро пожаловать в игру!\n" +
                "===============================\n" +
                "Для Начала игры нажмите - (Н)\n" +
                "Для Выхода из игры нажмите - (В)\n" +
                "===============================\n");
    }

    private static void inviteToEnterNextLetter() {
        System.out.println("Введите следующую букву:");
    }

    private static void printCurrentStatus() {
        System.out.println("Загаданное слово: " + getMaskedWordAsString());
        System.out.println("Ошибки: " + mistakes);
        System.out.println("Использованные буквы: " + triedLetters);
    }

    private static String getMaskedWordAsString() {
        StringBuilder sb = new StringBuilder();
        for (char c : maskedWord) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static void clearScreen() {
        System.out.println("\n".repeat(10));
    }

    private static void printIncorrectInputAlert() {
        System.out.println("Вы ввели некорректный символ");
    }

    private static void printPicture() {
        List<String> scaffoldLevels = List.of(
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
        );
        System.out.println(scaffoldLevels.get(mistakes));
    }
}