package adventofcode;

import java.util.List;

import adventofcode.io.Input;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;

public class Day3 {

    private static final String MAP = readResource("Day3.txt");

    private static long countTrees(List<String> lines, int dx, int dy) {
        final int maxX = lines.get(0).length();
        int x = 0;
        int y = 0;
        long count = 0;
        while (y < lines.size()) {
            if (lines.get(y).charAt(x) == '#') {
                count = count + 1;
            }
            y += dy;
            x = (x + dx) % maxX;
        }
        return count;
    }

    @Test
    void part1() {
        final List<String> lines = Input.readLines(MAP);
        System.out.println(countTrees(lines, 3, 1));
    }

    @Test
    void part2() {
        final List<String> lines = Input.readLines(MAP);
        System.out.println(
                countTrees(lines, 1, 1) *
                        countTrees(lines, 3, 1) *
                        countTrees(lines, 5, 1) *
                        countTrees(lines, 7, 1) *
                        countTrees(lines, 1, 2)
        );
    }
}
