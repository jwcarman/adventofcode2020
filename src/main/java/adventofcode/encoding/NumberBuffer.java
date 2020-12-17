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

package adventofcode.encoding;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class NumberBuffer {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Queue<Long> buffer;
    private final Multiset<Long> set;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public NumberBuffer(List<Long> preamble) {
        this.buffer = new LinkedList<>(preamble);
        this.set = HashMultiset.create(preamble);
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public boolean isValid(long n) {
        final boolean valid = set.stream().anyMatch(i -> set.contains(n - i));
        set.remove(buffer.remove());
        set.add(n);
        buffer.add(n);
        return valid;
    }
}
