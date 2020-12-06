package adventofcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;

public class Day6 {

    private static final String INPUT = readResource("Day6.txt");

    private static final String EXAMPLE = """
            abc
                        
            a
            b
            c
                        
            ab
            ac
                        
            a
            a
            a
            a
                        
            b""";
    @Test
    void part1() {
        final Set<Integer> answers = new HashSet<>();
        int count = 0;
        final List<String> lines = readLines(INPUT);
        lines.add("");
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                count += answers.size();
                answers.clear();
            } else {
                line.chars().forEach(answers::add);
            }
        }
        System.out.println(count);
    }

    private long countUnanimous(Multiset<Integer> answers,final int groupSize) {
        return answers.elementSet().stream()
                .filter(i -> answers.count(i) == groupSize)
                .count();
    }
    @Test
    void part2() {
        Multiset<Integer> answers = HashMultiset.create(26);
        int groupSize = 0;
        final List<String> lines = readLines(INPUT);
        lines.add("");
        int count = 0;
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                count += countUnanimous(answers, groupSize);
                answers.clear();
                groupSize = 0;
            } else {
                line.chars().forEach(answers::add);
                groupSize++;
            }
        }
        System.out.println(count);
    }
}
