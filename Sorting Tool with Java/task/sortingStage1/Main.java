package sorting;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numbersCount = 0;
        long maxNumber = Long.MIN_VALUE;
        int occurrences = 0;

        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            numbersCount++;
            if (number == maxNumber) {
                occurrences++;
            }
            if (number > maxNumber) {
                maxNumber = number;
                occurrences = 1;
            }
        }
        System.out.printf("Total numbers: %d.\n", numbersCount);
        System.out.printf("The greatest number: %d (%d time(s)).", maxNumber, occurrences);
    }
}