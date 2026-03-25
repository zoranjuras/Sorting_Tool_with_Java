package sorting;

import java.io.*;
import java.util.*;

public class Main {

    private static final Set<String> VALID_DATA_TYPES = Set.of("long", "word", "line");
    private static final Set<String> VALID_SORTING_TYPES = Set.of("natural", "byCount");

    private record Arguments(String dataType, String sortingType, String inputFile, String outputFile) {}

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Optional<Arguments> argumentsOptional = getArguments(args);
        if (argumentsOptional.isEmpty()) {
            return;
        }

        Arguments arguments = argumentsOptional.get();
        List<String> inputLines = readInputLines(scanner, arguments.inputFile());

        if (arguments.outputFile() != null) {
            try (PrintWriter writer = new PrintWriter(arguments.outputFile())) {
                process(arguments, inputLines, writer);
            } catch (FileNotFoundException e) {
                System.err.println("Error writing file: " + e.getMessage());
            }
        } else {
            PrintWriter writer = new PrintWriter(System.out);
            process(arguments, inputLines, writer);
            writer.flush();
        }
    }

    private static void process(Arguments arguments, List<String> inputLines, PrintWriter writer) {
        switch (arguments.dataType()) {
            case "long" -> processLongs(inputLines, arguments.sortingType(), writer);
            case "word" -> processWords(inputLines, arguments.sortingType(), writer);
            case "line" -> processLines(inputLines, arguments.sortingType(), writer);
            default -> throw new IllegalStateException("Unexpected data type: " + arguments.dataType());
        }
    }

    private static Optional<Arguments> getArguments(String[] args) {

        String dataType = "word";
        String sortingType = "natural";
        String inputFile = null;
        String outputFile = null;

        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "-dataType" -> {
                    if (VALID_DATA_TYPES.contains(args[i + 1])) {
                        dataType = args[i + 1];
                        i++;
                    } else {
                        System.out.println("No data type defined!");
                        return Optional.empty();
                    }
                }
                case "-sortingType" -> {
                    if (VALID_SORTING_TYPES.contains(args[i + 1])) {
                        sortingType = args[i + 1];
                        i++;
                    } else {
                        System.out.println("No sorting type defined!");
                        return Optional.empty();
                    }
                }
                case "-inputFile" -> {
                    inputFile = args[i + 1];
                    i++;
                }
                case "-outputFile" -> {
                    outputFile = args[i + 1];
                    i++;
                }
                default ->  {
                    System.err.printf("\"%s\" is not a valid parameter. It will be skipped.%n", args[i]);
                }
            }
        }
        return Optional.of(new Arguments(dataType, sortingType, inputFile, outputFile));
    }

    private static <T extends Comparable<? super T>> void printNaturalSorting(List<T> data, String label, PrintWriter writer) {

        int elementsCount = data.size();
        writer.printf("Total %s: %d.%n", label, elementsCount);
        writer.print("Sorted data:");
        for (T element : data) {
            writer.print(" " + element);
        }
    }

    private static <T extends Comparable<? super T>> void printByCountSorting(List<T> data, String label, PrintWriter writer) {

        int elementsCount = data.size();

        Map<T, Integer> elementsMap = new HashMap<>();

        for (T element : data) {
            elementsMap.put(element, elementsMap.getOrDefault(element, 0) + 1);
        }

        List<Map.Entry<T, Integer>> entries = new ArrayList<>(elementsMap.entrySet());

        entries.sort(
                Comparator.comparing(Map.Entry<T, Integer>::getValue)
                        .thenComparing(Map.Entry::getKey)
        );

        writer.printf("Total %s: %d.%n", label, elementsCount);

        for (Map.Entry<T, Integer> entry : entries) {
            T element = entry.getKey();
            int count = entry.getValue();
            int percentage = (int) Math.round((double) count / elementsCount * 100);

            writer.println(element + ": " + count + " time(s), " + percentage + "%");
        }
    }

    private static void processLongs(List<String> inputLines, String sortingType, PrintWriter writer) {

        if ("natural".equals(sortingType)) {
            printNaturalSorting(parseAndSortNumbers(inputLines), "numbers", writer);
        } else {
            printByCountSorting(parseAndSortNumbers(inputLines), "numbers", writer);
        }
    }

    private static void processWords(List<String> inputLines, String sortingType, PrintWriter writer) {

        if ("natural".equals(sortingType)) {
            printNaturalSorting(parseWords(inputLines), "words", writer);
        } else {
            printByCountSorting(parseWords(inputLines), "words", writer);
        }
    }

    private static void processLines(List<String> inputLines, String sortingType, PrintWriter writer) {

        if ("natural".equals(sortingType)) {
            printNaturalSorting(inputLines, "lines", writer);
        } else {
            printByCountSorting(inputLines, "lines", writer);
        }
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

    private static List<Long> parseAndSortNumbers(List<String> lines) {

        List<Long> longs = new ArrayList<>();
        List<String> tokens = extractTokens(lines);

        for (String token : tokens) {
            try {
                longs.add(Long.parseLong(token));
            } catch (NumberFormatException e) {
                System.err.printf("\"%s\" is not a long. It will be skipped.%n", token);
            }
        }
        longs.sort(null);
        return longs;
    }

    static List<String> readInputLines(Scanner scanner, String inputFile) {

        List<String> lines = new ArrayList<>();

        if (inputFile == null) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } else {
            try (Scanner fileScanner = new Scanner(new File(inputFile))) {
                while (fileScanner.hasNextLine()) {
                    lines.add(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return lines;
    }
}
