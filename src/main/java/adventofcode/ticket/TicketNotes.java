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

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;

import static adventofcode.io.Input.readLines;

@Value
public class TicketNotes {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    List<TicketField> fields;

    Ticket yourTicket;

    List<Ticket> nearbyTickets;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public TicketNotes(String input) {
        final String[] splits = input.split("your ticket:|nearby tickets:");

        this.fields = readLines(splits[0].trim()).stream()
                .map(TicketField::new)
                .collect(Collectors.toList());

        this.yourTicket = new Ticket(splits[1].trim());

        this.nearbyTickets = readLines(splits[2].trim()).stream()
                .map(Ticket::new)
                .collect(Collectors.toList());
    }
}
