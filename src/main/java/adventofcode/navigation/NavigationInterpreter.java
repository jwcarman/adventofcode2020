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

public class NavigationInterpreter {

    private final Location shipLocation;
    private final Location waypointLocation;
    private final Location directionalMoveTarget;

    public NavigationInterpreter(Location shipLocation, Location waypointLocation, Location directionalMoveTarget) {
        this.shipLocation = shipLocation;
        this.waypointLocation = waypointLocation;
        this.directionalMoveTarget = directionalMoveTarget;
    }

    public final void processInstruction(String instruction) {
        final char action = instruction.charAt(0);
        final int value = Integer.parseInt(instruction.substring(1));
        switch (action) {
            case 'N' -> directionalMoveTarget.moveNorth(value);
            case 'S' -> directionalMoveTarget.moveSouth(value);
            case 'E' -> directionalMoveTarget.moveEast(value);
            case 'W' -> directionalMoveTarget.moveWest(value);
            case 'L' -> waypointLocation.rotateLeft(value);
            case 'R' -> waypointLocation.rotateRight(value);
            default -> moveToWaypoint(value);
        }
    }

    public Location getShipLocation() {
        return shipLocation;
    }

    protected void moveToWaypoint(int times) {
        shipLocation.translateBy(waypointLocation.getX() * times, waypointLocation.getY() * times);
    }
}
