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

import adventofcode.recitation.RecitationGame;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day15Test {

    private static final String INPUT = readResource("Day15.txt");
    private static final String EXAMPLE_INPUT = "0,3,6";
    public static final int PART_1_LIMIT = 2020;
    public static final int PART_2_LIMIT = 30000000;

    @Test
    void part1() {
        log.info("Part One: {}", findNthNumber(INPUT, PART_1_LIMIT));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", findNthNumber(INPUT, PART_2_LIMIT));
    }

    @Test
    void example1() {
        assertThat(findNthNumber(EXAMPLE_INPUT, PART_1_LIMIT)).isEqualTo(436);
    }

    @Test
    void example2() {
        assertThat(findNthNumber(EXAMPLE_INPUT, PART_2_LIMIT)).isEqualTo(175594);
    }

    private List<Integer> parseStartingNumbers(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int findNthNumber(String input, int n) {
        return RecitationGame.findNthSpokenNumber(parseStartingNumbers(input), n);
    }
}
