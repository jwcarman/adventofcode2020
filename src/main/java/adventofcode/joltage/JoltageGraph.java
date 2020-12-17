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

package adventofcode.joltage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedAcyclicGraph;

import static adventofcode.io.Input.readLines;

public class JoltageGraph {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Graph<Integer, String> graph;
    private final int maximumJoltage;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public JoltageGraph(String input) {
        final List<Integer> joltages = parseJoltages(input);
        this.graph = buildGraph(joltages);
        this.maximumJoltage = joltages.get(joltages.size() - 1);
    }

    public static List<Integer> parseJoltages(String input) {
        List<Integer> joltages = readLines(input, Integer::parseInt).stream().sorted().collect(Collectors.toList());
        joltages.add(0, 0);
        joltages.add(joltages.get(joltages.size() - 1) + 3);
        return joltages;
    }

    private Graph<Integer, String> buildGraph(List<Integer> joltages) {
        final Graph<Integer, String> graph = new DirectedAcyclicGraph<>(String.class);
        final int n = joltages.size();
        for (int i = 0; i < n - 1; ++i) {
            final int curr = joltages.get(i);
            for (int j = i + 1; j < n && joltages.get(j) - curr < 4; ++j) {
                final int next = joltages.get(j);
                graph.addVertex(curr);
                graph.addVertex(next);
                final String edge = String.format("%d -> %d", curr, next);
                graph.addEdge(curr, next, edge);
            }
        }
        return graph;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public Map<Integer, Long> calculatePathCounts() {
        final Map<Integer, Long> pathCounts = new HashMap<>();
        final Queue<Integer> joltageQueue = new LinkedList<>();
        final Set<Integer> queued = new HashSet<>();
        enqueue(joltageQueue, queued, List.of(maximumJoltage));
        while (!joltageQueue.isEmpty()) {
            final int joltage = joltageQueue.remove();
            if (joltage == maximumJoltage) {
                addLeafPathCount(pathCounts, joltage);
            } else {
                addNonLeafPathCount(pathCounts, joltage);
            }
            enqueue(joltageQueue, queued, unqueuedParentsOf(joltage, queued));
        }
        return pathCounts;
    }

    private void addLeafPathCount(Map<Integer, Long> pathCounts, int joltage) {
        pathCounts.put(joltage, 1L);
    }

    private void addNonLeafPathCount(Map<Integer, Long> pathCounts, int joltage) {
        pathCounts.put(joltage, graph.outgoingEdgesOf(joltage).stream()
                .map(graph::getEdgeTarget)
                .mapToLong(pathCounts::get)
                .sum());
    }

    private void enqueue(Queue<Integer> joltageQueue, Set<Integer> queued, List<Integer> parents) {
        joltageQueue.addAll(parents);
        queued.addAll(parents);
    }

    private List<Integer> unqueuedParentsOf(int joltage, Set<Integer> queued) {
        return graph.incomingEdgesOf(joltage).stream()
                .map(graph::getEdgeSource)
                .filter(j -> !queued.contains(j))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
