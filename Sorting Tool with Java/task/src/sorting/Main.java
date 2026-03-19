package sorting;

import java.util.*;

public class Main {

    public static void main(final String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = readInputLines(scanner);

//        if (hasArgument(args, "-sortIntegers")) {
//            sortAndCountIntegers(inputLines);
//            return;
//        }

        String dataType = getDataType(args);
        String sortingType = getSortingType(args);

        if (dataType == null) {
            System.out.println("You must set value for -dataType");
            return;
        }

        switch (dataType) {
            case "long" -> processLongs(inputLines, sortingType);
            case "word" -> processWords(inputLines, sortingType);
            case "line" -> processLines(inputLines, sortingType);
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

    private static String getSortingType(String[] args) {
        String sortingType = "natural";
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-sortingType")) {
                sortingType = args[i + 1];
                break;
            }
        }
        return sortingType;
    }

//    private static boolean hasArgument(String[] args, String argument) {
//        return Arrays.asList(args).contains(argument);
//    }

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

    private static void sortAndCountWords(List<String> inputLines) {

        List<String> wordsList = parseWords(inputLines);
        wordsList.sort(null);
        int wordsCount = wordsList.size();
        System.out.printf("Total words: %d.%n", wordsCount);
        System.out.print("Sorted data:");
        for (String word : wordsList) {
            System.out.print(" " + word);
        }
    }

    private static void sortAndCountLines(List<String> inputLines) {

        List<String> linesList = new ArrayList<>(inputLines);
        linesList.sort(null);
        int linesCount = linesList.size();
        System.out.printf("Total lines: %d.%n", linesCount);
        System.out.print("Sorted data:");
        for (String line : linesList) {
            System.out.print(" " + line);
        }
    }

    private static void sortByCountAndCountIntegers(List<String> inputLines) {

        List<Long> numbersList = parseNumbers(inputLines);
        int numbersCount = numbersList.size();

        Map<Long, Integer> numbersMap = new HashMap<>();

        for (Long number : numbersList) {
            numbersMap.put(number, numbersMap.getOrDefault(number, 0) + 1);
        }

        List<Map.Entry<Long, Integer>> entries = new ArrayList<>(numbersMap.entrySet());

        entries.sort(
                Comparator.comparing(Map.Entry<Long, Integer>::getValue)
                        .thenComparing(Map.Entry<Long, Integer>::getKey)
        );

        System.out.printf("Total numbers: %d.%n", numbersCount);

        for (Map.Entry<Long, Integer> entry : entries) {
            long number = entry.getKey();
            int count = entry.getValue();
            int percentage = (int) Math.round((double) count / numbersCount * 100);

            System.out.println(number + ": " + count + " time(s), " + percentage + "%");
        }
    }

    private static void sortByCountAndCountWords(List<String> inputLines) {

        List<String> wordsList = parseWords(inputLines);
        int wordsCount = wordsList.size();

        Map<String, Integer> wordsMap = new HashMap<>();

        for (String word : wordsList) {
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordsMap.entrySet());

        entries.sort(
                Comparator.comparing(Map.Entry<String, Integer>::getValue)
                        .thenComparing(Map.Entry<String, Integer>::getKey)
        );

        System.out.printf("Total words: %d.%n", wordsCount);

        for (Map.Entry<String, Integer> entry : entries) {
            String word = entry.getKey();
            int count = entry.getValue();
            int percentage = (int) Math.round((double) count / wordsCount * 100);

            System.out.println(word + ": " + count + " time(s), " + percentage + "%");
        }
    }

    private static void sortByCountAndCountLines(List<String> inputLines) {

        List<String> linesList = new ArrayList<>(inputLines);
        int linesCount = linesList.size();

        Map<String, Integer> linesMap = new HashMap<>();

        for (String line : linesList) {
            linesMap.put(line, linesMap.getOrDefault(line, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(linesMap.entrySet());

        entries.sort(
                Comparator.comparing(Map.Entry<String, Integer>::getValue)
                        .thenComparing(Map.Entry<String, Integer>::getKey)
        );

        System.out.printf("Total lines: %d.%n", linesCount);

        for (Map.Entry<String, Integer> entry : entries) {
            String line = entry.getKey();
            int count = entry.getValue();
            int percentage = (int) Math.round((double) count / linesCount * 100);

            System.out.println(line + ": " + count + " time(s), " + percentage + "%");
        }
    }

    private static void processLongs(List<String> inputLines, String sortingType) {

        if (sortingType.equals("natural")) {
            sortAndCountIntegers(inputLines);
            return;
        }
        sortByCountAndCountIntegers(inputLines);
    }

    private static void processWords(List<String> inputLines, String sortingType) {

        if (sortingType.equals("natural")) {
            sortAndCountWords(inputLines);
            return;
        }
        sortByCountAndCountWords(inputLines);
    }

    private static void processLines(List<String> inputLines, String sortingType) {

        if (sortingType.equals("natural")) {
            sortAndCountLines(inputLines);
            return;
        }
        sortByCountAndCountLines(inputLines);
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
