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

import adventofcode.grammar.Grammar;
import adventofcode.grammar.Matcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day19Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day19.txt");
    private static final String EXAMPLE_INPUT = """
            0: 4 1 5
            1: 2 3 | 3 2
            2: 4 4 | 5 5
            3: 4 5 | 5 4
            4: "a"
            5: "b"
                        
            ababbb
            bababa
            abbbab
            aaabbb
            aaaabbb""";

    private static final String EXAMPLE_INPUT2 = """
            42: 9 14 | 10 1
            9: 14 27 | 1 26
            10: 23 14 | 28 1
            1: "a"
            11: 42 31
            5: 1 14 | 15 1
            19: 14 1 | 14 14
            12: 24 14 | 19 1
            16: 15 1 | 14 14
            31: 14 17 | 1 13
            6: 14 14 | 1 14
            2: 1 24 | 14 4
            0: 8 11
            13: 14 3 | 1 12
            15: 1 | 14
            17: 14 2 | 1 7
            23: 25 1 | 22 14
            28: 16 1
            4: 1 1
            20: 14 14 | 1 15
            3: 5 14 | 16 1
            27: 1 6 | 14 18
            14: "b"
            21: 14 1 | 1 14
            25: 1 1 | 1 14
            22: 14 14
            8: 42
            26: 14 22 | 1 20
            18: 15 15
            7: 14 5 | 1 21
            24: 14 1
                        
            abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
            bbabbbbaabaabba
            babbbbaabbbbbabbbbbbaabaaabaaa
            aaabbbbbbaaaabaababaabababbabaaabbababababaaa
            bbbbbbbaaaabbbbaaabbabaaa
            bbbababbbbaaaaaaaabbababaaababaabab
            ababaaaaaabaaab
            ababaaaaabbbaba
            baabbaaaabbaaaababbaababb
            abbbbabbbbaaaababbbbbbaaaababb
            aaaaabbaabaaaaababaa
            aaaabbaaaabbaaa
            aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
            babaaabbbaaabaababbaabababaaab
            aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(2);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT2)).isEqualTo(12);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private long calculateAnswerPart1(String input) {
        return calculateAnswer(input);
    }

    private long calculateAnswer(String input, String... overrideRules) {
        final String[] splits = input.split("\n\n");

        final String rulesInput = splits[0];
        final String messagesInput = splits[1];
        final Grammar grammar = new Grammar();
        for (String overrideRule : overrideRules) {
            grammar.addRule(overrideRule);
        }
        readLines(rulesInput).forEach(grammar::addRule);
        final Matcher matcher = grammar.createMatcher();
        return readLines(messagesInput).stream()
                .filter(matcher::matches)
                .count();
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private long calculateAnswerPart2(String input) {
        return calculateAnswer(input, "8: 42 | 42 8", "11: 42 31 | 42 11 31");
    }
}
