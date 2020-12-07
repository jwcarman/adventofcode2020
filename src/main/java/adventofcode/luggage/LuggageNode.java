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

package adventofcode.luggage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@EqualsAndHashCode(of = "color")
public class LuggageNode {
    private final String color;
    private final Set<LuggageNode> parents = new HashSet<>();
    private final List<LuggageNode> children = new LinkedList<>();
    private boolean outer = false;

    public void addChild(int n, LuggageNode child) {
        child.getParents().add(this);
        IntStream.range(0, n).forEach(ndx -> children.add(child));
    }

    public int countOuterParents() {
        final HashSet<LuggageNode> parents = new HashSet<>();
        collectOuterParents(this, parents);
        return parents.size();
    }

    public int countDescendants() {
        return children.size() + children.stream().mapToInt(LuggageNode::countDescendants).sum();
    }

    private void collectOuterParents(LuggageNode node, Set<LuggageNode> parents) {
        node.getParents().stream()
                .peek(c -> collectOuterParents(c, parents))
                .filter(LuggageNode::isOuter)
                .forEach(parents::add);
    }
}
