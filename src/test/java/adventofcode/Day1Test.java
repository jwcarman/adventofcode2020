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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static adventofcode.pointers.Pointers.threePointers;
import static adventofcode.pointers.Pointers.twoPointers;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class Day1Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day1.txt");

    private static final String EXAMPLE_INPUT = """
            1721
            979
            366
            299
            675
            1456""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        final List<Integer> unsorted = readLines(EXAMPLE_INPUT, Integer::parseInt);
        assertThat(twoPointersProduct(unsorted)).isEqualTo(514579);
    }

    @Test
    void example2() {
        final List<Integer> unsorted = readLines(EXAMPLE_INPUT, Integer::parseInt);
        assertThat(threePointersProduct(unsorted)).isEqualTo(241861950);
    }

    @Test
    void part1() {
        log.info("Part One: {}", twoPointersProduct(readLines(INPUT, Integer::parseInt)));
    }

    private Integer twoPointersProduct(List<Integer> unsorted) {
        final List<Integer> sorted = sort(unsorted);
        return twoPointers(sorted, 2020)
                .map(pair -> sorted.get(pair.getLeft()) * sorted.get(pair.getRight()))
                .orElse(-1);
    }

    @Test
    void part2() {
        log.info("Part Two: {}", threePointersProduct(readLines(INPUT, Integer::parseInt)));
    }

    private Integer threePointersProduct(List<Integer> unsorted) {
        final List<Integer> sorted = sort(unsorted);
        return threePointers(sorted, 2020)
                .map(triple -> sorted.get(triple.getLeft()) * sorted.get(triple.getMiddle()) * sorted.get(triple.getRight()))
                .orElse(-1);
    }

    private List<Integer> sort(List<Integer> unsorted) {
        return unsorted.stream().sorted().collect(Collectors.toList());
    }
}
