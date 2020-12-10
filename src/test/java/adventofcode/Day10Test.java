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
import java.util.stream.Collectors;

import adventofcode.adapters.ContiguousBlockComboFunction;
import adventofcode.adapters.ContiguousBlockSizeFunction;
import adventofcode.adapters.JoltageDifferenceFunction;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day10Test {

    private static final String INPUT = readResource("Day10.txt");

    private static final String EXAMPLE_INPUT = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3""";

    @Test
    void part1() {
        log.info("Part One: {}", calculatePart1Answer(INPUT));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculatePart2Answer(INPUT));
    }

    @Test
    void example1() {
        assertThat(calculatePart1Answer(EXAMPLE_INPUT)).isEqualTo(220);
    }


    @Test
    void example2() {
        assertThat(calculatePart2Answer(EXAMPLE_INPUT)).isEqualTo(19208);
    }

    private int calculatePart1Answer(String input) {
        final Multiset<Integer> differenceCounts = HashMultiset.create(calculateDifferences(input));
        return differenceCounts.count(1) * differenceCounts.count(3);
    }

    private Long calculatePart2Answer(String input) {
        return calculateDifferences(input)
                .stream()
                .map(new ContiguousBlockSizeFunction())
                .map(new ContiguousBlockComboFunction())
                .reduce(1L, (a, b) -> a * b);
    }

    private List<Integer> calculateDifferences(String input) {
        final List<Integer> diffs = readLines(input, Integer::parseInt)
                .stream()
                .sorted()
                .map(new JoltageDifferenceFunction())
                .collect(Collectors.toList());
        diffs.add(3);
        return diffs;
    }

}
