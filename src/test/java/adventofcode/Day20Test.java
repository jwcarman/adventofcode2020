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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import adventofcode.monster.ImageTile;
import adventofcode.monster.MonsterScanner;
import adventofcode.monster.Neighbors;
import adventofcode.monster.SquareTessellation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day20Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final char HIGHLIGHT_CHAR = 'O';
    private static final String INPUT = readResource("Day20.txt");

    private static final String EXAMPLE_INPUT = """
            Tile 2311:
            ..##.#..#.
            ##..#.....
            #...##..#.
            ####.#...#
            ##.##.###.
            ##...#.###
            .#.#.#..##
            ..#....#..
            ###...#.#.
            ..###..###
                        
            Tile 1951:
            #.##...##.
            #.####...#
            .....#..##
            #...######
            .##.#....#
            .###.#####
            ###.##.##.
            .###....#.
            ..#.#..#.#
            #...##.#..
                        
            Tile 1171:
            ####...##.
            #..##.#..#
            ##.#..#.#.
            .###.####.
            ..###.####
            .##....##.
            .#...####.
            #.##.####.
            ####..#...
            .....##...
                        
            Tile 1427:
            ###.##.#..
            .#..#.##..
            .#.##.#..#
            #.#.#.##.#
            ....#...##
            ...##..##.
            ...#.#####
            .#.####.#.
            ..#..###.#
            ..##.#..#.
                        
            Tile 1489:
            ##.#.#....
            ..##...#..
            .##..##...
            ..#...#...
            #####...#.
            #..#.#.#.#
            ...#.#.#..
            ##.#...##.
            ..##.##.##
            ###.##.#..
                        
            Tile 2473:
            #....####.
            #..#.##...
            #.##..#...
            ######.#.#
            .#...#.#.#
            .#########
            .###.#..#.
            ########.#
            ##...##.#.
            ..###.#.#.
                        
            Tile 2971:
            ..#.#....#
            #...###...
            #.#.###...
            ##.##..#..
            .#####..##
            .#..####.#
            #..#.#..#.
            ..####.###
            ..#.#.###.
            ...#.#.#.#
                        
            Tile 2729:
            ...#.#.#.#
            ####.#....
            ..#.#.....
            ....#..#.#
            .##..##.#.
            .#.####...
            ####.#.#..
            ##.####...
            ##..#.##..
            #.##...##.
                        
            Tile 3079:
            #.#.#####.
            .#..######
            ..#.......
            ######....
            ####.#..#.
            .#...#.##.
            #.#####.##
            ..#.###...
            ..#.......
            ..#.###...""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(20899048083289L);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo(273);
    }

    private Optional<SquareTessellation> findTessellation(String input) {
        final Map<Long, ImageTile> tiles = parseImageTiles(input);
        final Neighbors neighbors = toNeighbors(tiles);

        final int n = (int) Math.sqrt(tiles.size());

        return neighbors.all().stream()
                .map(start -> SquareTessellation.createSquareTessellation(n, neighbors, start))
                .filter(Objects::nonNull)
                .findFirst();
    }

    private Map<Long, ImageTile> parseImageTiles(String input) {
        return Arrays.stream(input.split("\n\n"))
                .map(ImageTile::parse)
                .collect(Collectors.toMap(ImageTile::getId, tile -> tile));
    }

    private Neighbors toNeighbors(Map<Long, ImageTile> imageTiles) {
        final Neighbors neighbors = new Neighbors();
        imageTiles.values().stream()
                .flatMap(tile -> tile.variants().stream())
                .forEach(neighbors::add);
        return neighbors;
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private long calculateAnswerPart1(String input) {
        return findTessellation(input)
                .map(SquareTessellation::checksum)
                .orElseThrow();
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private int calculateAnswerPart2(String input) {
        final SquareTessellation tessellation = findTessellation(input).orElseThrow();
        final ImageTile composite = tessellation.createCompositeImageTile();
        final String match = composite.variants().stream()
                .map(MonsterScanner::highlightMonsters)
                .filter(s -> s.indexOf(MonsterScanner.DEFAULT_HIGHLIGHT_CHARACTER) != -1)
                .findFirst()
                .orElse(null);

        return StringUtils.countMatches(match, '#');
    }
}
