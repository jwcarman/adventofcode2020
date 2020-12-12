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

package adventofcode.boarding;

import lombok.Value;

@Value
public class BoardingPass {
    String seat;

    public int calculateSeatId() {
        final int rowNumber = toInt(seat.substring(0, 7), 'F', 'B');
        final int colNumber = toInt(seat.substring(7), 'L', 'R');
        return rowNumber * 8 + colNumber;
    }

    private int toInt(String spec, char upper, char lower) {
        int min = 0;
        int max = (1 << spec.length()) - 1;
        for (char c : spec.toCharArray()) {
            final int range = max - min + 1;
            if (c == upper) {
                max -= range / 2;
            } else if (c == lower) {
                min += range / 2;
            }
        }
        return max;
    }
}
