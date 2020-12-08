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

import adventofcode.bootcode.BootCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day8Test {

    private static final String INPUT = readResource("Day8.txt");

    private static final String EXAMPLE_INPUT = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6""";


    @Test
    void part1() {
        final BootCode bootCode = new BootCode(readLines(INPUT));
        log.info("Part One: {}", bootCode.getAccumulator());
    }

    @Test
    void part2() {
        final BootCode bootCode = findSuccessfulMutation(INPUT);
        log.info("Part Two: {}", bootCode.getAccumulator());
    }

    @Test
    void example1() {
        final BootCode bootCode = new BootCode(readLines(EXAMPLE_INPUT));
        assertThat(bootCode.getAccumulator()).isEqualTo(5);
    }

    @Test
    void example2() {
        final BootCode bootCode = findSuccessfulMutation(EXAMPLE_INPUT);

        assertThat(bootCode.getAccumulator()).isEqualTo(8);
    }

    private BootCode findSuccessfulMutation(String input) {
        BootCode bootCode = new BootCode(readLines(input));
        return bootCode.mutations()
                .filter(BootCode::completed)
                .findFirst()
                .orElse(null);
    }
}
