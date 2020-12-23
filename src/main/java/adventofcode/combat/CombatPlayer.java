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

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;
import java.util.stream.Collectors;

import com.codepoetics.protonpack.StreamUtils;
import lombok.EqualsAndHashCode;

import static adventofcode.io.Input.readLines;

@EqualsAndHashCode(of = "id")
public class CombatPlayer {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final String id;
    private final Deque<Integer> deck;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public CombatPlayer(String input) {
        this(UUID.randomUUID().toString(), parseDeck(input));
    }

    private CombatPlayer(String id, Deque<Integer> deck) {
        this.id = id;
        this.deck = deck;
    }

    private static Deque<Integer> parseDeck(String input) {
        return readLines(input.trim()).stream()
                .skip(1)
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
    }

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    public String toString() {
        return deck.toString();
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public int cardsRemaining() {
        return deck.size();
    }

    public CombatPlayer createSubGamePlayer(int nCards) {
        final LinkedList<Integer> subGameDeck = this.deck.stream()
                .limit(nCards)
                .collect(Collectors.toCollection(LinkedList::new));
        return new CombatPlayer(id, subGameDeck);
    }

    public int deal() {
        return deck.remove();
    }

    public boolean hasLost() {
        return deck.isEmpty();
    }

    public long score() {
        return StreamUtils.zipWithIndex(deck.stream())
                .mapToLong(indexed -> (deck.size() - indexed.getIndex()) * indexed.getValue())
                .sum();
    }

    public void takeCards(int myCard, int theirCard) {
        deck.add(myCard);
        deck.add(theirCard);
    }
}
