package bts.KCamps.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Childhood {
    PRESCHOOLERS(3, 6, "Дошкільнята (3-6)", new ArrayList<>(Arrays.asList(3, 4, 5, 6))),
    PRIMARY_SCHOOL(7, 11, "Молодша школа (7-11)", new ArrayList<>(Arrays.asList(7, 8, 9, 10, 11))),
    HIGH_SCHOOL(12, 17, "Старша школа (12-17)", new ArrayList<>(Arrays.asList(12, 13, 14, 15, 16, 17))),
    ALL(3, 17, "Для усіх (3-17)", new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17)));

    private int minAge;
    private int maxAge;
    private String description;
    private List<Integer> range;

    Childhood(int min, int max, String description, List<Integer> range) {
        this.minAge = min;
        this.maxAge = max;
        this.description = description;
        this.range = range;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getRange() {
        return range;
    }

    public boolean isSuitable(int age) {
        return range.contains(age);
    }
}
