package bts.KCamps.enums;

public enum Location {
    NEAR_SEA("Біля моря"),
    FOREST("У лісі"),
    LAKE("Озера"),
    NATIONAL_PARKS("Національний парк");

    private String name;

    Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Location getByName(String name) {
        for (Location local : Location.values()) {
            if (local.name.equals(name)) {
                return local;
            }
        }
        return null;
    }
}
