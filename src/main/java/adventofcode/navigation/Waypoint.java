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

import lombok.Value;

@Value
class Waypoint {
    int x;
    int y;

    Waypoint moveNorth(int value) {
        return new Waypoint(x, y + value);
    }

    Waypoint moveSouth(int value) {
        return new Waypoint(x, y - value);
    }

    Waypoint moveEast(int value) {
        return new Waypoint(x + value, y);
    }

    Waypoint moveWest(int value) {
        return new Waypoint(x - value, y);
    }


    Waypoint rotateLeft(int degrees) {
        return switch (degrees) {
            case 90 -> new Waypoint(-y, x);
            case 180 -> new Waypoint(-x, -y);
            case 270 -> new Waypoint(y, -x);
            default -> this;
        };
    }

    Waypoint rotateRight(int degrees) {
        return switch (degrees) {
            case 90 -> new Waypoint(y, -x);
            case 180 -> new Waypoint(-x, -y);
            case 270 -> new Waypoint(-y, x);
            default -> this;
        };
    }

}
