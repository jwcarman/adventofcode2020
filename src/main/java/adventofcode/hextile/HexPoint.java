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

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"x", "y"})
public class HexPoint {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final HexPoint ORIGIN = new HexPoint(0, 0);
    int x;
    int y;

    Supplier<List<HexPoint>> neighborhood = Suppliers.memoize(this::calculateNeighborhood);

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    public String toString() {
        return "(" + x + "," + y + ")";
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private List<HexPoint> calculateNeighborhood() {
        return List.of(
                this,
                new HexPoint(x - 2, y),
                new HexPoint(x + 2, y),
                new HexPoint(x - 1, y - 1),
                new HexPoint(x - 1, y + 1),
                new HexPoint(x + 1, y - 1),
                new HexPoint(x + 1, y + 1)
        );
    }

    public List<HexPoint> neighborhood() {
        return neighborhood.get();
    }
}
