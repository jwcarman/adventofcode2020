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

public class ShipInterpreter extends NavigationInstructionInterpreter {

    public ShipInterpreter() {
        this.waypoint = new Waypoint(1, 0);
    }

    @Override
    protected void processInstruction(char action, int value) {
        switch (action) {
            case 'N' -> y += value;
            case 'S' -> y -= value;
            case 'E' -> x += value;
            case 'W' -> x -= value;
            case 'L' -> waypoint = waypoint.rotateLeft(value);
            case 'R' -> waypoint = waypoint.rotateRight(value);
            default -> moveToWaypoint(value);
        }
    }
}
