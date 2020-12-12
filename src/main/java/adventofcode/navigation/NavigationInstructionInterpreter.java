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

public abstract class NavigationInstructionInterpreter {
    protected int x = 0;
    protected int y = 0;
    protected Waypoint waypoint;

    protected abstract void processInstruction(char action, int value);

    public final void processInstruction(String instruction) {
        final char action = instruction.charAt(0);
        final int value = Integer.parseInt(instruction.substring(1));
        processInstruction(action, value);
    }

    protected void moveToWaypoint(int times) {
        x += waypoint.getX() * times;
        y += waypoint.getY() * times;
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }
}
