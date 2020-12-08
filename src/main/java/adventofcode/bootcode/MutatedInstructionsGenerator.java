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
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static adventofcode.bootcode.BootCodeInterpreter.ACCUMULATE_INSTRUCTION;
import static adventofcode.bootcode.BootCodeInterpreter.JUMP_INSTRUCTION;
import static adventofcode.bootcode.BootCodeInterpreter.NOP_INSTRUCTION;

public class MutatedInstructionsGenerator implements Supplier<List<String>> {

    private final List<String> instructions;
    private int mutationIndex = -1;

    public MutatedInstructionsGenerator(List<String> original) {
        this.instructions = new ArrayList<>(original);
    }

    @Override
    public List<String> get() {
        if (mutationIndex > -1) {
            flip(mutationIndex);
        }
        this.mutationIndex = findNextMutation(mutationIndex);
        flip(mutationIndex);
        return Collections.unmodifiableList(instructions);
    }

    private void flip(int index) {
        final String instruction = instructions.get(mutationIndex);
        instructions.set(index, mutateInstruction(instruction));
    }

    private String mutateInstruction(String instruction) {
        if (instruction.startsWith(JUMP_INSTRUCTION)) {
            return instruction.replace(JUMP_INSTRUCTION, NOP_INSTRUCTION);
        } else {
            return instruction.replace(NOP_INSTRUCTION, JUMP_INSTRUCTION);
        }
    }

    private int findNextMutation(final int last) {
        int next = last;
        do {
            next++;
        } while (instructions.get(next).startsWith(ACCUMULATE_INSTRUCTION));
        return next;
    }
}
