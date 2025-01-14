package api.dashboard.insurance.system.model.enums;

public enum Month {
    JANUARY("JANUARY"),
    FEBRUARY("FEBRUARY"),
    MARCH("MARCH"),
    APRIL("APRIL"),
    MAY("MAY"),
    JUNE("JUNE"),
    JULY("JULY"),
    AUGUST("AUGUST"),
    SEPTEMBER("SEPTEMBER"),
    OCTOBER("OCTOBER"),
    NOVEMBER("NOVEMBER"),
    DECEMBER("DECEMBER"),
    UNKNOWN("UNKNOWN");

    private final String value;

    Month(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Month fromString(String text) {
        for (Month month : Month.values()) {
            if (month.value.equalsIgnoreCase(text)) {
                return month;
            }
        }
        return UNKNOWN;
    }
}
