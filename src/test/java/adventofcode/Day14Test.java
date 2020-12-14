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

import adventofcode.dock.AddressMaskingMemory;
import adventofcode.dock.Memory;
import adventofcode.dock.ValueMaskingMemory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day14Test {

    private static final String INPUT = readResource("Day14.txt");

    private static final String EXAMPLE_INPUT1 = """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0""";

    private static final String EXAMPLE_INPUT2 = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1""";


    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT1)).isEqualTo(165);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT2)).isEqualTo(208);
    }

    private long calculateAnswerPart1(String input) {
        return calculateAnswer(input, new ValueMaskingMemory());
    }

    private long calculateAnswerPart2(String input) {
        return calculateAnswer(input, new AddressMaskingMemory());
    }

    private long calculateAnswer(String input, Memory memory) {
        final List<String> lines = readLines(input);
        for (String line : lines) {
            if (line.startsWith("mem")) {
                final int ndx = line.indexOf(']');
                final long address = Long.parseUnsignedLong(line.substring(4, ndx));
                final long value = Long.parseUnsignedLong(line.substring(line.indexOf('=') + 2));
                memory.writeValue(address, value);
            } else {
                memory.setMask(line.substring(7));
            }
        }
        return memory.sumOfMemoryValues();
    }
}
