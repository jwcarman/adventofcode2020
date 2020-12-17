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

package adventofcode;

import java.util.Map;

import adventofcode.ticket.TicketNotes;
import adventofcode.ticket.TicketScanner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day16Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day16.txt");

    private static final String EXAMPLE_INPUT1 = """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50
                        
            your ticket:
            7,1,14
                        
            nearby tickets:
            7,3,47
            40,4,50
            55,2,20
            38,6,12""";

    private static final String EXAMPLE_INPUT2 = """
            class: 0-1 or 4-19
            row: 0-5 or 8-19
            seat: 0-13 or 16-19
                        
            your ticket:
            11,12,13
                        
            nearby tickets:
            3,9,18
            15,1,5
            5,14,9""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT1)).isEqualTo(71L);
    }

    @Test
    void example2() {
        final Map<String, Integer> fields = extractTicketFields(EXAMPLE_INPUT2);
        assertThat(fields.get("class")).isEqualTo(12);
        assertThat(fields.get("row")).isEqualTo(11);
        assertThat(fields.get("seat")).isEqualTo(13);
    }

    private Map<String, Integer> extractTicketFields(String input) {
        final TicketNotes notes = parseNotes(input);
        final TicketScanner scanner = new TicketScanner(notes.getFields());
        notes.getNearbyTickets().forEach(scanner::scan);
        return scanner.extractFields(notes.getYourTicket());
    }

    private TicketNotes parseNotes(String input) {
        return new TicketNotes(input);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private long calculateAnswerPart1(String input) {
        final TicketNotes notes = parseNotes(input);
        final TicketScanner scanner = new TicketScanner(notes.getFields());
        notes.getNearbyTickets().forEach(scanner::scan);
        return scanner.getErrorRate();
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private Long calculateAnswerPart2(String input) {
        final Map<String, Integer> extractedFields = extractTicketFields(input);

        final Long product = extractedFields.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("departure"))
                .mapToLong(Map.Entry::getValue)
                .reduce(1L, (a, b) -> a * b);
        return product;
    }
}
