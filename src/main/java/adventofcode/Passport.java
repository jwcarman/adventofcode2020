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
