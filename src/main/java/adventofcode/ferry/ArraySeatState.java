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

import org.apache.commons.lang3.ArrayUtils;

public class ArraySeatState implements SeatState {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final int OCCUPIED = 1;
    public static final int EMPTY = 0;
    private final short[] occupied;
    private final int width;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public ArraySeatState(int width, int height) {
        this.occupied = new short[width * height];
        this.width = width;
    }

    private ArraySeatState(short[] occupied, int width) {
        this.occupied = occupied;
        this.width = width;
    }

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public SeatState clone() {
        return new ArraySeatState(ArrayUtils.clone(occupied), width);
    }

//----------------------------------------------------------------------------------------------------------------------
// SeatState Implementation
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public void empty(int row, int col) {
        occupied[indexOf(row, col)] = EMPTY;
    }

    @Override
    public boolean isOccupied(int row, int col) {
        return occupied[indexOf(row, col)] == OCCUPIED;
    }

    @Override
    public void occupy(int row, int col) {
        occupied[indexOf(row, col)] = OCCUPIED;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private int indexOf(int row, int col) {
        return row * width + col;
    }
}
