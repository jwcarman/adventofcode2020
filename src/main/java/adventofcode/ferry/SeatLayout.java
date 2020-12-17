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

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import static adventofcode.io.Input.readLines;

public class SeatLayout {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final List<String> rows;
    private final int width;
    private final int height;
    private final Table<Integer, Integer, Seat> table;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public SeatLayout(String input) {
        this.rows = readLines(input);
        this.width = rows.get(0).length();
        this.height = rows.size();
        this.table = HashBasedTable.create(height, width);
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public List<Seat> createDirectNeighborSeats() {
        final List<Seat> seats = createUnlinkedSeats();
        for (Seat seat : seats) {
            seat.linkDirectNeighbors(table);
        }
        return seats;
    }

    private List<Seat> createUnlinkedSeats() {
        final List<Seat> seats = new LinkedList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                final char c = rows.get(row).charAt(col);
                if (c == Seat.EMPTY) {
                    seats.add(getOrCreateSeat(row, col));
                }
            }
        }
        return seats;
    }

    private Seat getOrCreateSeat(int row, int col) {
        Seat seat = table.get(row, col);
        if (seat == null) {
            seat = new Seat(row, col);
            table.put(row, col, seat);
        }
        return seat;
    }

    public SeatState createInitialState() {
        return new BitSetSeatState(width, height);
    }

    public List<Seat> createVisibleNeighborSeats() {
        final List<Seat> seats = createUnlinkedSeats();
        for (Seat seat : seats) {
            seat.linkVisibleNeighbors(table, height, width);
        }
        return seats;
    }
}
