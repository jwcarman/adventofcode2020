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

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Suppliers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import static adventofcode.io.Input.readLines;

@Slf4j
public class ImageTile {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String HEADER_ID_LABEL = "Tile ";

    private final long id;
    private final int size;
    private final char[] data;
    private final Supplier<String> northBorder = Suppliers.memoize(this::calculateNorthBorder);
    private final Supplier<String> eastBorder = Suppliers.memoize(this::calculateEastBorder);
    private final Supplier<String> southBorder = Suppliers.memoize(this::calculateSouthBorder);
    private final Supplier<String> westBorder = Suppliers.memoize(this::calculateWestBorder);
    private final Supplier<String> toString = Suppliers.memoize(this::calculateToString);

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public ImageTile(int size, char[] data) {
        this.id = -1L;
        this.size = size;
        this.data = data;
    }

    private ImageTile(ImageTile original, BiFunction<Integer, Integer, Pair<Integer, Integer>> xform) {
        this.id = original.id;
        this.size = original.size;
        this.data = new char[original.data.length];
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                final Pair<Integer, Integer> coord = xform.apply(x, y);
                setCharAt(coord.getLeft(), coord.getRight(), original.getCharAt(x, y));
            }
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    private ImageTile(long id, int size, char[] data) {
        this.id = id;
        this.size = size;
        this.data = data;
    }

    public static ImageTile parse(String input) {
        final List<String> lines = readLines(input);
        final String header = lines.get(0);
        final long id = Long.parseLong(header.substring(HEADER_ID_LABEL.length(), header.length() - 1).trim());
        return new ImageTile(id, lines.size() - 1, String.join("", lines.subList(1, lines.size())).toCharArray());
    }

    public static ImageTile composite(int n, List<ImageTile> tiles) {
        final List<ImageTile> borderless = tiles.stream().map(ImageTile::borderless).collect(Collectors.toList());
        final int imageSize = borderless.get(0).getSize();
        final StringBuilder sb = new StringBuilder();
        for (int row = 0; row < n; ++row) {
            for (int line = 0; line < imageSize; ++line) {
                for (int col = 0; col < n; ++col) {
                    final ImageTile tile = borderless.get(row * n + col);
                    sb.append(tile.data, line * imageSize, imageSize);
                }
            }
        }
        return new ImageTile(imageSize * n, sb.toString().toCharArray());
    }

    private void setCharAt(int x, int y, char value) {
        data[indexOf(x, y)] = value;
    }

    private int indexOf(int x, int y) {
        return (y * size) + x;
    }

    private char getCharAt(int x, int y) {
        return data[indexOf(x, y)];
    }

//----------------------------------------------------------------------------------------------------------------------
// Getters/Setters
//----------------------------------------------------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    public String toString() {
        return toString.get();
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public ImageTile borderless() {
        final StringBuilder sb = new StringBuilder();
        for (int y = 1; y < size - 1; ++y) {
            for (int x = 1; x < size - 1; ++x) {
                sb.append(getCharAt(x, y));
            }
        }
        return new ImageTile(id, size - 2, sb.toString().toCharArray());
    }

    private String calculateNorthBorder() {
        return String.copyValueOf(data, 0, size);
    }

    private String calculateSouthBorder() {
        return String.copyValueOf(data, size * (size - 1), size);
    }

    private String calculateToString() {
        return String.copyValueOf(data);
    }

    private String calculateWestBorder() {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < size; ++y) {
            sb.append(getCharAt(0, y));
        }
        return sb.toString();
    }

    public String eastBorder() {
        eastBorder.get();
        return calculateEastBorder();
    }

    private String calculateEastBorder() {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < size; ++y) {
            sb.append(getCharAt(size - 1, y));
        }
        return sb.toString();
    }

    public String northBorder() {
        return northBorder.get();
    }

    public String southBorder() {
        return southBorder.get();
    }

    public List<ImageTile> variants() {
        return List.of(this,
                variant((x, y) -> Pair.of(size - y - 1, x)),
                variant((x, y) -> Pair.of(size - x - 1, size - y - 1)),
                variant((x, y) -> Pair.of(y, size - x - 1)),
                variant((x, y) -> Pair.of(y, x)),
                variant((x, y) -> Pair.of(x, size - y - 1)),
                variant((x, y) -> Pair.of(size - y - 1, size - x - 1)),
                variant((x, y) -> Pair.of(size - x - 1, y)));
    }

    private ImageTile variant(BiFunction<Integer, Integer, Pair<Integer, Integer>> xform) {
        return new ImageTile(this, xform);
    }

    public String westBorder() {
        return westBorder.get();
    }
}
