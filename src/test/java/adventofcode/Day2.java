package adventofcode;

import java.io.StringReader;
import java.util.List;
import java.util.Scanner;

import adventofcode.io.Input;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;

public class Day2 {

    private static final String INPUT = readResource("Day2.txt");

    @Test
    void part1() {
        final List<String> lines = Input.readLines(new StringReader(INPUT));

        final long count = lines.stream()
                .filter(line -> {
                    Scanner scanner = new Scanner(line);
                    scanner.useDelimiter("-|:\\s|\\s+");
                    final int min = scanner.nextInt();
                    final int max = scanner.nextInt();
                    final char letter = scanner.next().charAt(0);
                    final String password = scanner.next();
                    final int matches = StringUtils.countMatches(password, letter);
                    return matches >= min && matches <= max;
                }).count();
        System.out.println(count);
    }

    @Test
    void part2() {
        final List<String> lines = Input.readLines(new StringReader(INPUT));

        final long count = lines.stream()
                .filter(line -> {
                    Scanner scanner = new Scanner(line);
                    scanner.useDelimiter("-|:\\s|\\s+");
                    final int index1 = scanner.nextInt() - 1;
                    final int index2 = scanner.nextInt() - 1;
                    final char letter = scanner.next().charAt(0);
                    final String password = scanner.next();
                    return password.charAt(index1) == letter ^ password.charAt(index2) == letter;
                }).count();
        System.out.println(count);
    }
}
