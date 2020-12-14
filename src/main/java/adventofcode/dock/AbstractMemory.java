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

package adventofcode.dock;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

abstract class AbstractMemory implements Memory {

    protected static final int MASK_SIZE = 36;

    private final Map<Long, Long> memory = new HashMap<>();

    protected static long parseBinary(CharSequence text) {
        return Long.parseUnsignedLong(text.toString(), 2);
    }

    protected void writeToAddress(long address, long value) {
        memory.put(address, value);
    }

    @Override
    public long sumOfMemoryValues() {
        return memory.values().stream().mapToLong(i -> i).sum();
    }

    @Override
    public long readValue(long address) {
        return memory.getOrDefault(address, 0L);
    }

    protected String fillMask(char character) {
        return StringUtils.repeat(character, MASK_SIZE);
    }

    protected String binaryStringOf(long value) {
        return StringUtils.leftPad(Long.toBinaryString(value), MASK_SIZE, '0');
    }
}
