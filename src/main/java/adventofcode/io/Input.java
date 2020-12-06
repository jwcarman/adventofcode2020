package adventofcode.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Input {

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

    public static <T> List<T> readLines(Reader stream, Function<String, T> parser) {
        return readLines(stream).stream().map(parser).collect(Collectors.toList());
    }
}
