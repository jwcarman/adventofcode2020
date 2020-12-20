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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Grammar {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final String startState;
    private final String vocabulary;

    private final Multimap<String, String> rules = HashMultimap.create();

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public Grammar() {
        this("0", "ab");
    }

    public Grammar(String startState, String vocabulary) {
        this.startState = startState;
        this.vocabulary = vocabulary;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public void addRule(String rule) {
        final int ndx = rule.indexOf(':');
        final String symbol = rule.substring(0, ndx);
        if (rules.containsKey(symbol)) {
            return;
        }
        final String[] alternatives = rule.substring(ndx + 1).split("\\|");

        Arrays.stream(alternatives)
                .map(String::trim)
                .map(alternative -> alternative.replaceAll("\"(\\w+)\"", "$1"))
                .forEach(alternative -> rules.put(symbol, alternative));
    }

    public Matcher createMatcher() {
        return new CykMatcher(startState, toChomskyNormalForm(rules));
    }

    private Multimap<String, String> toChomskyNormalForm(Multimap<String, String> rules) {
        final Multimap<String, String> chomsky = HashMultimap.create(rules);

        eliminateNonSolitaryTerminals(chomsky);
        eliminateMoreThanTwoNonTerminals(chomsky);
        eliminateUnitRules(chomsky);

        return chomsky;
    }

    private void eliminateNonSolitaryTerminals(Multimap<String, String> rules) {
        for (Map.Entry<String, String> rule : new ArrayList<>(rules.entries())) {
            final String symbol = rule.getKey();
            final String rhs = rule.getValue();
            final String[] terms = rhs.split("\\s+");
            if (terms.length > 1) {
                final Set<String> terminals = Arrays.stream(terms)
                        .filter(this::isTerminal)
                        .collect(Collectors.toSet());
                rules.remove(symbol, rhs);
                String value = rhs;
                for (String terminal : terminals) {
                    final String newSymbol = terminal.toUpperCase();
                    value = value.replace(terminal, newSymbol);
                    rules.put(newSymbol, terminal);
                }
                rules.put(symbol, value);
            }
        }
    }

    private void eliminateMoreThanTwoNonTerminals(Multimap<String, String> rules) {
        for (Map.Entry<String, String> rule : new ArrayList<>(rules.entries())) {
            final String symbol = rule.getKey();
            final String rhs = rule.getValue();
            final String[] terms = rhs.split("\\s+");
            final int n = terms.length;
            if (n > 2) {
                rules.remove(symbol, rhs);
                rules.put(symbol, String.format("%s %s-1", terms[0], symbol));
                for (int i = 1; i < n - 2; ++i) {
                    rules.put(String.format("%s-%d", symbol, i), String.format("%s %s-%d", terms[i], symbol, i + 1));
                }
                rules.put(String.format("%s-%d", symbol, n - 2), String.format("%s %s", terms[n - 2], terms[n - 1]));
            }
        }
    }

    private void eliminateUnitRules(Multimap<String, String> rules) {
        for (Map.Entry<String, String> rule : new ArrayList<>(rules.entries())) {
            final String symbol = rule.getKey();
            final String rhs = rule.getValue();
            final String[] terms = rhs.split("\\s+");
            if (terms.length == 1 && !isTerminal(terms[0])) {
                rules.get(terms[0]).forEach(r -> rules.put(symbol, r));
                rules.remove(symbol, rhs);
            }
        }
    }

    private boolean isTerminal(String term) {
        return term.length() == 1 && vocabulary.contains(term);
    }
}
