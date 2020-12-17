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

package adventofcode.recitation;

import java.util.List;

public class RecitationGame {

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static int findNthSpokenNumber(List<Integer> startingNumbers, int n) {
        final int[] previousTurns = new int[n + 1];
        for (int i = 0; i < startingNumbers.size() - 1; i++) {
            Integer number = startingNumbers.get(i);
            previousTurns[number] = i + 1;
        }
        int lastNumberSpoken = startingNumbers.get(startingNumbers.size() - 1);
        for (int turn = startingNumbers.size(); turn <= n; ++turn) {
            if (turn == n) {
                return lastNumberSpoken;
            }
            final int previousTurn = previousTurns[lastNumberSpoken];
            previousTurns[lastNumberSpoken] = turn;
            if (previousTurn == 0) {
                lastNumberSpoken = 0;
            } else {
                lastNumberSpoken = turn - previousTurn;
            }
        }

        return -1;
    }
}
