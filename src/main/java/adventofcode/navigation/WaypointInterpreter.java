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

public class WaypointInterpreter {

    private int x = 0;
    private int y = 0;
    private Waypoint waypoint = new Waypoint(10, 1);

    public void processInstruction(String instruction) {
        final char action = instruction.charAt(0);
        final int value = Integer.parseInt(instruction.substring(1));

        switch (action) {
            case 'N' -> waypoint = waypoint.moveNorth(value);
            case 'S' -> waypoint = waypoint.moveSouth(value);
            case 'E' -> waypoint = waypoint.moveEast(value);
            case 'W' -> waypoint = waypoint.moveWest(value);
            case 'L' -> waypoint = waypoint.rotateLeft(value);
            case 'R' -> waypoint = waypoint.rotateRight(value);
            default -> moveToWaypoint(value);
        }
    }

    private void moveToWaypoint(int times) {
        x += waypoint.getX() * times;
        y += waypoint.getY() * times;
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }
}
