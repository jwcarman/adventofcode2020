package adventofcode;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import adventofcode.io.Input;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static com.google.common.collect.Sets.combinations;


public class Day1 {

    private static final String INPUT = readResource("Day1.txt");


    @Test
    void part1() {
        final Set<Integer> numbers = new HashSet<>(Input.readLines(new StringReader(INPUT), Integer::parseInt));
        findSum2020AndMultiply(combinations(numbers, 2)).ifPresent(System.out::println);
    }

    @Test
    void part2() {
        final Set<Integer> numbers = new HashSet<>(Input.readLines(new StringReader(INPUT), Integer::parseInt));
        findSum2020AndMultiply(combinations(numbers, 3)).ifPresent(System.out::println);
    }

    private Optional<Integer> findSum2020AndMultiply(Set<Set<Integer>> combinations) {
        return combinations.stream()
                .filter(sumTo2020())
                .findFirst()
                .map(multiply());
    }

    private Function<Set<Integer>, Integer> multiply() {
        return c -> c.stream().reduce(1, (a, b) -> a * b);
    }

    private Predicate<Set<Integer>> sumTo2020() {
        return c -> c.stream().mapToInt(i -> i).sum() == 2020;
    }
}
