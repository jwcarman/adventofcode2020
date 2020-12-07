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

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import adventofcode.io.Input;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static com.google.common.collect.Sets.combinations;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class Day1Test {

    private static final String INPUT = readResource("Day1.txt");

    private static final String EXAMPLE_INPUT = """
            1721
            979
            366
            299
            675
            1456""";

    @Test
    void part1() {
        final Set<Integer> numbers = new HashSet<>(Input.readLines(new StringReader(INPUT), Integer::parseInt));
        final int sum = findSum2020AndMultiply(combinations(numbers, 2));
        log.info("Part One: {}", sum);
    }

    @Test
    void part2() {
        final Set<Integer> numbers = new HashSet<>(Input.readLines(new StringReader(INPUT), Integer::parseInt));
        final int sum = findSum2020AndMultiply(combinations(numbers, 3));
        log.info("Part Two: {}", sum);
    }

    @Test
    void example1() {
        final Set<Integer> numbers = new HashSet<>(Input.readLines(new StringReader(EXAMPLE_INPUT), Integer::parseInt));
        final int sum = findSum2020AndMultiply(combinations(numbers, 2));
        assertThat(sum).isEqualTo(514579);
    }

    @Test
    void example2() {
        final Set<Integer> numbers = new HashSet<>(Input.readLines(new StringReader(EXAMPLE_INPUT), Integer::parseInt));
        final int sum = findSum2020AndMultiply(combinations(numbers, 3));
        assertThat(sum).isEqualTo(241861950);
    }

    private int findSum2020AndMultiply(Set<Set<Integer>> combinations) {
        return combinations.stream()
                .filter(sumTo2020())
                .findFirst()
                .map(multiply())
                .orElse(-1);
    }

    private Function<Set<Integer>, Integer> multiply() {
        return c -> c.stream().reduce(1, (a, b) -> a * b);
    }

    private Predicate<Set<Integer>> sumTo2020() {
        return c -> c.stream().mapToInt(i -> i).sum() == 2020;
    }
}
