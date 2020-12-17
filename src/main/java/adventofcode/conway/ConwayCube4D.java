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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Value;

@Value
public class ConwayCube4D implements ConwayCube {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final Map<ConwayCube4D, List<ConwayCube>> NEIGHBORS = new HashMap<>();

    int x;
    int y;
    int z;
    int w;

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    public String toString() {
        return String.format("(%d,%d,%d)", x, y, z);
    }

//----------------------------------------------------------------------------------------------------------------------
// ConwayCube Implementation
//----------------------------------------------------------------------------------------------------------------------

    public List<ConwayCube> neighborhood() {
        return NEIGHBORS.computeIfAbsent(this, cube -> {
            List<ConwayCube> n = new ArrayList<>(26);
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    for (int dz = -1; dz <= 1; ++dz) {
                        for (int dw = -1; dw <= 1; ++dw) {
                            n.add(new ConwayCube4D(cube.x + dx, cube.y + dy, cube.z + dz, cube.w + dw));
                        }
                    }
                }
            }
            return n;
        });
    }
}
