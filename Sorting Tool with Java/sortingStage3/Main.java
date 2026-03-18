package sorting;

import java.util.*;

public class Main {

    public static void main(final String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = readInputLines(scanner);

        if (hasArgument(args, "-sortIntegers")) {
            sortAndCountIntegers(inputLines);
            return;
        }

        String dataType = getDataType(args);

        if (dataType == null) {
            System.out.println("Wrong usage, explanation blablabla...");
            return;
        }

        switch (dataType) {
            case "long" -> processLongs(inputLines);
            case "word" -> processWords(inputLines);
            case "line" -> processLines(inputLines);
            default -> System.out.println("Wrong data type!");
        }
    }

    private static String getDataType(String[] args) {
        String dataType = null;
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-dataType")) {
                dataType = args[i + 1];
                break;
            }
        }
        return dataType;
    }

    private static boolean hasArgument(String[] args, String argument) {
        return Arrays.asList(args).contains(argument);
    }

    private static void sortAndCountIntegers(List<String> inputLines) {

        List<Long> numbersList = parseNumbers(inputLines);
        numbersList.sort(null);
        int numbersCount = numbersList.size();
        System.out.printf("Total numbers: %d.%n", numbersCount);
        System.out.print("Sorted data:");
        for (Long number : numbersList) {
            System.out.print(" " + number);
        }
    }

    private static void processLongs(List<String> inputLines) {

        List<Long> numbersList = parseNumbers(inputLines);
        numbersList.sort(null);
        int numbersCount = numbersList.size();
        long maxNumber = numbersList.getLast();
        int occurrences = Collections.frequency(numbersList, maxNumber);
        int percentage = (int) Math.round((double) occurrences / numbersCount * 100);

        System.out.printf("Total numbers: %d.%n", numbersCount);
        System.out.printf("The greatest number: %s (%d time(s), %d%%).%n", maxNumber, occurrences, percentage);
    }

    private static void processWords(List<String> inputLines) {

        List<String> wordsList = parseWords(inputLines);
        wordsList.sort(Comparator.comparingInt(String::length));
        int wordsCount = wordsList.size();
        String maxLengthWord = wordsList.getLast();
        int wordOccurrences = Collections.frequency(wordsList, maxLengthWord);
        int wordPercentage = (int) Math.round((double) wordOccurrences / wordsCount * 100);

        System.out.printf("Total words: %d.%n", wordsCount);
        System.out.printf("The longest word: %s (%d time(s), %d%%).%n", maxLengthWord, wordOccurrences, wordPercentage);
    }

    private static void processLines(List<String> inputLines) {

        List<String> linesList = new ArrayList<>(inputLines);
        linesList.sort(Comparator.comparingInt(String::length));
        int linesCount = linesList.size();
        String maxLengthLine = linesList.getLast();
        int linesOccurrences = Collections.frequency(linesList, maxLengthLine);
        int linesPercentage = (int) Math.round((double) linesOccurrences / linesCount * 100);

        System.out.printf("Total lines: %d.%n", linesCount);
        System.out.printf("The longest line:%n%s%n(%d time(s), %d%%).%n", maxLengthLine, linesOccurrences, linesPercentage);
    }

    private static List<String> extractTokens(List<String> lines) {

        List<String> result = new ArrayList<>();
        for (String line : lines) {
            if (!line.isBlank()) {
                String[] tokens = line.split("\\s+");
                result.addAll(Arrays.asList(tokens));
            }
        }
        return result;
    }

    private static List<String> parseWords(List<String> lines) {

        return extractTokens(lines);
    }

    private static List<Long> parseNumbers(List<String> lines) {

        List<Long> longs = new ArrayList<>();
        List<String> tokens = extractTokens(lines);

        for (String token : tokens) {
            longs.add(Long.parseLong(token));
        }
        return longs;
    }

    static List<String> readInputLines(Scanner scanner) {

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }
}
