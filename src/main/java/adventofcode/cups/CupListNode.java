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

class CupListNode {
    private final int cup;
    private CupListNode next;

    public CupListNode(int cup) {
        this.cup = cup;
    }

    public CupListNode(int cup, CupListNode next) {
        this.cup = cup;
        this.next = next;
    }

    void link(CupListNode next) {
        this.next = next;
    }

    public CupListNode next() {
        return next;
    }

    public int getCup() {
        return cup;
    }
}
