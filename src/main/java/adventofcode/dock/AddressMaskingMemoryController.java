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

public class AddressMaskingMemoryController extends BaseMemoryController {

    private String mask = fillMask('0');

    @Override
    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public void writeValue(long address, long value) {
        final List<String> addresses = new ArrayList<>();
        addresses.add("");
        applyMask(binaryStringOf(address), addresses);
        addresses.stream()
                .mapToLong(BaseMemoryController::parseBinary)
                .forEach(a -> writeToAddress(a, value));
    }

    private void applyMask(String binary, List<String> addresses) {
        for (int i = 0; i < MASK_SIZE; ++i) {
            final char maskChar = mask.charAt(i);
            switch (maskChar) {
                case '1' -> appendToAddresses(addresses, '1');
                case '0' -> appendToAddresses(addresses, binary.charAt(i));
                default -> {
                    final List<String> copy = new ArrayList<>(addresses);
                    appendToAddresses(addresses, '0');
                    appendToAddresses(copy, '1');
                    addresses.addAll(copy);
                }
            }
        }
    }

    private void appendToAddresses(List<String> addresses, char c) {
        for (int i = 0; i < addresses.size(); i++) {
            addresses.set(i, addresses.get(i) + c);
        }
    }
}
