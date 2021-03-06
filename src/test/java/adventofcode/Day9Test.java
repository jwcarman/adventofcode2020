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
import java.util.function.Predicate;

import adventofcode.encoding.NumberBuffer;
import adventofcode.encoding.Stats;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day9Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final int PREAMBLE_SIZE = 25;
    public static final int EXAMPLE_PREAMBLE_SIZE = 5;
    private static final String INPUT = readResource("Day9.txt");
    private static final String EXAMPLE_INPUT = """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576""";

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    private static Long findFirstInvalid(List<Long> numbers, int bufferSize) {
        final NumberBuffer buffer = new NumberBuffer(numbers.subList(0, bufferSize));

        return numbers.subList(bufferSize, numbers.size()).stream()
                .filter(Predicate.not(buffer::isValid))
                .findFirst()
                .orElse(-1L);
    }

    /**
     * Inspiration for this algorithm came
     * from <a href="https://www.geeksforgeeks.org/find-subarray-with-given-sum/">GeeksforGeeks</a>
     *
     * @param numbers   the list of numbers
     * @param targetSum the sum we're looking for (from first problem)
     * @return the sum of the max/min of the sublist that sums to the target sum
     */
    private static long findEncryptionWeakness(List<Long> numbers, long targetSum) {
        final int n = numbers.size();
        int start = 0;
        final Stats stats = new Stats();
        stats.add(numbers.get(0));
        for (int end = 1; end < n; ++end) {
            stats.add(numbers.get(end));
            while (stats.getSum() > targetSum && start < end - 1) {
                stats.remove(numbers.get(start));
                start++;
            }
            if (stats.getSum() == targetSum) {
                return stats.getMax() + stats.getMin();
            }
        }
        return -1L;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private static long findFirstInvalid(String input, int bufferSize) {
        final List<Long> numbers = readLines(input, Long::parseLong);
        return findFirstInvalid(numbers, bufferSize);
    }

    @Test
    void example1() {
        assertThat(findFirstInvalid(EXAMPLE_INPUT, EXAMPLE_PREAMBLE_SIZE)).isEqualTo(127);
    }

    @Test
    void part1() {
        log.info("Part One: {}", findFirstInvalid(INPUT, PREAMBLE_SIZE));
    }

    @Test
    void example2() {
        assertThat(findEncryptionWeakness(EXAMPLE_INPUT, EXAMPLE_PREAMBLE_SIZE)).isEqualTo(62);
    }

    @Test
    void part2() {
        log.info("Part Two: {}", findEncryptionWeakness(INPUT, PREAMBLE_SIZE));
    }

    private long findEncryptionWeakness(String input, int bufferSize) {
        final List<Long> numbers = readLines(input, Long::parseLong);

        final long invalidNumber = findFirstInvalid(input, bufferSize);
        return findEncryptionWeakness(numbers, invalidNumber);
    }
}
