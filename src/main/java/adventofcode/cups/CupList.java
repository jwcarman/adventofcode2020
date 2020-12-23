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

import java.util.ArrayList;
import java.util.List;

public class CupList {
    private final CupListNode[] index;
    private CupListNode head;
    private CupListNode tail;

    public CupList(int size) {
        this.index = new CupListNode[size + 1];
    }

    public void add(int cup) {
        final CupListNode node = getOrCreateNode(cup);
        if (head == null) {
            node.link(node);
            head = node;
            tail = node;
        } else {
            tail.link(node);
            node.link(head);
            tail = node;
        }

    }

    private CupListNode getOrCreateNode(int cup) {
        CupListNode node = this.index[cup];
        if (node == null) {
            node = new CupListNode(cup);
            this.index[cup] = node;
        }
        return node;
    }

    public int cycle() {
        tail = tail.next();
        head = head.next();
        return tail.getCup();
    }

    public List<Integer> takeN(int n) {
        final List<Integer> cups = new ArrayList<>(n);
        while (cups.size() < n) {
            cups.add(head.getCup());
            head = head.next();
            tail.link(head);
        }
        return cups;
    }

    public List<Integer> collectN(int n, int cup) {
        CupListNode node = getOrCreateNode(cup);
        final List<Integer> cups = new ArrayList<>(n);
        while (cups.size() < n) {
            node = node.next();
            cups.add(node.getCup());
        }
        return cups;
    }

    public void insertAfter(int destination, List<Integer> cups) {
        CupListNode prev = getOrCreateNode(destination);
        for (Integer cup : cups) {
            final CupListNode curr = getOrCreateNode(cup);
            curr.link(prev.next());
            prev.link(curr);
            if (tail == prev) {
                tail = curr;
            }
            prev = curr;
        }
    }


}
