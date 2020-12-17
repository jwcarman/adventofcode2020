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

package adventofcode.customs;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PassengerGroup {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Multiset<Integer> answers = HashMultiset.create(26);
    private int size = 0;

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static List<PassengerGroup> parseFromInput(List<String> lines) {
        lines.add("");
        return lines.stream().collect(LinkedList::new, new Accumulator(), List::addAll);
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public void addMemberAnswers(String memberAnswers) {
        memberAnswers.chars().forEach(answers::add);
        size++;
    }

    public long calculateUnanimousAnswers() {
        return answers.entrySet().stream()
                .filter(e -> e.getCount() == size)
                .count();
    }

    public long calculateUniqueAnswers() {
        return answers.elementSet().size();
    }

//----------------------------------------------------------------------------------------------------------------------
// Inner Classes
//----------------------------------------------------------------------------------------------------------------------

    private static class Accumulator implements BiConsumer<List<PassengerGroup>, String> {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

        private PassengerGroup currentGroup = new PassengerGroup();

//----------------------------------------------------------------------------------------------------------------------
// BiConsumer Implementation
//----------------------------------------------------------------------------------------------------------------------


        @Override
        public void accept(List<PassengerGroup> passengerGroups, String memberAnswers) {
            if (isEmpty(memberAnswers)) {
                passengerGroups.add(currentGroup);
                currentGroup = new PassengerGroup();
            } else {
                currentGroup.addMemberAnswers(memberAnswers);
            }
        }
    }
}
