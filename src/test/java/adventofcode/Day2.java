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

import adventofcode.io.Input;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;

public class Day2 {

    private static final String INPUT = readResource("Day2.txt");

    @Test
    void part1() {
        final List<String> lines = Input.readLines(new StringReader(INPUT));

        final long count = lines.stream()
                .filter(line -> {
                    Scanner scanner = new Scanner(line);
                    scanner.useDelimiter("-|:\\s|\\s+");
                    final int min = scanner.nextInt();
                    final int max = scanner.nextInt();
                    final char letter = scanner.next().charAt(0);
                    final String password = scanner.next();
                    final int matches = StringUtils.countMatches(password, letter);
                    return matches >= min && matches <= max;
                }).count();
        System.out.println(count);
    }

    @Test
    void part2() {
        final List<String> lines = Input.readLines(new StringReader(INPUT));

        final long count = lines.stream()
                .filter(line -> {
                    Scanner scanner = new Scanner(line);
                    scanner.useDelimiter("-|:\\s|\\s+");
                    final int index1 = scanner.nextInt() - 1;
                    final int index2 = scanner.nextInt() - 1;
                    final char letter = scanner.next().charAt(0);
                    final String password = scanner.next();
                    return password.charAt(index1) == letter ^ password.charAt(index2) == letter;
                }).count();
        System.out.println(count);
    }
}
