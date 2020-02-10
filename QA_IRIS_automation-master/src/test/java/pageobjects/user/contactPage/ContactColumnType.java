package pageobjects.user.contactPage;

/**
 * Created by sarahr on 1/31/2017.
 */
public enum ContactColumnType {
    NAME("Name"),
    LOCATION("Location"),
    PHONE("Phone"),
    EMAIL("Email");

    private final String columnName;

    ContactColumnType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
