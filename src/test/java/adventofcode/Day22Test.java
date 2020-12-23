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

import adventofcode.combat.CombatGame;
import adventofcode.combat.CombatPlayer;
import adventofcode.combat.RecursiveCombatGame;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day22Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day22.txt");
    private static final String EXAMPLE_INPUT = """
            Player 1:
            9
            2
            6
            3
            1
                        
            Player 2:
            5
            8
            4
            7
            10""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(306L);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo(291L);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private long calculateAnswerPart1(String input) {
        final String[] splits = input.split("\n\n");
        return CombatGame.builder()
                .player1(new CombatPlayer(splits[0]))
                .player2(new CombatPlayer(splits[1]))
                .build()
                .play()
                .winner()
                .score();
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private long calculateAnswerPart2(String input) {
        final String[] splits = input.split("\n\n");
        return RecursiveCombatGame.builder()
                .player1(new CombatPlayer(splits[0]))
                .player2(new CombatPlayer(splits[1]))
                .build()
                .play()
                .winner().score();
    }
}
