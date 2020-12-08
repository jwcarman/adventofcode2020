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
import java.util.stream.Stream;

import adventofcode.toboggan.PathGenerator;
import adventofcode.toboggan.Position;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day3Test {

    private static final String INPUT = readResource("Day3.txt");

    private static final String EXAMPLE_INPUT = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#""";


    private static long countTrees(String input, int dx, int dy) {
        return countTrees(readLines(input), dx, dy);
    }

    private static long countTrees(List<String> lines, int dx, int dy) {
        final Stream<Position> positions = Stream.generate(new PathGenerator(dx, dy, lines.get(0).length()));

        return positions.takeWhile(position -> position.getY() < lines.size())
                .filter(position -> lines.get(position.getY()).charAt(position.getX()) == '#')
                .count();
    }

    @Test
    void part1() {
        log.info("Part One: {}", countTrees(INPUT, 3, 1));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", multiplySlopesPart2(INPUT));
    }

    @Test
    void example1() {
        assertThat(countTrees(EXAMPLE_INPUT, 3, 1)).isEqualTo(7);
    }

    @Test
    void example2() {
        final long total = multiplySlopesPart2(EXAMPLE_INPUT);
        assertThat(total).isEqualTo(336);
    }

    private long multiplySlopesPart2(String input) {
        final List<String> lines = readLines(input);
        return countTrees(lines, 1, 1) *
                countTrees(lines, 3, 1) *
                countTrees(lines, 5, 1) *
                countTrees(lines, 7, 1) *
                countTrees(lines, 1, 2);
    }
}
