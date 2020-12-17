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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuggageTree {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final Pattern CHILD_PATTERN = Pattern.compile("(?<n>\\d+) (?<color>.+)");

    private final Map<String, LuggageNode> nodes = new HashMap<>();

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public void addRule(String rule) {
        final String[] splits = cleanRule(rule).split(",");
        final LuggageNode parent = getNode(splits[0]);
        parent.setOuter(true);
        for (int i = 1; i < splits.length; ++i) {
            final Matcher matcher = CHILD_PATTERN.matcher(splits[i].trim());
            if (matcher.matches()) {
                final int n = Integer.parseInt(matcher.group("n"));
                final String color = matcher.group("color");
                final LuggageNode child = getNode(color);
                parent.addChild(n, child);
            }
        }
    }

    public LuggageNode getNode(final String color) {
        return nodes.computeIfAbsent(color, LuggageNode::new);
    }

    private String cleanRule(String original) {
        return Optional.ofNullable(original)
                .map(rule -> rule.replaceAll(" bags", ""))
                .map(rule -> rule.replaceAll(" bag", ""))
                .map(rule -> rule.replaceAll(" contain", ","))
                .map(rule -> rule.replaceAll("\\.", ""))
                .map(rule -> rule.replaceAll(", no other", ""))
                .orElse("");
    }
}
