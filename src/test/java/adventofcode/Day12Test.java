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

import adventofcode.navigation.NavigationInstructionInterpreter;
import adventofcode.navigation.ShipInterpreter;
import adventofcode.navigation.WaypointInterpreter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day12Test {
    private static final String INPUT = readResource("Day12.txt");

    private static final String EXAMPLE_INPUT = """
            F10
            N3
            F7
            R90
            F11""";

    @Test
    void part1() {
        log.info("Part One: {}", calculateShipDistance(INPUT));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateWaypointDistance(INPUT));
    }

    @Test
    void example1() {
        assertThat(calculateShipDistance(EXAMPLE_INPUT)).isEqualTo(25);
    }

    @Test
    void example2() {
        assertThat(calculateWaypointDistance(EXAMPLE_INPUT)).isEqualTo(286);
    }

    private int calculateDistance(String input, NavigationInstructionInterpreter interpreter) {
        final List<String> instructions = readLines(input);
        instructions.forEach(interpreter::processInstruction);
        return interpreter.manhattanDistance();
    }

    private int calculateShipDistance(String input) {
        return calculateDistance(input, new ShipInterpreter());
    }

    private int calculateWaypointDistance(String input) {
        return calculateDistance(input, new WaypointInterpreter());
    }
}
