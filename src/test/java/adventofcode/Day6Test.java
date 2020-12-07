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

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;

@Slf4j
public class Day6Test {

    private static final String INPUT = readResource("Day6.txt");

    private static final String EXAMPLE = """
            abc
                        
            a
            b
            c
                        
            ab
            ac
                        
            a
            a
            a
            a
                        
            b""";

    @Test
    void part1() {
        final Set<Integer> answers = new HashSet<>();
        int count = 0;
        final List<String> lines = readLines(INPUT);
        lines.add("");
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                count += answers.size();
                answers.clear();
            } else {
                line.chars().forEach(answers::add);
            }
        }
        log.info("Part One: {}", count);
    }

    private long countUnanimous(Multiset<Integer> answers,final int groupSize) {
        return answers.elementSet().stream()
                .filter(i -> answers.count(i) == groupSize)
                .count();
    }

    @Test
    void part2() {
        Multiset<Integer> answers = HashMultiset.create(26);
        int groupSize = 0;
        final List<String> lines = readLines(INPUT);
        lines.add("");
        int count = 0;
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                count += countUnanimous(answers, groupSize);
                answers.clear();
                groupSize = 0;
            } else {
                line.chars().forEach(answers::add);
                groupSize++;
            }
        }
        log.info("Part Two: {}", count);
    }
}
