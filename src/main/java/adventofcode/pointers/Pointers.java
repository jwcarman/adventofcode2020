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

package adventofcode.pointers;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class Pointers {

    /**
     * Performs the
     * <a href="https://www.geeksforgeeks.org/find-a-triplet-that-sum-to-a-given-value/">Three Pointers Technique</a>
     * to find a triple of numbers from the given sorted list that add up to a target sum.
     *
     * @param sorted    the sorted list of numbers
     * @param targetSum the target sum
     * @return an optional containing the triple of indices for the numbers
     */
    public static Optional<Triple<Integer, Integer, Integer>> threePointers(List<Integer> sorted, int targetSum) {
        for (int fixed = 0; fixed < sorted.size() - 2; ++fixed) {
            final Optional<Pair<Integer, Integer>> pair = twoPointers(sorted, targetSum - sorted.get(fixed), fixed + 1, sorted.size() - 1);
            if (pair.isPresent()) {
                return Optional.of(new ImmutableTriple<>(fixed, pair.get().getLeft(), pair.get().getRight()));
            }
        }
        return Optional.empty();
    }

    /**
     * Performs the <a href="https://www.geeksforgeeks.org/two-pointers-technique/">Two Pointers Technique</a> to find
     * a pair of numbers from the given sorted list that add up to a target sum.
     *
     * @param sorted    the sorted list of numbers
     * @param targetSum the target sum
     * @return an optional containing the pair of indices for the numbers
     */
    public static Optional<Pair<Integer, Integer>> twoPointers(List<Integer> sorted, int targetSum) {
        return twoPointers(sorted, targetSum, 0, sorted.size() - 1);
    }

    private static Optional<Pair<Integer, Integer>> twoPointers(List<Integer> numbers, int targetSum, int left, int right) {
        while (left != right) {
            final int sum = numbers.get(left) + numbers.get(right);
            if (sum > targetSum) {
                right--;
            } else if (sum < targetSum) {
                left++;
            } else {
                return Optional.of(new ImmutablePair<>(left, right));
            }
        }
        return Optional.empty();
    }
}
