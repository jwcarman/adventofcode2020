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

import adventofcode.io.Input;
import adventofcode.luggage.LuggageNode;
import adventofcode.luggage.LuggageTree;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day7Test {

    private static final String INPUT = readResource("Day7.txt");

    private static final String EXAMPLE_INPUT = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.""";

    @Test
    void part1() {
        final LuggageTree tree = parseTree(INPUT);

        final LuggageNode node = tree.getNode("shiny gold");
        log.info("Part One: {}", node.countOuterParents());
    }

    @Test
    void part2() {
        final LuggageTree tree = parseTree(INPUT);

        final LuggageNode node = tree.getNode("shiny gold");
        log.info("Part Two: {}", node.countDescendants());
    }

    @Test
    void example1() {
        final LuggageTree tree = parseTree(EXAMPLE_INPUT);

        final LuggageNode node = tree.getNode("shiny gold");
        assertThat(node.countOuterParents()).isEqualTo(4);
    }

    @Test
    void example2() {
        final LuggageTree tree = parseTree(EXAMPLE_INPUT);

        final LuggageNode node = tree.getNode("shiny gold");
        assertThat(node.countDescendants()).isEqualTo(32);
    }

    private LuggageTree parseTree(String input) {
        final LuggageTree tree = new LuggageTree();
        Input.readLines(input).forEach(tree::addRule);
        return tree;
    }
}
