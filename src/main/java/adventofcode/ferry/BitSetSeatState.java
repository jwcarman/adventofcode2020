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

package adventofcode.ferry;

import java.util.BitSet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitSetSeatState implements SeatState {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final BitSet bits;
    private final int width;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public BitSetSeatState(int width, int height) {
        this.width = width;
        this.bits = new BitSet(width * height);
    }

    private BitSetSeatState(BitSet bits, int width) {
        this.bits = bits;
        this.width = width;
    }

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public SeatState clone() {
        return new BitSetSeatState((BitSet) bits.clone(), width);
    }

//----------------------------------------------------------------------------------------------------------------------
// SeatState Implementation
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public void empty(int row, int col) {
        bits.clear(indexOf(row, col));
    }

    @Override
    public boolean isOccupied(int row, int col) {
        return bits.get(indexOf(row, col));
    }

    @Override
    public void occupy(int row, int col) {
        bits.set(indexOf(row, col));
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private int indexOf(int row, int col) {
        return row * width + col;
    }
}
