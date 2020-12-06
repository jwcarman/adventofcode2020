package adventofcode;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;

public class Day5 {

    private static final String INPUT = readResource("Day5.txt");

    private static final String EXAMPLE = """
            BFFFBBFRRR
            FFFBBBFRRR
            BBFFBBFRLL""";

    @Test
    void part1() {
        System.out.println(readLines(INPUT).stream()
                .map(line -> new MutablePair<>(line.substring(0, 7), line.substring(7)))
                .mapToInt(pair -> toInt(pair.getLeft(), 'F', 'B') * 8 + toInt(pair.getRight(), 'L', 'R'))
                .max()
                .orElse(-1));

    }

    @Test
    void part2() {
        final List<Integer> seatIds = readLines(INPUT).stream()
                .map(line -> new MutablePair<>(line.substring(0, 7), line.substring(7)))
                .map(pair -> toInt(pair.getLeft(), 'F', 'B') * 8 + toInt(pair.getRight(), 'L', 'R'))
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < seatIds.size() - 1; i++) {
            final int seatId = seatIds.get(i);
            final int nextSeatId = seatIds.get(i + 1);
            if(nextSeatId - seatId > 1) {
                System.out.println(seatId + 1);
            }
        }
    }

    int toInt(String spec, char upper, char lower) {
        int min = 0;
        int max = (1 << spec.length()) - 1;
        for (char c : spec.toCharArray()) {
            final int range = max - min + 1;
            if (c == upper) {
                max -= range / 2;
            } else if (c == lower) {
                min += range / 2;
            }
        }
        return max;
    }


}
