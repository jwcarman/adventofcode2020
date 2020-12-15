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

public class ValueMaskingMemoryController extends BaseMemoryController {

    private String mask = fillMask('X');

    @Override
    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public void writeValue(long address, long value) {
        final StringBuilder binary = new StringBuilder(binaryStringOf(value));
        for (int i = 0; i < 36; ++i) {
            final char maskChar = mask.charAt(i);
            if (maskChar != 'X') {
                binary.setCharAt(i, maskChar);
            }
        }
        writeToAddress(address, parseBinary(binary));
    }
}
