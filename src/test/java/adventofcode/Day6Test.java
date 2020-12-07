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

import java.util.List;

import adventofcode.customs.PassengerGroup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day6Test {

    private static final String INPUT = readResource("Day6.txt");

    private static final String EXAMPLE_INPUT = """
            abc
                        
            a
            b
            c
                        
            ab
            ac
                        
            a
            a
            a
            a
                        
            b""";

    @Test
    void part1() {
        log.info("Part One: {}", uniqueAnswersFromInput(INPUT));
    }

    private long uniqueAnswersFromInput(String input) {
        final List<PassengerGroup> groups = PassengerGroup.parseFromInput(readLines(input));
        return groups.stream()
                .mapToLong(PassengerGroup::calculateUniqueAnswers)
                .sum();
    }

    @Test
    void part2() {
        log.info("Part Two: {}", unanimousAnswersFromInput(INPUT));
    }

    private long unanimousAnswersFromInput(String input) {
        final List<PassengerGroup> groups = PassengerGroup.parseFromInput(readLines(input));
        return groups.stream()
                .mapToLong(PassengerGroup::calculateUnanimousAnswers)
                .sum();
    }

    @Test
    void example1() {
        assertThat(uniqueAnswersFromInput(EXAMPLE_INPUT)).isEqualTo(11);
    }

    @Test
    void example2() {
        assertThat(unanimousAnswersFromInput(EXAMPLE_INPUT)).isEqualTo(6);
    }
}
