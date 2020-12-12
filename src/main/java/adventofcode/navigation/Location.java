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

package adventofcode.navigation;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Location origin() {
        return new Location(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void moveNorth(int value) {
        moveTo(x, y + value);
    }

    void moveSouth(int value) {
        moveTo(x, y - value);
    }

    void moveEast(int value) {
        moveTo(x + value, y);
    }

    void moveWest(int value) {
        moveTo(x - value, y);
    }

    private void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    void translateBy(int dx, int dy) {
        x += dx;
        y += dy;
    }

    void rotateLeft(int degrees) {
        switch (degrees) {
            case 90 -> moveTo(-y, x);
            case 180 -> moveTo(-x, -y);
            case 270 -> moveTo(y, -x);
        }
        ;
    }

    void rotateRight(int degrees) {
        switch (degrees) {
            case 90 -> moveTo(y, -x);
            case 180 -> moveTo(-x, -y);
            case 270 -> moveTo(-y, x);
        }
        ;
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }
}
