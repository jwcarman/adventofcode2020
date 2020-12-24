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

package adventofcode.cups;

public class CupList {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final int[] successorOf;
    private int head;
    private int tail;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public CupList(int size) {
        this.successorOf = new int[size + 1];
        this.head = -1;
        this.tail = -1;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public void add(int cup) {
        if (head == -1) {
            head = cup;
            tail = cup;
            successorOf[cup] = cup;
        } else {
            successorOf[tail] = cup;
            successorOf[cup] = head;
            tail = cup;
        }
    }

    public int[] collectN(int n, int cup) {
        final int[] cups = new int[n];
        for (int i = 0; i < n; ++i) {
            cups[i] = successorOf[cup];
            cup = successorOf[cup];
        }
        return cups;
    }

    public int cycle() {
        head = successorOf[head];
        tail = successorOf[tail];
        return tail;
    }

    public void insertAfter(int destination, int[] cups) {
        int prev = destination;
        for (int curr : cups) {
            successorOf[curr] = successorOf[prev];
            successorOf[prev] = curr;
            if (tail == prev) {
                tail = curr;
            }
            prev = curr;
        }
    }

    public int[] takeN(int n) {
        final int[] cups = new int[n];
        int curr = head;
        for (int i = 0; i < n; ++i) {
            cups[i] = curr;
            curr = successorOf[curr];
        }
        head = curr;
        successorOf[tail] = head;
        return cups;
    }
}
