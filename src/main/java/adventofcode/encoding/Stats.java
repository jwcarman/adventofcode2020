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

import java.util.List;

public class Stats {
    private long smallest;
    private long largest;

    public Stats(List<Long> numbers) {
        this.smallest = Long.MAX_VALUE;
        this.largest = Long.MIN_VALUE;
        numbers.forEach(n -> {
            smallest = Math.min(smallest, n);
            largest = Math.max(largest, n);
        });
    }

    public long getSmallest() {
        return smallest;
    }

    public long getLargest() {
        return largest;
    }
}
