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

package adventofcode.combat;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;

@Builder
public class RecursiveCombatGame {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final CombatPlayer player1;
    private final CombatPlayer player2;
    private final Set<String> history = new HashSet<>();

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public CombatGameResult play() {
        while (!gameIsOver()) {
            if (loopDetected()) {
                return CombatGameResult.builder()
                        .winner(player1)
                        .build();
            }
            final int player1Card = player1.deal();
            final int player2Card = player2.deal();
            if (isRecursionRound(player1Card, player2Card)) {
                playSubGame(player1Card, player2Card);
            } else {
                playRound(player1Card, player2Card);
            }
        }
        return CombatGameResult.create(player1, player2);
    }

    private boolean loopDetected() {
        return !history.add(player1 + " vs. " + player2);
    }

    private boolean gameIsOver() {
        return player1.hasLost() || player2.hasLost();
    }

    private boolean isRecursionRound(int player1Card, int player2Card) {
        return player1.cardsRemaining() >= player1Card && player2.cardsRemaining() >= player2Card;
    }

    private void playSubGame(int player1Card, int player2Card) {
        final RecursiveCombatGame subGame = new RecursiveCombatGame(player1.createSubGamePlayer(player1Card), player2.createSubGamePlayer(player2Card));
        final CombatGameResult subGameResult = subGame.play();
        if (player1.equals(subGameResult.winner())) {
            player1.takeCards(player1Card, player2Card);
        } else {
            player2.takeCards(player2Card, player1Card);
        }
    }

    protected void playRound(int player1Card, int player2Card) {
        if (player1Card > player2Card) {
            player1.takeCards(player1Card, player2Card);
        } else {
            player2.takeCards(player2Card, player1Card);
        }
    }
}
