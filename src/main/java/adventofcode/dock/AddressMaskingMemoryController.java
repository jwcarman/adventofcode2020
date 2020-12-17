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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressMaskingMemoryController extends BaseMemoryController {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private Long orMask = 0L;
    private List<Long> xorMasks;

//----------------------------------------------------------------------------------------------------------------------
// MemoryController Implementation
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public void setMask(String mask) {
        this.orMask = parseBinary(mask.replace('X', '0'));
        List<String> maskStrings = new ArrayList<>();
        maskStrings.add("");
        for (int i = 0; i < mask.length(); ++i) {
            final char character = mask.charAt(i);
            if (character == 'X') {
                final List<String> copy = new ArrayList<>(maskStrings);
                appendToAddresses(maskStrings, '0');
                appendToAddresses(copy, '1');
                maskStrings.addAll(copy);
            } else {
                appendToAddresses(maskStrings, '0');
            }
        }
        xorMasks = maskStrings.stream()
                .map(BaseMemoryController::parseBinary)
                .collect(Collectors.toList());
    }

    @Override
    public void writeValue(long address, long value) {
        final long anded = address | orMask;
        for (Long xorMask : xorMasks) {
            writeToAddress(xorMask ^ anded, value);
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private void appendToAddresses(List<String> addresses, char c) {
        for (int i = 0; i < addresses.size(); i++) {
            addresses.set(i, addresses.get(i) + c);
        }
    }
}
