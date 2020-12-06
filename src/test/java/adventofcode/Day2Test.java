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

import java.io.StringReader;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import adventofcode.io.Input;
import adventofcode.password.PasswordPolicyLine;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test {

    private static final String INPUT = readResource("Day2.txt");

    private static final String EXAMPLE_INPUT = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc""";

    @Test
    void part1() {
        final List<String> lines = Input.readLines(new StringReader(INPUT));
        final long count = countLines(lines, PasswordPolicyLine::isValidPart1);

        System.out.println(count);
    }

    @Test
    void part2() {
        final List<String> lines = Input.readLines(new StringReader(INPUT));
        final long count = countLines(lines, PasswordPolicyLine::isValidPart2);

        System.out.println(count);
    }

    @Test
    void example1() {
        final List<String> lines = Input.readLines(new StringReader(EXAMPLE_INPUT));
        final long count = countLines(lines, PasswordPolicyLine::isValidPart1);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void example2() {
        final List<String> lines = Input.readLines(new StringReader(EXAMPLE_INPUT));
        final long count = countLines(lines, PasswordPolicyLine::isValidPart2);
        assertThat(count).isEqualTo(1);
    }

    private long countLines(List<String> lines, Predicate<PasswordPolicyLine> predicate) {
        return lines.stream()
                .map(this::toPolicyLine)
                .filter(predicate)
                .count();
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
