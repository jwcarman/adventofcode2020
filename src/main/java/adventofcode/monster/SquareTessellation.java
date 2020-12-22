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

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SquareTessellation {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final int n;
    private final ImageTile[] tiles;

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    private SquareTessellation(int n, ImageTile[] tiles) {
        this.n = n;
        this.tiles = tiles;
    }

    public static SquareTessellation createSquareTessellation(int n, Neighbors neighbors, ImageTile start) {
        final ImageTile[] tiles = new ImageTile[n * n];
        for (int y = 0; y < n; ++y) {
            for (int x = 0; x < n; ++x) {
                if (x == 0 && y == 0) {
                    tiles[0] = start;
                } else {
                    ImageTile neighbor = null;
                    if (y == 0) {
                        neighbor = neighbors.eastNeighborOf(tiles[indexOf(n, x - 1, y)]);
                    } else if (x == 0) {
                        neighbor = neighbors.southNeighborOf(tiles[indexOf(n, x, y - 1)]);
                    } else {
                        final ImageTile east = neighbors.eastNeighborOf(tiles[indexOf(n, x - 1, y)]);
                        final ImageTile south = neighbors.southNeighborOf(tiles[indexOf(n, x, y - 1)]);
                        neighbor = east == south ? east : null;
                    }
                    if (neighbor == null) {
                        return null;
                    }
                    tiles[y * n + x] = neighbor;
                }
            }
        }
        return new SquareTessellation(n, tiles);
    }

    private static int indexOf(int n, int x, int y) {
        return y * n + x;
    }

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public ImageTile createCompositeImageTile() {
        return ImageTile.composite(n, Arrays.stream(tiles).collect(Collectors.toList()));
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public ImageTile bottomLeft() {
        return tiles[tiles.length - n];
    }

    public ImageTile bottomRight() {
        return tiles[tiles.length - 1];
    }

    public long checksum() {
        return topLeft().getId() * topRight().getId() * bottomLeft().getId() * bottomRight().getId();
    }

    public ImageTile topLeft() {
        return tiles[0];
    }

    public ImageTile topRight() {
        return tiles[n - 1];
    }
}
