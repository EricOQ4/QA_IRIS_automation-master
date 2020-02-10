package util;


public enum EnvironmentType {

  LOCAL("develop.q4desktop.com/"),
  DEVELOP("develop.q4desktop.com/"),
  STAGING("staging.q4desktop.com/"),
  BETA("beta.q4desktop.com/"),
  PRODUCTION("www.q4desktop.com/"),
  // Admin
  LOCALADMIN("admin-dev.q4inc.com/ "),
  DEVELOPADMIN("admin-dev.q4inc.com/ "),
  STAGINGADMIN("admin-stage.q4inc.com/");
  private final String host;
  private final String protocol = "https://";

  EnvironmentType(String host) {
    this.host = host;
  }

  public String getHost() {
    return host;
  }

  public String getProtocol() {
    return protocol;
  }
}

