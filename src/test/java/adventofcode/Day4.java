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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import adventofcode.io.Input;
import adventofcode.io.InputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;

@Slf4j
public class Day4 {

    private static final String INPUT = readResource("Day4.txt");

    private Validator validator;

    @Test
    void part1() {
        final List<Passport> passports = parsePassports(INPUT);
        final long count = passports.stream()
                .filter(this::isValid)
                .count();

        System.out.println(count);
    }

    private boolean isBlank(String line) {
        return "".equals(line);
    }

    private List<Passport> parsePassports(String input) {
        final List<String> lines = Input.readLines(input);
        final List<Passport> passports = new LinkedList<>();
        Passport passport = new Passport();
        for (String line : lines) {
            if (isBlank(line)) {
                passports.add(passport);
                passport = new Passport();
            } else {
                final String[] splits = line.split("\\s+");
                for (String split : splits) {
                    final String[] nameValue = split.split(":");
                    try {
                        BeanUtils.setProperty(passport, nameValue[0], nameValue[1]);
                    } catch (ReflectiveOperationException e) {
                        throw new InputException(e, "Unable to set Passport property '%s' to value '%s'.", nameValue[0], nameValue[1]);
                    }
                }
            }
        }
        passports.add(passport);
        return passports;
    }

    @BeforeEach
    void initValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private boolean isValid(Passport bean, Class... groups) {
        final Set<ConstraintViolation<Passport>> errors = validator.validate(bean, groups);
        if (!errors.isEmpty()) {
            log.info("Invalid Passport: {}", bean);
            errors.forEach(error -> {
                log.info("{}: {}", error.getPropertyPath(), error.getMessage());
            });
            log.info("--------------------------------------------------");
            return false;
        }
        return true;
    }

    private boolean isStrictlyValid(Passport bean) {
        return isValid(bean, Passport.StrictGroup.class);
    }

    @Test
    void part2() {
        final List<Passport> passports = parsePassports(INPUT);
        final long count = passports.stream()
                .filter(this::isStrictlyValid)
                .count();

        System.out.println(count);
    }
}
