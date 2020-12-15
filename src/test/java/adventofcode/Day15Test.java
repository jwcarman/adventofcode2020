/*
 * Copyright (c) 2020 James Carman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day15Test {

    private static final String INPUT = readResource("Day15.txt");
    private static final String EXAMPLE_INPUT = "0,3,6";

    @Test
    void part1() {
        log.info("Part One: {}", findNthNumber(INPUT, 2020));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", findNthNumber(INPUT, 30000000));
    }

    @Test
    void example1() {
        assertThat(findNthNumber(EXAMPLE_INPUT, 2020)).isEqualTo(436);
    }

    @Test
    void example2() {
        assertThat(findNthNumber(EXAMPLE_INPUT, 30000000)).isEqualTo(175594);
    }

    private List<Integer> parseStartingNumbers(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int findNthNumber(String input, int n) {
        final long before = System.nanoTime();
        try {
            return findNthNumber(parseStartingNumbers(input), n);
        } finally {
            log.info("Finished finding {}th number in {}ms", n, (System.nanoTime() - before) / 1000000.0);
        }
    }

    private int findNthNumber(List<Integer> startingNumbers, int n) {
        final int[] previousTurns = new int[n + 1];
        for (int i = 0; i < startingNumbers.size() - 1; i++) {
            Integer number = startingNumbers.get(i);
            previousTurns[number] = i + 1;
        }
        int lastNumberSpoken = startingNumbers.get(startingNumbers.size() - 1);
        for (int turn = startingNumbers.size(); turn <= n; ++turn) {
            if (turn == n) {
                return lastNumberSpoken;
            }
            final int previousTurn = previousTurns[lastNumberSpoken];
            previousTurns[lastNumberSpoken] = turn;
            if (previousTurn == 0) {
                lastNumberSpoken = 0;
            } else {
                lastNumberSpoken = turn - previousTurn;
            }
        }

        return -1;
    }

}
