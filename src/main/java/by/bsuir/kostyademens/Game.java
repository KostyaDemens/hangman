package by.bsuir.kostyademens;

import java.util.*;


public class Game {
    private int mistakeCounter;
    private final Dictionary dictionary = new Dictionary();
    private Scanner scanner;


    public void initGame() {
        startGame();
    }

    public void startGame() {
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


    public void mainLogic() {
        mistakeCounter = 0;
        Set<String> listOfLetters = new HashSet<>();
        String secretWord = dictionary.getRandomWordFromFile().toLowerCase();
        MaskedWord maskedWord = new MaskedWord(secretWord);
        boolean winFlag = true;
        while (winFlag) {
            correctPrint();
            System.out.printf("Загаданное слово: %s\n" +
                    "Ошибки: (%d)\n" +
                    "Использованные буквы: %s\n" +
                    "Введите букву: ", maskedWord, mistakeCounter, listOfLetters);
            scanner = new Scanner(System.in);
            String letter = scanner.nextLine();
            System.out.println("\n \n \n \n \n \n \n \n \n \n");
            if (letter.matches("[а-я]")) {
                if (secretWord.contains(letter)) {
                    listOfLetters.add(letter);
                    maskedWord.openLetter(letter.charAt(0));
                    if (!Arrays.toString(maskedWord.getMaskedWord()).contains("_")) {
                        System.out.println("\n \n \n \n \n \n \n \n \n \n");
                        correctPrint();
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
                correctPrint();
                System.out.println("**********\n" +
                        "GAME  OVER\n" +
                        "**********");
                System.out.printf("Загаданное слово: %s\n\n\n", secretWord.toUpperCase());
                winFlag = false;
                startGame();
            }
        }
    }

    public void correctPrint() {
        switch (mistakeCounter) {
            case 0:
                printGameField(Scaffold.ZERO);
                break;
            case 1:
                printGameField(Scaffold.ONE);
                break;
            case 2:
                printGameField(Scaffold.TWO);
                break;
            case 3:
                printGameField(Scaffold.THREE);
                break;
            case 4:
                printGameField(Scaffold.FOUR);
                break;
            case 5:
                printGameField(Scaffold.FIVE);
                break;
            case 6:
                printGameField(Scaffold.SIX);
                break;
            default:
                break;
        }
    }


    public void printGameField(Scaffold scaffold) {
        for (Scaffold s : Scaffold.values()) {
            if (s == scaffold) {
                System.out.println(s);
            }
        }

    }
}

