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

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import lombok.Data;

@Data
public class Passport {

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

    public interface Strict {

    }

    public interface StrictPrimary {

    }

    @GroupSequence({Default.class, StrictPrimary.class, Strict.class})
    public interface StrictGroup {

    }
}
