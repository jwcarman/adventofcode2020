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

package adventofcode.password;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
@Builder
public class PasswordPolicyLine {
    int first;
    int second;
    char letter;
    String password;

    public boolean isValidPart1() {
        final int matches = StringUtils.countMatches(password, letter);
        return matches >= first && matches <= second;
    }

    public boolean isValidPart2() {
        return password.charAt(first - 1) == letter ^ password.charAt(second - 1) == letter;
    }
}
