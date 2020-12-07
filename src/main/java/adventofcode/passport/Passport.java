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

package adventofcode.passport;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import adventofcode.io.InputException;
import adventofcode.passport.validation.Height;
import adventofcode.passport.validation.Part2Group;
import adventofcode.passport.validation.Strict;
import adventofcode.passport.validation.StrictPrimary;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

@Data
@Slf4j
public class Passport {

    public static List<Passport> parseFromInput(List<String> lines) {
        lines.add("");
        return lines.stream().collect(LinkedList::new, new Accumulator(), List::addAll);
    }

    @NotNull
    @Min(value = 1920, groups = StrictPrimary.class)
    @Max(value = 2002, groups = StrictPrimary.class)
    private Integer byr;

    @NotNull
    @Min(value = 2010, groups = StrictPrimary.class)
    @Max(value = 2020, groups = StrictPrimary.class)
    private Integer iyr;

    @NotNull
    @Min(value = 2020, groups = StrictPrimary.class)
    @Max(value = 2030, groups = StrictPrimary.class)
    private Integer eyr;

    @NotEmpty
    @Pattern(regexp = "\\d+(cm|in)", groups = StrictPrimary.class)
    @Height(groups = Strict.class)
    private String hgt;

    @NotEmpty
    @Pattern(regexp = "#[0-9a-f]{6}", groups = StrictPrimary.class)
    private String hcl;

    @NotEmpty
    @Pattern(regexp = "amb|blu|brn|gry|grn|hzl|oth", groups = StrictPrimary.class)
    private String ecl;

    @NotEmpty
    @Pattern(regexp = "\\d{9}", groups = StrictPrimary.class)
    private String pid;

    private String cid;

    public boolean isValidPart1() {
        return isValid(Default.class);
    }

    public boolean isValidPart2() {
        return isValid(Part2Group.class);
    }

    private boolean isValid(Class... groups) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<Passport>> errors = validator.validate(this, groups);
        if (!errors.isEmpty()) {
            log.debug("Invalid Passport: {}", this);
            errors.forEach(error -> {
                log.debug("{}: {}", error.getPropertyPath(), error.getMessage());
            });
            log.debug("--------------------------------------------------");
            return false;
        }
        return true;
    }

    private static class Accumulator implements BiConsumer<List<Passport>, String> {

        private Passport passport = new Passport();

        @Override
        public void accept(List<Passport> passports, String line) {
            if (StringUtils.isEmpty(line)) {
                passports.add(passport);
                passport = new Passport();
            } else {
                copyPropertiesFromLine(passport, line);
            }
        }

        private void copyPropertiesFromLine(Passport passport, String line) {
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
}
