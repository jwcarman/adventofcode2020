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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.allergen.AllergenScanner;
import adventofcode.allergen.Food;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readLines;
import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day21Test {

    private static final String INPUT = readResource("Day21.txt");

    private static final String EXAMPLE_INPUT = """
            mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
            trh fvjkl sbzzf mxmxvkd (contains dairy)
            sqjhc fvjkl (contains soy)
            sqjhc mxmxvkd sbzzf (contains fish)""";

    @Test
    void part1() {
        log.info("Part One: {}", calculateAnswerPart1(INPUT));
    }

    @Test
    void part2() {
        log.info("Part Two: {}", calculateAnswerPart2(INPUT));
    }

    @Test
    void example1() {
        assertThat(calculateAnswerPart1(EXAMPLE_INPUT)).isEqualTo(5);
    }

    @Test
    void example2() {
        assertThat(calculateAnswerPart2(EXAMPLE_INPUT)).isEqualTo("mxmxvkd,sqjhc,fvjkl");
    }

    private List<Food> parseFoods(String input) {
        return readLines(input).stream()
                .map(Food::new)
                .collect(Collectors.toList());
    }

    private String calculateAnswerPart2(String input) {
        final List<Food> foods = parseFoods(input);
        final Map<String, String> ingredientsToAllergen = AllergenScanner.scanAllergens(foods);
        return ingredientsToAllergen.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(","));
    }

    private long calculateAnswerPart1(String input) {
        final List<Food> foods = parseFoods(input);
        final Map<String, String> ingredientsToAllergen = AllergenScanner.scanAllergens(foods);
        return foods.stream()
                .flatMap(food -> food.getIngredients().stream())
                .filter(ingredient -> !ingredientsToAllergen.containsKey(ingredient))
                .count();
    }
}
