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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TicketScanner {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final List<TicketField> fields;
    private final Map<Integer, Set<TicketField>> validFields = new HashMap<>();
    private long errorRate = 0L;

//----------------------------------------------------------------------------------------------------------------------
// Getters/Setters
//----------------------------------------------------------------------------------------------------------------------

    public long getErrorRate() {
        return errorRate;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public Map<String, Integer> extractFields(Ticket ticket) {
        final Map<Integer, TicketField> fieldMapping = calculateFieldMapping();
        return fieldMapping.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getValue().getName(), entry -> ticket.getFieldValues().get(entry.getKey())));
    }

    private Map<Integer, TicketField> calculateFieldMapping() {
        final Map<Integer, TicketField> fieldMapping = new HashMap<>();
        while (fieldMapping.size() != validFields.size()) {
            for (Map.Entry<Integer, Set<TicketField>> entry : validFields.entrySet()) {
                if (entry.getValue().size() == 1) {
                    fieldMapping.put(entry.getKey(), entry.getValue().iterator().next());
                } else {
                    entry.getValue().removeAll(fieldMapping.values());
                }
            }
        }
        return fieldMapping;
    }

    public void scan(Ticket ticket) {
        long ticketErrorRate = 0L;
        final List<Set<TicketField>> matchingFieldsPerValue = new LinkedList<>();
        for (Integer value : ticket.getFieldValues()) {
            final Set<TicketField> matchingFields = findMatchingFields(value);
            if (matchingFields.isEmpty()) {
                ticketErrorRate += value;
            } else {
                matchingFieldsPerValue.add(matchingFields);
            }
        }
        if (ticketErrorRate == 0L) {
            for (int i = 0; i < matchingFieldsPerValue.size(); i++) {
                updateEligibleFields(i, matchingFieldsPerValue.get(i));
            }
        } else {
            errorRate += ticketErrorRate;
        }
    }

    private Set<TicketField> findMatchingFields(Integer value) {
        return fields.stream().filter(field -> field.matches(value)).collect(Collectors.toSet());
    }

    private void updateEligibleFields(Integer index, Set<TicketField> matchingFields) {
        validFields.computeIfAbsent(index, (i) -> new HashSet<>(fields)).retainAll(matchingFields);
    }
}
