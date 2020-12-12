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

import com.google.common.collect.Table;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"row", "col"})
public class Seat {

    public static final char EMPTY = 'L';

    private final int row;
    private final int col;
    private final List<Seat> neighbors;

    Seat(int row, int col) {
        this.row = row;
        this.col = col;
        this.neighbors = new LinkedList<>();
    }

    public List<Seat> getNeighbors() {
        return neighbors;
    }

    public boolean applyRules(SeatState oldState, SeatState newState, int maxNeighbors) {
        final long neighborCount = occupiedNeighborCount(oldState);

        if (isOccupied(oldState)) {
            if (neighborCount >= maxNeighbors) {
                newState.empty(row, col);
                return true;
            }
        } else {
            if (neighborCount == 0) {
                newState.occupy(row, col);
                return true;
            }
        }
        return false;
    }

    public boolean isOccupied(SeatState seatState) {
        return seatState.isOccupied(row, col);
    }

    void linkDirectNeighbors(Table<Integer, Integer, Seat> table) {
        addNeighbor(table.get(row - 1, col - 1));
        addNeighbor(table.get(row - 1, col));
        addNeighbor(table.get(row - 1, col + 1));
        addNeighbor(table.get(row, col - 1));
        addNeighbor(table.get(row, col + 1));
        addNeighbor(table.get(row + 1, col - 1));
        addNeighbor(table.get(row + 1, col));
        addNeighbor(table.get(row + 1, col + 1));
    }

    void linkVisibleNeighbors(Table<Integer, Integer, Seat> table, int height, int width) {
        addNeighbor(findVisibleNeighbor(table, -1, -1, height, width));
        addNeighbor(findVisibleNeighbor(table, -1, 0, height, width));
        addNeighbor(findVisibleNeighbor(table, -1, 1, height, width));
        addNeighbor(findVisibleNeighbor(table, 0, -1, height, width));
        addNeighbor(findVisibleNeighbor(table, 0, +1, height, width));
        addNeighbor(findVisibleNeighbor(table, 1, -1, height, width));
        addNeighbor(findVisibleNeighbor(table, 1, 0, height, width));
        addNeighbor(findVisibleNeighbor(table, 1, 1, height, width));
    }

    private Seat findVisibleNeighbor(Table<Integer, Integer, Seat> table, int dRow, int dCol, int height, int width) {
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < height && c >= 0 && c < width) {
            Seat seat = table.get(r, c);
            if (seat != null) {
                return seat;
            }
            r += dRow;
            c += dCol;
        }
        return null;
    }

    private void addNeighbor(Seat neighbor) {
        if (neighbor != null) {
            neighbors.add(neighbor);
        }
    }

    private long occupiedNeighborCount(SeatState seatState) {
        long count = 0;
        for (Seat neighbor : neighbors) {
            if (neighbor.isOccupied(seatState)) {
                count++;
            }
        }
        return count;
    }
}
