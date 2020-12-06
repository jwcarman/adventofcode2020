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

package adventofcode.toboggan;

import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PathGenerator implements Supplier<Position> {
    private final int dx;
    private final int dy;
    private final int maxX;
    private int x = 0;
    private int y = 0;

    @Override
    public Position get() {
        final Position position = new Position(x, y);
        x = (x + dx) % maxX;
        y += dy;
        return position;
    }
}
