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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BootCode {

    public static final String ACCUMULATE_INSTRUCTION = "acc";
    public static final String JUMP_INSTRUCTION = "jmp";
    public static final String NOP_INSTRUCTION = "nop";

    private final List<String> instructions;
    private int index;
    private long accumulator;

    public BootCode(List<String> instructions) {
        this.instructions = new ArrayList<>(instructions);
        this.index = 0;
        this.accumulator = 0;
        final Set<Integer> visited = new HashSet<>();
        while (!visited.contains(index) && index < instructions.size()) {
            visited.add(index);
            final String instruction = instructions.get(index);
            final String[] split = instruction.split("\\s+");
            final String operation = split[0];
            final int argument = Integer.parseInt(split[1]);
            switch (operation) {
                case ACCUMULATE_INSTRUCTION -> {
                    accumulator += argument;
                    index++;
                }
                case JUMP_INSTRUCTION -> index += argument;
                case NOP_INSTRUCTION -> index++;
            }
        }
    }

    public Stream<BootCode> mutations() {
        return Stream.generate(new MutationGenerator());
    }

    public boolean completed() {
        return index == instructions.size();
    }

    public long getAccumulator() {
        return accumulator;
    }

    private class MutationGenerator implements Supplier<BootCode> {
        private int mutation = -1;

        @Override
        public BootCode get() {
            incrementMutation();
            final List<String> mutated = new ArrayList<>(instructions);
            final String original = mutated.get(mutation);
            if (original.startsWith(JUMP_INSTRUCTION)) {
                mutated.set(mutation, original.replace(JUMP_INSTRUCTION, NOP_INSTRUCTION));
            } else {
                mutated.set(mutation, original.replace(NOP_INSTRUCTION, JUMP_INSTRUCTION));
            }
            return new BootCode(mutated);
        }

        private void incrementMutation() {
            do {
                mutation++;
            } while (instructions.get(mutation).startsWith(ACCUMULATE_INSTRUCTION));
        }
    }
}
