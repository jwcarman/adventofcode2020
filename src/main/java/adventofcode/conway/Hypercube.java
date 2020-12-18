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

package adventofcode.conway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ArrayUtils;

@EqualsAndHashCode(of = "coordinates")
public class Hypercube implements ConwayCube {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final int[] coordinates;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public Hypercube(int... coordinates) {
        this.coordinates = coordinates;
    }


    @Override
    public List<ConwayCube> neighborhood() {
        final List<int[]> neighborCoordinates = new ArrayList<>((int) Math.pow(3, coordinates.length));
        neighborCoordinates.add(ArrayUtils.EMPTY_INT_ARRAY);
        for (int coordinate : coordinates) {
            final List<int[]> copy = new ArrayList<>(neighborCoordinates);
            neighborCoordinates.clear();
            for (int[] coords : copy) {
                neighborCoordinates.add(ArrayUtils.add(coords, coordinate - 1));
                neighborCoordinates.add(ArrayUtils.add(coords, coordinate));
                neighborCoordinates.add(ArrayUtils.add(coords, coordinate + 1));
            }
        }

        final List<ConwayCube> neighborhood = new ArrayList<>(neighborCoordinates.size());
        for (int[] neighborCoordinate : neighborCoordinates) {
            neighborhood.add(new Hypercube(neighborCoordinate));
        }
        return neighborhood;
    }

    public String toString() {
        return String.format("(%s)", Arrays.stream(coordinates).mapToObj(Integer::toString).collect(Collectors.joining(",")));
    }
}
