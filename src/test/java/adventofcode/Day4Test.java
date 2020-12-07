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

import adventofcode.io.Input;
import adventofcode.passport.Passport;
import adventofcode.passport.PassportAccumulator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static adventofcode.io.Input.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day4Test {

    private static final String INPUT = readResource("Day4.txt");
    private static final String EXAMPLE_INPUT1 = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
            byr:1937 iyr:2017 cid:147 hgt:183cm
                        
            iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
            hcl:#cfa07d byr:1929
                        
            hcl:#ae17e1 iyr:2013
            eyr:2024
            ecl:brn pid:760753108 byr:1931
            hgt:179cm
                        
            hcl:#cfa07d eyr:2025 pid:166559648
            iyr:2011 ecl:brn hgt:59in""";

    private static final String EXAMPLE_INPUT2 = """
            eyr:1972 cid:100
            hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926
                        
            iyr:2019
            hcl:#602927 eyr:1967 hgt:170cm
            ecl:grn pid:012533040 byr:1946
                        
            hcl:dab227 iyr:2012
            ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277
                        
            hgt:59cm ecl:zzz
            eyr:2038 hcl:74454a iyr:2023
            pid:3556412378 byr:2007
                        
            pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
            hcl:#623a2f
                        
            eyr:2029 ecl:blu cid:129 byr:1989
            iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm
                        
            hcl:#888785
            hgt:164cm byr:2001 iyr:2015 cid:88
            pid:545766238 ecl:hzl
            eyr:2022
                        
            iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719""";

    @Test
    void part1() {
        final List<Passport> passports = parsePassports(INPUT);
        final long count = passports.stream()
                .filter(Passport::isValidPart1)
                .count();
        log.info("Part One: {}", count);
    }

    @Test
    void part2() {
        final List<Passport> passports = parsePassports(INPUT);
        final long count = passports.stream()
                .filter(Passport::isValidPart2)
                .count();
        log.info("Part Two: {}", count);
    }

    @Test
    void example1() {
        final List<Passport> passports = parsePassports(EXAMPLE_INPUT1);
        final long count = passports.stream()
                .filter(Passport::isValidPart1)
                .count();

        assertThat(count).isEqualTo(2);

    }

    @Test
    void example2() {
        final List<Passport> passports = parsePassports(EXAMPLE_INPUT2);
        final long count = passports.stream()
                .filter(Passport::isValidPart2)
                .count();

        assertThat(count).isEqualTo(4);

    }

    private List<Passport> parsePassports(String input) {
        final List<String> lines = Input.readLines(input);
        lines.add("");

        return lines.stream().reduce(new LinkedList<>(), new PassportAccumulator(), (left, right) -> null);
    }


}
