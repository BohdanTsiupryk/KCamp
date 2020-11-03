package bts.KCamps.enums;

public enum Location {
    NEAR_SEA("Біля моря"),
    FOREST("У лісі"),
    LAKE("Озера"),
    NATIONAL_PARKS("Національний парк");

    private String description;

    Location(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Location getByDescription(String name) {
        for (Location inter : Location.values()) {
            if (inter.description.equals(name)) {
                return inter;
            }
        }
        return null;
    }

    public static Location getByName(String name) {
        for (Location inter : Location.values()) {
            if (inter.name().equals(name)) {
                return inter;
            }
        }
        return null;
    }
}
