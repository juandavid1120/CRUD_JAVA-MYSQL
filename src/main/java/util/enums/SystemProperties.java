package util.enums;

public enum SystemProperties {
    USER_DIR(System.getProperty("user.dir"));

    private final String value;

    SystemProperties(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
