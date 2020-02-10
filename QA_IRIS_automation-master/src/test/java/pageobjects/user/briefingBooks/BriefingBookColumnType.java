package pageobjects.user.briefingBooks;

/**
 * Created by noelc on 2016-11-24.
 */
public enum  BriefingBookColumnType {
    TITLE("Title"),
    AUTHOR("Author"),
    CREATED("Created"),
    LAST_UPDATED("Last Update");

    private final String columnName;

    BriefingBookColumnType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
