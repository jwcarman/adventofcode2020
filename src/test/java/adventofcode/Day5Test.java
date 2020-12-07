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

import adventofcode.boarding.BoardingPass;
import adventofcode.boarding.NextSeatIdPredicate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day5Test {

    private static final String INPUT = readResource("Day5.txt");

    private static final String EXAMPLE_INPUT = """
            BFFFBBFRRR
            FFFBBBFRRR
            BBFFBBFRLL""";

    @Test
    void part1() {
        log.info("Part One: {}", findMaxSeatId(INPUT));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", findMissingSeatId(INPUT));
    }

    private Integer findMissingSeatId(String input) {
        return readLines(input).stream()
                .map(BoardingPass::new)
                .map(BoardingPass::calculateSeatId)
                .sorted()
                .filter(new NextSeatIdPredicate())
                .findFirst()
                .map(seatId -> seatId - 1)
                .orElse(-1);
    }

    @Test
    void example1() {
        assertThat(findMaxSeatId(EXAMPLE_INPUT)).isEqualTo(820);
    }

    private int findMaxSeatId(String input) {
        return readLines(input).stream()
                .map(BoardingPass::new)
                .mapToInt(BoardingPass::calculateSeatId)
                .max()
                .orElse(-1);
    }


}
