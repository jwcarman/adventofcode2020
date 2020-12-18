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

package adventofcode.math;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Expression evaluator which uses
 * <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm#The_algorithm_in_detail">Dijkstra's Shunting-Yard</a>
 * algorithm.
 */
@RequiredArgsConstructor
@Slf4j
public class ExpressionEvaluator {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final String LEFT_PAREN = "(";
    public static final String RIGHT_PAREN = ")";
    public static final String ADD = "+";
    public static final String MULTIPLY = "*";


    private final Function<String, Integer> orderOfOperations;

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private static void pushEvaluation(LinkedList<Long> valueStack, LinkedList<String> operatorStack) {
        final String operator = operatorStack.pop();
        final Long right = valueStack.pop();
        final Long left = valueStack.pop();
        final long value = switch (operator) {
            case ADD -> left + right;
            case MULTIPLY -> left * right;
            default -> throw new UnsupportedOperationException("Operator " + operator + " not supported.");
        };
        log.debug("Pushing {} {} {} = {}", left, operator, right, value);
        valueStack.push(value);
    }

    public long evaluate(String expression) {
        final LinkedList<Long> valueStack = new LinkedList<>();
        final LinkedList<String> operatorStack = new LinkedList<>();
        Scanner scanner = new Scanner(cleanInput(expression));
        while (scanner.hasNext()) {
            final String token = scanner.next();
            log.debug("Evaluating token: \"{}\"", token);
            switch (token) {
                case LEFT_PAREN -> onLeftParam(operatorStack, token);
                case RIGHT_PAREN -> onRightParam(valueStack, operatorStack);
                case ADD, MULTIPLY -> onOperator(valueStack, operatorStack, token);
                default -> onNumber(valueStack, token);
            }
            log.debug("Value stack: {}", valueStack);
            log.debug("Operator stack: {}", operatorStack);
            log.debug("-------------------");
        }
        while (!operatorStack.isEmpty()) {
            pushEvaluation(valueStack, operatorStack);
        }
        log.debug("{} = {}", expression, valueStack.getFirst());
        return valueStack.removeFirst();
    }

    private String cleanInput(String input) {
        return input.replace(LEFT_PAREN, " ( ").replace(")", " ) ");
    }

    private void onLeftParam(LinkedList<String> operatorStack, String token) {
        operatorStack.push(token);
    }

    private void onRightParam(LinkedList<Long> valueStack, LinkedList<String> operatorStack) {
        while (!LEFT_PAREN.equals(operatorStack.getFirst())) {
            pushEvaluation(valueStack, operatorStack);
        }
        operatorStack.pop();
    }

    private void onOperator(LinkedList<Long> valueStack, LinkedList<String> operatorStack, String token) {
        while (!operatorStack.isEmpty() &&
                !LEFT_PAREN.equals(operatorStack.getFirst()) &&
                orderOfOperations.apply(operatorStack.getFirst()) >= orderOfOperations.apply(token)) {
            pushEvaluation(valueStack, operatorStack);
        }
        operatorStack.push(token);
    }

    private void onNumber(LinkedList<Long> valueStack, String token) {
        valueStack.push(Long.parseLong(token));
    }
}
