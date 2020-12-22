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

import java.util.Arrays;
import java.util.List;

public class Food {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private List<String> ingredients;
    private List<String> allergens;

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public Food(String input) {
        final String[] splits = cleanInput(input).split("\\|");
        this.ingredients = Arrays.asList(splits[0].split("\\s+"));
        this.allergens = Arrays.asList(splits[1].split(","));
    }

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    private static String cleanInput(String input) {
        return input.replace(" (contains ", "|")
                .replace(")", "")
                .replace(", ", ",");
    }

//----------------------------------------------------------------------------------------------------------------------
// Getters/Setters
//----------------------------------------------------------------------------------------------------------------------

    public List<String> getAllergens() {
        return allergens;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
