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

public class BootCodeInterpreter {

    static final String ACCUMULATE_INSTRUCTION = "acc";
    static final String JUMP_INSTRUCTION = "jmp";
    static final String NOP_INSTRUCTION = "nop";

    public static BootCodeResult interpret(List<String> instructions) {
        int index = 0;
        int accumulator = 0;
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
        return BootCodeResult.builder()
                .accumulator(accumulator)
                .successful(index == instructions.size())
                .build();
    }
}
