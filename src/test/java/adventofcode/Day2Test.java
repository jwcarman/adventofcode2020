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
import java.util.Scanner;
import java.util.function.Predicate;

import adventofcode.password.PasswordPolicyLine;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day2Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day2.txt");

    private static final String EXAMPLE_INPUT = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        final long count = countLines(EXAMPLE_INPUT, PasswordPolicyLine::isValidPart1);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void example2() {
        final long count = countLines(EXAMPLE_INPUT, PasswordPolicyLine::isValidPart2);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void part1() {
        final long count = countLines(INPUT, PasswordPolicyLine::isValidPart1);
        log.info("Part One: {}", count);
    }

    private long countLines(String input, Predicate<PasswordPolicyLine> predicate) {
        final List<String> lines = readLines(input);
        return lines.stream()
                .map(this::toPolicyLine)
                .filter(predicate)
                .count();
    }

    @Test
    void part2() {
        final long count = countLines(INPUT, PasswordPolicyLine::isValidPart2);
        log.info("Part Two: {}", count);
    }

    private PasswordPolicyLine toPolicyLine(String line) {
        final Scanner scanner = new Scanner(line);
        scanner.useDelimiter("-|:\\s|\\s+");
        return PasswordPolicyLine.builder()
                .first(scanner.nextInt())
                .second(scanner.nextInt())
                .letter(scanner.next().charAt(0))
                .password(scanner.next())
                .build();
    }
}
