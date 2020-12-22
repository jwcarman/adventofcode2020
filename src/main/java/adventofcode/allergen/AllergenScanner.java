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

package adventofcode.allergen;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class AllergenScanner {

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static Map<String, String> scanAllergens(List<Food> foods) {
        final Multimap<String, String> mayContainAllergen = findIngredientsWhichMayContainAllergen(foods);
        return findIngredientsForAllergens(mayContainAllergen);
    }

    private static Multimap<String, String> findIngredientsWhichMayContainAllergen(List<Food> foods) {
        final Multimap<String, String> mayContainAllergen = HashMultimap.create();
        for (Food food : foods) {
            for (String allergen : food.getAllergens()) {
                if (!mayContainAllergen.containsKey(allergen)) {
                    mayContainAllergen.putAll(allergen, food.getIngredients());
                } else {
                    mayContainAllergen.get(allergen).retainAll(food.getIngredients());
                }
            }
        }
        return mayContainAllergen;
    }

    private static Map<String, String> findIngredientsForAllergens(Multimap<String, String> mayContainAllergen) {
        final Map<String, String> containsAllergen = new TreeMap<>();
        while (containsAllergen.size() < mayContainAllergen.keySet().size()) {
            for (String allergen : mayContainAllergen.keySet()) {
                final Collection<String> ingredients = mayContainAllergen.get(allergen);
                if (ingredients.size() == 1) {
                    containsAllergen.put(ingredients.iterator().next(), allergen);
                } else {
                    ingredients.removeAll(containsAllergen.keySet());
                }
            }
        }
        return containsAllergen;
    }
}
