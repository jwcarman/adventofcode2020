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

import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.hextile.HexPath;
import adventofcode.hextile.HexPoint;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day24Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day24.txt");

    private static final String EXAMPLE_INPUT = """
            sesenwnenenewseeswwswswwnenewsewsw
            neeenesenwnwwswnenewnwwsewnenwseswesw
            seswneswswsenwwnwse
            nwnwneseeswswnenewneswwnewseswneseene
            swweswneswnenwsewnwneneseenw
            eesenwseswswnenwswnwnwsewwnwsene
            sewnenenenesenwsewnenwwwse
            wenwwweseeeweswwwnwwe
            wsweesenenewnwwnwsenewsenwwsesesenwne
            neeswseenwwswnwswswnw
            nenwswwsewswnenenewsenwsenwnesesenew
            enewnwewneswsewnwswenweswnenwsenwsw
            sweneswneswneneenwnewenewwneswswnese
            swwesenesewenwneswnwwneseswwne
            enesenwswwswneneswsenwnewswseenwsese
            wnwnesenesenenwwnenwsewesewsesesew
            nenewswnwewswnenesenwnesewesw
            eneswnwswnwsenenwnwnwwseeswneewsenese
            neswnwewnwnwseenwseesewsenwsweewe
            wseweeenwnesenwwwswnew""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(10);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo(2208);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private int calculateAnswerPart1(String input) {
        return findInitialBlackTiles(input).size();
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private int calculateAnswerPart2(String input) {
        Set<HexPoint> blackTiles = findInitialBlackTiles(input);
        for (int i = 0; i < 100; ++i) {
            blackTiles = nextBlackTiles(blackTiles);
        }
        return blackTiles.size();
    }

    private Set<HexPoint> findInitialBlackTiles(String input) {
        final HashMultiset<HexPoint> results = readLines(input).stream()
                .map(HexPath::new)
                .map(path -> path.from(HexPoint.ORIGIN))
                .collect(Collectors.toCollection(HashMultiset::create));

        return results.entrySet().stream()
                .filter(entry -> entry.getCount() % 2 == 1)
                .map(Multiset.Entry::getElement)
                .collect(Collectors.toSet());
    }

    private Set<HexPoint> nextBlackTiles(final Set<HexPoint> blackTiles) {
        return blackTiles.stream()
                .flatMap(tile -> tile.neighborhood().stream())
                .filter(tile -> shouldBeBlack(blackTiles, tile))
                .collect(Collectors.toSet());
    }

    private boolean shouldBeBlack(Set<HexPoint> blackTiles, HexPoint tile) {
        final int blackTileCount = countBlackTilesAround(blackTiles, tile);
        if (blackTiles.contains(tile)) {
            return blackTileCount == 2 || blackTileCount == 3;
        } else {
            return blackTileCount == 2;
        }
    }

    private int countBlackTilesAround(Set<HexPoint> blackTiles, HexPoint tile) {
        int numberOfBlackNeighbors = 0;
        for (HexPoint neighbor : tile.neighborhood()) {
            if (blackTiles.contains(neighbor)) {
                numberOfBlackNeighbors++;
            }
        }
        return numberOfBlackNeighbors;
    }
}
