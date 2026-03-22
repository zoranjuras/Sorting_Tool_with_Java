package sorting;

import java.util.*;

public class Main {
    private static final String[] VALID_DATA_TYPES = {"long", "word", "line"};
    private static final String[] VALID_SORTING_TYPES = {"natural", "byCount"};

    private record Arguments(String dataType, String sortingType) {}

    public static void main(final String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = readInputLines(scanner);

        Optional<Arguments> argumentsOptional = getArguments(args);
        if (argumentsOptional.isEmpty()) {
            return;
        }

        Arguments arguments = argumentsOptional.get();

        switch (arguments.dataType()) {
            case "long" -> processLongs(inputLines, arguments.sortingType());
            case "word" -> processWords(inputLines, arguments.sortingType());
            case "line" -> processLines(inputLines, arguments.sortingType());
            default -> System.out.println("Wrong data type!");
        }
    }

    private static Optional<Arguments> getArguments(String[] args) {
        List<String> dataTypes = List.of(VALID_DATA_TYPES);
        List<String> sortingTypes = List.of(VALID_SORTING_TYPES);

        String dataType = "word";
        String sortingType = "natural";

        for (int i = 0; i < args.length - 1; i++) {
            if ("-dataType".equals(args[i])) {
                if (dataTypes.contains(args[i + 1])) {
                    dataType = args[i + 1];
                    i++;
                } else {
                    System.out.println("No data type defined!");
                    return Optional.empty();
                }
            } else if ("-sortingType".equals(args[i])) {
                if (sortingTypes.contains(args[i + 1])) {
                    sortingType = args[i + 1];
                    i++;
                } else {
                    System.out.println("No sorting type defined!");
                    return Optional.empty();
                }
            } else {
                System.out.printf("\"%s\" is not a valid parameter. It will be skipped.%n", args[i]);
            }
        }
        return Optional.of(new Arguments(dataType, sortingType));
    }

    private static <T extends Comparable<? super T>> void printNaturalSorting(List<T> data, String label) {

        int elementsCount = data.size();
        System.out.printf("Total %s: %d.%n", label, elementsCount);
        System.out.print("Sorted data:");
        for (T element : data) {
            System.out.print(" " + element);
        }
    }

    private static <T extends Comparable<? super T>> void printByCountSorting(List<T> data, String label) {

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

        System.out.printf("Total %s: %d.%n", label, elementsCount);

        for (Map.Entry<T, Integer> entry : entries) {
            T element = entry.getKey();
            int count = entry.getValue();
            int percentage = (int) Math.round((double) count / elementsCount * 100);

            System.out.println(element + ": " + count + " time(s), " + percentage + "%");
        }
    }

    private static void processLongs(List<String> inputLines, String sortingType) {

        if ("natural".equals(sortingType)) {
            printNaturalSorting(parseAndSortNumbers(inputLines), "numbers");
            return;
        }
        printByCountSorting(parseAndSortNumbers(inputLines), "numbers");
    }

    private static void processWords(List<String> inputLines, String sortingType) {

        if ("natural".equals(sortingType)) {
            printNaturalSorting(parseWords(inputLines), "words");
            return;
        }
        printByCountSorting(parseWords(inputLines), "words");
    }

    private static void processLines(List<String> inputLines, String sortingType) {

        if ("natural".equals(sortingType)) {
            printNaturalSorting(inputLines, "lines");
            return;
        }
        printByCountSorting(inputLines, "lines");
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
                System.out.printf("\"%s\" is not a long. It will be skipped.%n", token);
            }
        }
        longs.sort(null);
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
