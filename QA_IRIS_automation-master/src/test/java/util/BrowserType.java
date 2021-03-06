package util;


public enum BrowserType {

  CHROME("chrome", "51"),
  FIREFOX("firefox", "45"),
  OPERA("opera", "12"),
  SAFARI("safari", "9"),
  IE("internet explorer", "11");

  private final String name;
  private final String latestVersion;

  BrowserType(String name, String latestVersion) {
    this.name = name;
    this.latestVersion = latestVersion;
  }

  public String getName() {
    return name;
  }

  public String getLatestVersion() {
    return latestVersion;
  }
}

