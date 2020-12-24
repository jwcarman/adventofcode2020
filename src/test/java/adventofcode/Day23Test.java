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
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import adventofcode.cups.CupsGame;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day23Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day23.txt");
    private static final String EXAMPLE_INPUT = "389125467";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo("67384529");
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo(149245887792L);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private String calculateAnswerPart1(String input) {
        final CupsGame game = new CupsGame(parseCups(input));
        IntStream.range(0, 100).forEach(moveNumber -> {
            game.move();
        });
        return Arrays.stream(game.cupsAfter(1, 8))
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private long calculateAnswerPart2(String input) {
        final CupsGame game = new CupsGame(parseCups(input), 1000000);
        for (int i = 0; i < 10000000; ++i) {
            game.move();
        }
        return Arrays.stream(game.cupsAfter(1, 2))
                .mapToLong(i -> i)
                .reduce(1, (a, b) -> a * b);
    }

    private LinkedList<Integer> parseCups(String input) {
        return input.chars().mapToObj(c -> c - '0').collect(Collectors.toCollection(LinkedList::new));
    }
}
