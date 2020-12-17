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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import adventofcode.conway.ConwayCube;
import adventofcode.conway.ConwayCube3D;
import adventofcode.conway.ConwayCube4D;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day17Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final int CYCLE_COUNT = 6;
    private static final String INPUT = readResource("Day17.txt");

    private static final String EXAMPLE_INPUT = """
            .#.
            ..#
            ###""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(112);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo(848);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private int calculateAnswerPart1(String input) {
        return performCycles(parseActiveCubes(input, (x, y) -> new ConwayCube3D(x, y, 0)), CYCLE_COUNT);
    }

    private int performCycles(Set<ConwayCube> activeCubes, int n) {
        for (int cycle = 0; cycle < n; ++cycle) {
            activeCubes = performCycle(activeCubes);
        }
        return activeCubes.size();
    }

    private Set<ConwayCube> performCycle(final Set<ConwayCube> activeCubes) {
        final Set<ConwayCube> nextState = new HashSet<>(activeCubes);
        final Set<ConwayCube> allCubes = activeCubes.stream()
                .flatMap(cube -> cube.neighborhood().stream())
                .collect(Collectors.toSet());
        for (ConwayCube cube : allCubes) {
            final long activeNeighborCount = cube.neighborhood().stream()
                    .filter(activeCubes::contains)
                    .count();
            if (activeCubes.contains(cube)) {
                if (activeNeighborCount < 3 || activeNeighborCount > 4) {
                    nextState.remove(cube);
                }
            } else {
                if (activeNeighborCount == 3) {
                    nextState.add(cube);
                }
            }
        }
        return nextState;
    }

    private Set<ConwayCube> parseActiveCubes(String input, BiFunction<Integer, Integer, ConwayCube> cubeFactory) {
        final List<String> lines = readLines(input);
        Set<ConwayCube> activeCubes = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            final char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                if (chars[x] == '#') {
                    activeCubes.add(cubeFactory.apply(x, y));
                }
            }
        }
        return activeCubes;
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private int calculateAnswerPart2(String input) {
        return performCycles(parseActiveCubes(input, (x, y) -> new ConwayCube4D(x, y, 0, 0)), CYCLE_COUNT);
    }
}
