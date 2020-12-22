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

package adventofcode.grammar;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

import static com.google.common.collect.Sets.cartesianProduct;

class CykMatcher implements Matcher {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final String startState;
    private final SetMultimap<String, String> table = HashMultimap.create();

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public CykMatcher(String startState, Multimap<String, String> rules) {
        this.startState = startState;
        for (Map.Entry<String, String> entry : rules.entries()) {
            table.put(entry.getValue(), entry.getKey());
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Matcher Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean matches(String w) {
        final int n = w.length();
        for (int length = 2; length <= n; ++length) {
            for (int start = 0; start + length <= n; ++start) {
                final String substring = w.substring(start, start + length);
                if (!table.containsKey(substring)) {
                    processSubstring(substring);
                }
            }
        }
        return table.get(w).contains(startState);
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private void processSubstring(String substring) {
        for (int i = 1; i < substring.length(); ++i) {
            final String left = substring.substring(0, i);
            final Set<String> leftSymbols = table.get(left);
            final String right = substring.substring(i);
            final Set<String> rightSymbols = table.get(right);
            cartesianProduct(leftSymbols, rightSymbols).stream()
                    .map(s -> String.join(" ", s))
                    .flatMap(rhs -> table.get(rhs).stream())
                    .forEach(symbol -> table.put(substring, symbol));
        }
    }
}
