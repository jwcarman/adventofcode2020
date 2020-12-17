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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day13Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day13.txt");

    private static final String EXAMPLE_INPUT = """
            939
            7,13,x,x,59,x,31,19""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(295);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo(1068781);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private int calculateAnswerPart1(String input) {
        final List<String> lines = readLines(input);
        final int timestamp = Integer.parseInt(lines.get(0));
        final List<Integer> busIds = Arrays.stream(lines.get(1).split(","))
                .filter(s -> !"x".equals(s))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = timestamp; i < Integer.MAX_VALUE; ++i) {
            for (Integer busId : busIds) {
                if (i % busId == 0) {
                    return (i - timestamp) * busId;
                }
            }
        }
        return -1;
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private long calculateAnswerPart2(String input) {
        final List<Indexed<Long>> busIds = readIndexBusIds(input);
        long time = busIds.get(0).getValue();
        long increment = busIds.get(0).getValue();
        for (int i = 1; i < busIds.size(); i++) {
            Indexed<Long> indexed = busIds.get(i);
            while ((time + indexed.getIndex()) % indexed.getValue() != 0) {
                time += increment;
            }
            increment *= indexed.getValue();
        }
        return time;
    }

    private List<Indexed<Long>> readIndexBusIds(String input) {
        final List<String> lines = readLines(input);

        return StreamUtils.zipWithIndex(Arrays.stream(lines.get(1).split(",")))
                .filter(i -> !"x".equals(i.getValue()))
                .map(i -> Indexed.index(i.getIndex(), Long.parseLong(i.getValue())))
                .sorted(Comparator.comparingLong(Indexed::getValue))
                .collect(Collectors.toList());
    }
}
