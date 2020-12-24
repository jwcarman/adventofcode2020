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

package adventofcode.hextile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HexPath {

    private final List<HexDirection> directions;

    public HexPath(String spec) {
        this.directions = parseDirections(spec);
    }

    private static List<HexDirection> parseDirections(String spec) {
        return Arrays.stream(spec
                .toUpperCase()
                .replace("SW", " SW ")
                .replace("NW", " NW ")
                .replace("NE", " NE ")
                .replace("SE", " SE ")
                .replace("EE", " E E ")
                .replace("WW", " W W ")
                .replace("WE", " W E ")
                .replace("EW", " E W ")
                .replaceAll("\\s+", " ")
                .trim().split("\\s+"))
                .map(HexDirection::valueOf)
                .collect(Collectors.toList());
    }

    public HexPoint from(HexPoint origin) {
        return directions.stream()
                .reduce(origin, (point, direction) -> direction.translate(point), (left, right) -> right);
    }
}
