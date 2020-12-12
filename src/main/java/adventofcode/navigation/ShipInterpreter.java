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

public class ShipInterpreter {
    private int bearing = 0;
    private int x = 0;
    private int y = 0;

    public void processInstruction(String instruction) {
        final char action = instruction.charAt(0);
        final int value = Integer.parseInt(instruction.substring(1));

        switch (action) {
            case 'N' -> y += value;
            case 'S' -> y -= value;
            case 'E' -> x += value;
            case 'W' -> x -= value;
            case 'L' -> turnLeft(value);
            case 'R' -> turnRight(value);
            default -> moveForward(value);
        }
    }

    private void turnRight(int degrees) {
        bearing = (360 + bearing - degrees) % 360;
    }

    private void turnLeft(int degrees) {
        bearing = (bearing + degrees) % 360;
    }

    private void moveForward(int value) {
        switch (bearing) {
            case 0 -> x += value;
            case 90 -> y += value;
            case 180 -> x -= value;
            case 270 -> y -= value;
        }
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }
}
