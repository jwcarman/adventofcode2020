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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.ferry.Seat;
import adventofcode.ferry.SeatFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day11Test {

    private static final String INPUT = readResource("Day11.txt");

    private static final String EXAMPLE_INPUT = """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL""";

    @Test
    void part1() {
        final long count = calculateAnswerPart1(INPUT);
        log.info("Part One: {}", count);
    }

    @Test
    void part2() {
        final long count = calculateAnswerPart2(INPUT);
        log.info("Part Two: {}", count);
    }

    @Test
    void example1() {
        final long count = calculateAnswerPart1(EXAMPLE_INPUT);
        assertThat(count).isEqualTo(37);
    }

    @Test
    void example2() {
        final long count = calculateAnswerPart2(EXAMPLE_INPUT);
        assertThat(count).isEqualTo(26);
    }

    private long calculateAnswerPart1(String input) {
        char[][] state = parseGrid(input);
        final List<Seat> seats = new SeatFactory(state).createDirectNeighborSeats();
        return calculateAnswer(state, seats, 4);
    }

    private long calculateAnswer(char[][] currentState, List<Seat> seats, int maxNeighbors) {
        Set<Seat> modifiable = new HashSet<>(seats);
        while (!modifiable.isEmpty()) {
            final char[][] nextState = copy(currentState);
            modifiable = applyRules(modifiable, currentState, nextState, maxNeighbors);
            currentState = nextState;
        }
        return countOccupied(seats, currentState);
    }

    private Set<Seat> applyRules(Set<Seat> modifiable, final char[][] oldState, final char[][] newState, final int maxNeighbors) {
        return modifiable.stream()
                .filter(seat -> seat.applyRules(oldState, newState, maxNeighbors))
                .flatMap(seat -> seat.getNeighbors().stream())
                .collect(Collectors.toSet());
    }

    private long calculateAnswerPart2(String input) {
        char[][] state = parseGrid(input);
        final List<Seat> seats = new SeatFactory(state).createVisibleNeighborSeats();

        return calculateAnswer(state, seats, 5);
    }

    private long countOccupied(List<Seat> seats, final char[][] state) {
        return seats.stream()
                .filter(seat -> seat.isOccupied(state))
                .count();
    }

    private char[][] copy(char[][] original) {
        return Arrays.stream(original)
                .map(char[]::clone)
                .toArray(x -> original.clone());
    }

    private char[][] parseGrid(String input) {
        final List<String> lines = readLines(input);
        final char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            grid[i] = line.toCharArray();
        }
        return grid;
    }
}
