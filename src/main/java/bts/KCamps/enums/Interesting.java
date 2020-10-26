package bts.KCamps.enums;

public enum Interesting{
    ENGLISH("Уроки Англійської"),
    POLISH("Уроки Польської"),
    GERMAN("Уроки Німецької"),
    MUSIC("Музика"),
    DANCE("Танці"),
    SPORT("Спрот"),
    EXCURSIONS("Екскурсії"),
    DESIGN_OF_CLOTHING("Дизайн одягу"),
    POOL("Басейн"),
    TENT_CAMP("Наметовий табір"),
    IT("ІТ-табір"),
    ART("Мистецтво"),
    HoUSE_RIDE("Верхова їзда");

    private String name;

    Interesting(String name) {
        this.name = name;
    }

    public static Interesting getByName(String name) {
        for (Interesting inter : Interesting.values()) {
            if (inter.name.equals(name)) {
                return inter;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
