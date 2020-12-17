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

package adventofcode.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.io.Resources;

public class Input {

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static List<String> readLines(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new InputException(e, "Unable to parse standard input.");
        }
    }

    public static List<String> readLines(String input) {
        return readLines(new StringReader(input));
    }

    public static <T> List<T> readLines(String input, Function<String, T> parser) {
        return readLines(new StringReader(input), parser);
    }

    public static <T> List<T> readLines(Reader stream, Function<String, T> parser) {
        return readLines(stream).stream().map(parser).collect(Collectors.toList());
    }

    public static String readResource(String name) {
        try {
            return Resources.toString(Resources.getResource(Input.class, "/" + name), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InputException(e, "Unable to read resource %s.", name);
        }
    }
}
