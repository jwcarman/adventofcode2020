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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class MonsterScanner {

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static final char DEFAULT_HIGHLIGHT_CHARACTER = 'O';

    public static String highlightMonsters(ImageTile image) {
        return highlightMonsters(image, DEFAULT_HIGHLIGHT_CHARACTER);
    }

    public static String highlightMonsters(ImageTile image, char highlightCharacter) {
        final String regex = createScanningRegex(image);
        final Matcher matcher = Pattern.compile(regex).matcher(image.toString());
        final List<Integer> indices = new LinkedList<>();
        int ndx = 0;
        while (matcher.find(ndx)) {
            final int start = matcher.start();
            indices.add(start);
            ndx = start + 1;
        }
        return highlightMonsters(image, highlightCharacter, regex, indices);
    }

    private static String highlightMonsters(ImageTile image, char highlightCharacter, String regex, List<Integer> indices) {
        final StringBuilder builder = new StringBuilder(image.toString());
        final List<Integer> offsets = monsterOffsets(regex);

        for (Integer index : indices) {
            for (Integer offset : offsets) {
                builder.setCharAt(index + offset, highlightCharacter);
            }
        }
        return builder.toString();
    }

    private static List<Integer> monsterOffsets(String regex) {
        final List<Integer> offsets = new LinkedList<>();
        int offset = regex.indexOf('#');
        while (offset != -1) {
            offsets.add(offset);
            offset = regex.indexOf('#', offset + 1);
        }
        return offsets;
    }

    private static String createScanningRegex(ImageTile image) {
        final String padding = StringUtils.repeat('.', image.getSize() - 19);
        return "#" +
                padding +
                "#....##....##....###" +
                padding +
                "#..#..#..#..#..#";
    }
}
