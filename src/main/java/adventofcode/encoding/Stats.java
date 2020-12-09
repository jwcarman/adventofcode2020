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

import java.util.Deque;
import java.util.LinkedList;

/**
 * Implementation of sliding window maximum/minimum inspired
 * by <a href="https://www.nayuki.io/page/sliding-window-minimum-maximum-algorithm">Nayuki</a>.
 */
public class Stats {
    private long sum;

    private Deque<Long> maximums = new LinkedList<>();
    private Deque<Long> minimums = new LinkedList<>();

    public void add(long x) {
        while (!maximums.isEmpty() && maximums.getLast() < x) {
            maximums.removeLast();
        }
        maximums.addLast(x);

        while (!minimums.isEmpty() && minimums.getLast() > x) {
            minimums.removeLast();
        }
        minimums.addLast(x);
        sum += x;
    }

    public void remove(long x) {
        if (maximums.getFirst() == x) {
            maximums.removeFirst();
        }
        if (minimums.getFirst() == x) {
            minimums.removeFirst();
        }
        sum -= x;
    }

    public long getMax() {
        return maximums.getFirst();
    }

    public long getMin() {
        return minimums.getFirst();
    }

    public long getSum() {
        return sum;
    }
}
