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

    public static List<String> readLines(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new InputException(e, "Unable to parse standard input.");
        }
    }

    public static String readResource(String name) {
        try {
            return Resources.toString(Resources.getResource(Input.class, "/" + name), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InputException(e, "Unable to read resource %s.", name);
        }
    }
    public static List<String> readLines(String input) {
        return readLines(new StringReader(input));
    }

    public static <T> List<T> readLines(Reader stream, Function<String, T> parser) {
        return readLines(stream).stream().map(parser).collect(Collectors.toList());
    }
}
