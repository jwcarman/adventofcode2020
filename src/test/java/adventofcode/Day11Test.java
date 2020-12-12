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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.ferry.Seat;
import adventofcode.ferry.SeatLayout;
import adventofcode.ferry.SeatState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
        final SeatLayout layout = new SeatLayout(input);
        final List<Seat> seats = layout.createDirectNeighborSeats();
        return calculateAnswer(layout.createInitialState(), seats, 4);
    }

    private long calculateAnswer(SeatState currentState, List<Seat> seats, int maxNeighbors) {
        Set<Seat> modifiable = new HashSet<>(seats);
        while (!modifiable.isEmpty()) {
            SeatState nextState = currentState.clone();
            modifiable = applyRules(modifiable, currentState, nextState, maxNeighbors);
            currentState = nextState;
        }
        return countOccupied(seats, currentState);
    }

    private Set<Seat> applyRules(Set<Seat> modifiable, SeatState oldState, SeatState newState, final int maxNeighbors) {
        final Set<Seat> modified = new HashSet<>();
        for (Seat seat : modifiable) {
            if (seat.applyRules(oldState, newState, maxNeighbors)) {
                modified.add(seat);
            }
        }
        return modified;
    }

    private long calculateAnswerPart2(String input) {
        final SeatLayout layout = new SeatLayout(input);
        final List<Seat> seats = layout.createVisibleNeighborSeats();
        return calculateAnswer(layout.createInitialState(), seats, 5);
    }

    private long countOccupied(List<Seat> seats, SeatState state) {
        long count = 0;
        for (Seat seat : seats) {
            if (seat.isOccupied(state)) {
                count++;
            }
        }
        return count;
    }
}
