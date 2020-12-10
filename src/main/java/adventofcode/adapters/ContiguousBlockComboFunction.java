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

package adventofcode.adapters;

import java.util.function.Function;

public class ContiguousBlockComboFunction implements Function<Integer, Long> {
    @Override
    public Long apply(Integer blockSize) {
        return switch (blockSize) {
            case 0, 1 -> 1L;
            case 2 -> 2L;
            case 3 -> 4L;
            case 4 -> 7L;
            default -> 13L;
        };
    }
}
