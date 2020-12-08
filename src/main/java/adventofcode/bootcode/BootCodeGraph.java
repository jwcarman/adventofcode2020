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

package adventofcode.bootcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import static adventofcode.io.Input.readLines;

public class BootCodeGraph {

    public static int accumulateToCycle(String input) {
        final Graph<Integer, BootCodeEdge> graph = buildStandardGraph(input);
        int vertex = 0;
        int accumulator = 0;
        final Set<Integer> visited = new HashSet<>();
        while (!visited.contains(vertex)) {
            visited.add(vertex);
            final BootCodeEdge edge = graph.outgoingEdgesOf(vertex).iterator().next();
            accumulator += edge.getValue();
            vertex = graph.getEdgeTarget(edge);
        }
        return accumulator;
    }

    public static int accumulatePathToTerminus(String input) {
        final Graph<Integer, BootCodeEdge> graph = buildMutationGraph(input);
        final DijkstraShortestPath<Integer, BootCodeEdge> dijkstra = new DijkstraShortestPath<>(graph);
        final int terminus = graph.vertexSet().size() - 1;
        final GraphPath<Integer, BootCodeEdge> path = dijkstra.getPath(0, terminus);
        return path.getEdgeList().stream()
                .mapToInt(BootCodeEdge::getValue)
                .sum();
    }

    private static Graph<Integer, BootCodeEdge> buildStandardGraph(String input) {
        return buildGraph(input, new StandardEdgeGenerator());
    }

    private static Graph<Integer, BootCodeEdge> buildMutationGraph(String input) {
        return buildGraph(input, new MutationEdgeGenerator());
    }

    private static Graph<Integer, BootCodeEdge> buildGraph(String input, EdgeGenerator generator) {
        final Graph<Integer, BootCodeEdge> graph = new DirectedWeightedPseudograph<>(BootCodeEdge.class);
        final List<String> instructions = readLines(input);
        for (int i = 0; i < instructions.size(); i++) {
            final String instruction = instructions.get(i);
            final int ndx = instruction.indexOf(' ');
            final String operation = instruction.substring(0, ndx);
            final int argument = Integer.parseInt(instruction.substring(ndx + 1));
            generator.addEdges(graph, i, operation, argument);
        }
        return graph;
    }

    public static void addEdge(Graph<Integer, BootCodeEdge> graph, String operation, int src, int dest, int value, boolean mutation) {
        graph.addVertex(src);
        graph.addVertex(dest);
        final BootCodeEdge edge = BootCodeEdge.builder()
                .source(src)
                .operation(operation)
                .value(value)
                .mutation(mutation)
                .build();
        graph.addEdge(src, dest, edge);
        graph.setEdgeWeight(edge, mutation ? 1.0 : 0.0);
    }


    interface EdgeGenerator {
        void addEdges(Graph<Integer, BootCodeEdge> graph, int src, String operation, int argument);
    }

    public static class MutationEdgeGenerator implements EdgeGenerator {
        @Override
        public void addEdges(Graph<Integer, BootCodeEdge> graph, int source, String operation, int argument) {
            switch (operation) {
                case "jmp" -> {
                    addEdge(graph, String.format("jmp %d", argument), source, source + argument, 0, false);
                    addEdge(graph, String.format("nop %d", argument), source, source + 1, 0, true);
                }
                case "acc" -> addEdge(graph, operation, source, source + 1, argument, false);
                case "nop" -> {
                    addEdge(graph, String.format("nop %d", argument), source, source + 1, 0, false);
                    addEdge(graph, String.format("jmp %d", argument), source, source + argument, 0, true);
                }
            }
        }
    }

    public static class StandardEdgeGenerator implements EdgeGenerator {

        @Override
        public void addEdges(Graph<Integer, BootCodeEdge> graph, int source, String operation, int argument) {
            switch (operation) {
                case "jmp" -> addEdge(graph, String.format("jmp %d", argument), source, source + argument, 0, false);
                case "acc" -> addEdge(graph, operation, source, source + 1, argument, false);
                case "nop" -> addEdge(graph, String.format("nop %d", argument), source, source + 1, 0, false);
            }
        }
    }
}
