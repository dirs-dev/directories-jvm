package io.github.soc.directories;

final class Util {
	private Util() {
		throw new Error();
	}

	static String defaultIfNullOrEmpty(String value, String fallbackValue) {
		if (value == null || value.isEmpty()) return fallbackValue;
		else return value;
	}

  private static String runPowerShellCommand(String argument) {
    ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command",
        "[Environment]::GetFolderPath([Environment+SpecialFolder]::" + argument + ")");
    Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e1) {
      e1.printStackTrace();
      return null;
    }
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      return reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
