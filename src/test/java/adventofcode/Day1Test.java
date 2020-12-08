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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import adventofcode.io.Input;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
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
        log.info("Part One: {}", findEntriesAndMultiply(INPUT, 2));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", findEntriesAndMultiply(INPUT, 3));
    }

    @Test
    void example1() {
        assertThat(findEntriesAndMultiply(EXAMPLE_INPUT, 2)).isEqualTo(514579);
    }

    @Test
    void example2() {
        assertThat(findEntriesAndMultiply(EXAMPLE_INPUT, 3)).isEqualTo(241861950);
    }

    private <T> Stream<List<T>> combinationsOf(List<T> list, int k) {
        final Spliterator<int[]> spliterator = Spliterators.spliteratorUnknownSize(CombinatoricsUtils.combinationsIterator(list.size(), k), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false)
                .map(arr -> Arrays.stream(arr).mapToObj(list::get).collect(Collectors.toList()));
    }

    private long findEntriesAndMultiply(String input, int k) {
        final List<Integer> numbers = new ArrayList<>(Input.readLines(input, Integer::parseInt));
        return combinationsOf(numbers, k)
                .filter(this::sumsUpTo2020)
                .map(this::multiply)
                .findFirst()
                .orElse(-1L);
    }

    private long multiply(List<Integer> combo) {
        return combo.stream().reduce(1, (a, b) -> a * b);
    }

    private boolean sumsUpTo2020(List<Integer> combo) {
        return combo.stream().mapToInt(i -> i).sum() == 2020;
    }
}
