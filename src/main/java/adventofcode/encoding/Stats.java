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
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of sliding window maximum/minimum inspired
 * by <a href="https://www.nayuki.io/page/sliding-window-minimum-maximum-algorithm">Nayuki</a>.
 */
@Slf4j
public class Stats {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private long sum;

    private final Deque<Long> maximums = new LinkedList<>();
    private final Deque<Long> minimums = new LinkedList<>();

//----------------------------------------------------------------------------------------------------------------------
// Getters/Setters
//----------------------------------------------------------------------------------------------------------------------

    public long getSum() {
        return sum;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public void add(long x) {
        addToMaximums(x);
        addToMinimums(x);
        sum += x;
    }

    private void addToMaximums(long x) {
        log.debug("Adding {} to maximums {}", x, maximums);
        addLast(maximums, x, last -> last < x);
        log.debug("Result  {}", maximums);
    }

    private void addToMinimums(long x) {
        log.debug("Adding {} to minimums {}", x, minimums);
        addLast(minimums, x, last -> last > x);
        log.debug("Result  {}", minimums);
    }

    private void addLast(Deque<Long> deque, long x, Predicate<Long> condition) {
        while (!deque.isEmpty() && condition.test(deque.getLast())) {
            log.debug("Removing {}", deque.getLast());
            deque.removeLast();
        }
        deque.addLast(x);
    }

    public long getMax() {
        return maximums.getFirst();
    }

    public long getMin() {
        return minimums.getFirst();
    }

    public void remove(long x) {
        removeIfFirst(maximums, x);
        removeIfFirst(minimums, x);
        sum -= x;
    }

    private void removeIfFirst(Deque<Long> deque, long x) {
        if (deque.getFirst() == x) {
            deque.removeFirst();
        }
    }
}
