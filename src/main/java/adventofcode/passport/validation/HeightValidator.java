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

package adventofcode.passport.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeightValidator implements ConstraintValidator<Height, String> {

//----------------------------------------------------------------------------------------------------------------------
// ConstraintValidator Implementation
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final int height = Integer.parseInt(value.substring(0, value.length() - 2));
        if (value.endsWith("cm")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be between 150cm and 193cm").addConstraintViolation();
            return height >= 150 && height <= 193;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be between 59in and 76in").addConstraintViolation();
            return height >= 59 && height <= 76;
        }
    }
}
