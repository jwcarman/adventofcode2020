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

package adventofcode.monster;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Neighbors {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Multimap<String, ImageTile> eastNeighbors = HashMultimap.create();
    private final Multimap<String, ImageTile> southNeighbors = HashMultimap.create();
    private final List<ImageTile> all = new LinkedList<>();

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public void add(ImageTile tile) {
        eastNeighbors.put(tile.westBorder(), tile);
        southNeighbors.put(tile.northBorder(), tile);
        all.add(tile);
    }

    public List<ImageTile> all() {
        return all;
    }

    public ImageTile eastNeighborOf(ImageTile tile) {
        return eastNeighbors.get(tile.eastBorder()).stream()
                .filter(neighbor -> neighbor.getId() != tile.getId())
                .findFirst()
                .orElse(null);
    }

    public ImageTile southNeighborOf(ImageTile tile) {
        return southNeighbors.get(tile.southBorder()).stream()
                .filter(neighbor -> neighbor.getId() != tile.getId())
                .findFirst()
                .orElse(null);
    }
}
