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

package adventofcode.ticket;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@Value
@EqualsAndHashCode(of = "name")
public class TicketField {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    String name;
    List<Pair<Integer, Integer>> validRanges;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public TicketField(String input) {
        final String[] splits = input.split(":");
        this.name = StringUtils.trim(splits[0]);
        this.validRanges = parseValidRanges(splits[1]);
    }

    private static List<Pair<Integer, Integer>> parseValidRanges(String input) {
        return Arrays.stream(input.trim().replace(" or ", ",").split(","))
                .map(s -> {
                    int index = s.indexOf('-');
                    final int min = Integer.parseInt(s.substring(0, index));
                    final int max = Integer.parseInt(s.substring(index + 1));
                    return new ImmutablePair<>(min, max);
                })
                .collect(Collectors.toList());
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public boolean matches(int value) {
        return validRanges.stream().anyMatch(range -> rangeContains(range, value));
    }

    private boolean rangeContains(Pair<Integer, Integer> range, int value) {
        return range.getLeft() <= value && range.getRight() >= value;
    }
}
