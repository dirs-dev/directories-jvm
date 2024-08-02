package dev.dirs.impl;

public final class MacOs {

  private MacOs() {}

  public static String applicationPath(String qualifier, String organization, String application) {
    StringBuilder buf = new StringBuilder(Math.max(Util.stringLength(qualifier) + Util.stringLength(organization) + Util.stringLength(application), 0));
    boolean qualPresent = !Util.isNullOrEmpty(qualifier);
    boolean orgPresent = !Util.isNullOrEmpty(organization);
    boolean appPresent = !Util.isNullOrEmpty(application);
    if (qualPresent) {
      buf.append(Util.trimLowercaseReplaceWhitespace(qualifier, "-", false));
      if (orgPresent || appPresent)
        buf.append('.');
    }
    if (orgPresent) {
      buf.append(Util.trimLowercaseReplaceWhitespace(organization, "-", false));
      if (appPresent)
        buf.append('.');
    }
    if (appPresent)
      buf.append(Util.trimLowercaseReplaceWhitespace(application, "-", false));
    return buf.toString();
  }
}
