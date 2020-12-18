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

import java.util.function.Function;
import java.util.function.ToLongFunction;

import adventofcode.math.ExpressionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static adventofcode.math.ExpressionEvaluator.ADD;
import static adventofcode.math.ExpressionEvaluator.LEFT_PAREN;
import static adventofcode.math.ExpressionEvaluator.MULTIPLY;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day18Test {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String INPUT = readResource("Day18.txt");

    private static final String EXAMPLE_INPUT = """
            2 * 3 + (4 * 5)
            5 + (8 * 3 + 9 + 3 * 4 * 3)
            5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
            ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2""";

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private Long evaluateExpressionPart1(String input) {
        return evaluateExpression(input, operator -> switch (operator) {
            case ADD, MULTIPLY -> 1;
            case LEFT_PAREN -> 0;
            default -> -1;
        });
    }

    private Long evaluateExpression(String expression, Function<String, Integer> orderOfOperations) {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator(orderOfOperations);
        return evaluator.evaluate(expression);
    }

    private Long evaluateExpressionPart2(String input) {
        return evaluateExpression(input, operator -> switch (operator) {
            case ADD -> 2;
            case MULTIPLY -> 1;
            case LEFT_PAREN -> 0;
            default -> -1;
        });
    }

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(26335);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo(693891);
    }

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    private long calculateAnswerPart1(String input) {
        return evaluateInput(input, this::evaluateExpressionPart1);
    }

    private long evaluateInput(String input, ToLongFunction<String> evaluator) {
        return readLines(input).stream()
                .mapToLong(evaluator)
                .sum();
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    private long calculateAnswerPart2(String input) {
        return evaluateInput(input, this::evaluateExpressionPart2);
    }
}
