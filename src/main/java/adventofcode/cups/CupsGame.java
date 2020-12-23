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

package adventofcode.cups;

import java.util.List;

public class CupsGame {

    private final CupList cups;
    private final int n;

    public CupsGame(List<Integer> seeds) {
        this(seeds, seeds.size());
    }

    public CupsGame(List<Integer> seeds, int n) {
        this.n = n;
        this.cups = new CupList(n);
        int max = 0;
        for (final Integer cup : seeds) {
            cups.add(cup);
            max = Math.max(max, cup);
        }
        for (int cup = max + 1; cup <= n; ++cup) {
            cups.add(cup);
        }
    }

    public List<Integer> cupsAfter(int cup, int numberOfCups) {
        return cups.collectN(numberOfCups, cup);
    }

    public void move() {
        final int currentCup = cups.cycle();
        final List<Integer> removed = cups.takeN(3);
        final int destination = findDestination(removed, currentCup);
        cups.insertAfter(destination, removed);
    }

    private int findDestination(List<Integer> removed, int currentCup) {
        int destination = currentCup;
        do {
            destination = destination == 1 ? n : destination - 1;
        } while (removed.contains(destination));
        return destination;
    }
}
