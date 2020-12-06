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

import java.util.List;
import java.util.function.BiFunction;

import adventofcode.io.InputException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

public class PassportAccumulator implements BiFunction<List<Passport>, String, List<Passport>> {

    private Passport passport = new Passport();

    @Override
    public List<Passport> apply(List<Passport> passports, String line) {
        if (StringUtils.isEmpty(line)) {
            passports.add(passport);
            passport = new Passport();
        } else {
            copyPropertiesFromLine(passport, line);
        }
        return passports;
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
