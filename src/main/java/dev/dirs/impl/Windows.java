package dev.dirs.impl;

import dev.dirs.Constants;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.ValueLayout.JAVA_BYTE;
import static java.lang.foreign.ValueLayout.JAVA_CHAR;

public final class Windows {

  private Windows() {}

  static {
    if (Constants.operatingSystem == 'w') {
      String sysdir = System.getenv("WINDIR") + "/system32/";
      System.load(sysdir + "combase.dll");
      System.load(sysdir + "ole32.dll");
      System.load(sysdir + "shell32.dll");
    }
  }

  private static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup().or(Linker.nativeLinker().defaultLookup());
  private static final ValueLayout.OfByte C_CHAR = ValueLayout.JAVA_BYTE;
  private static final ValueLayout.OfShort C_SHORT = ValueLayout.JAVA_SHORT;
  private static final AddressLayout C_POINTER = ValueLayout.ADDRESS
      .withTargetLayout(MemoryLayout.sequenceLayout(java.lang.Long.MAX_VALUE, JAVA_BYTE));
  private static final ValueLayout.OfInt C_LONG = ValueLayout.JAVA_INT;

  public static String getProfileDir() {
    return getDir("{5E6C858F-0E22-4760-9AFE-EA3317B67173}");
  }

  public static String getMusicDir() {
    return getDir("{4BD8D571-6D19-48D3-BE97-422220080E43}");
  }

  public static String getDesktopDir() {
    return getDir("{B4BFCC3A-DB2C-424C-B029-7FE99A87C641}");
  }

  public static String getDocumentsDir() {
    return getDir("{FDD39AD0-238F-46AF-ADB4-6C85480369C7}");
  }

  public static String getDownloadsDir() {
    return getDir("{374DE290-123F-4565-9164-39C4925E467B}");
  }

  public static String getPicturesDir() {
    return getDir("{33E28130-4E1E-4676-835A-98395C3BC3BB}");
  }

  public static String getPublicDir() {
    return getDir("{DFDF76A2-C82A-4D63-906A-5644AC457385}");
  }

  public static String getTemplatesDir() {
    return getDir("{A63293E8-664E-48DB-A079-DF759E0509F7}");
  }

  public static String getVideosDir() {
    return getDir("{18989B1D-99B5-455B-841C-AB7C74E4DDFC}");
  }

  public static String getRoamingAppDataDir() {
    return getDir("{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}");
  }

  public static String getLocalAppDataDir() {
    return getDir("{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}");
  }

  public static String applicationPath(String qualifier, String organization, String application) {
    StringBuilder buf = new StringBuilder(Math.max(Util.stringLength(organization) + Util.stringLength(application), 0));
    boolean orgPresent = !Util.isNullOrEmpty(organization);
    boolean appPresent = !Util.isNullOrEmpty(application);
    if (orgPresent) {
      buf.append(organization);
      if (appPresent)
        buf.append('\\');
    }
    if (appPresent)
      buf.append(application);
    return buf.toString();
  }

  private static String getDir(String folderId) {
    try (var arena = Arena.ofConfined()) {
      MemorySegment guidSegment = arena.allocate(GUID_LAYOUT);
      if (CLSIDFromString(createSegmentFromString(folderId, arena), guidSegment) != 0) {
        throw new AssertionError("failed converting string " + folderId + " to KnownFolderId");
      }
      MemorySegment path = arena.allocate(C_POINTER);
      SHGetKnownFolderPath(guidSegment, 0, MemorySegment.NULL, path);
      String stringFromSegment = createStringFromSegment(path.get(C_POINTER, 0));
      CoTaskMemFree(path);
      return stringFromSegment;
    }
  }

  /**
   * Creates a memory segment as a copy of a Java string.
   * <p>
   * The memory segment contains a copy of the string (null-terminated, UTF-16/wide characters).
   * </p>
   *
   * @param str   the string to copy
   * @param arena the arena for the memory segment
   * @return the resulting memory segment
   */
  private static MemorySegment createSegmentFromString(String str, Arena arena) {
    // allocate segment (including space for terminating null)
    var segment = arena.allocate(JAVA_CHAR, str.length() + 1L);
    // copy characters
    segment.copyFrom(MemorySegment.ofArray(str.toCharArray()));
    return segment;
  }

  /**
   * Creates a copy of the string in the memory segment.
   * <p>
   * The string must be a null-terminated UTF-16 (wide character) string.
   * </p>
   *
   * @param segment the memory segment
   * @return copied string
   */
  private static String createStringFromSegment(MemorySegment segment) {
    var len = 0;
    while (segment.get(JAVA_CHAR, len) != 0) {
      len += 2;
    }

    return new String(segment.asSlice(0, len).toArray(JAVA_CHAR));
  }

  private static MemorySegment findOrThrow(String symbol) {
    return SYMBOL_LOOKUP.find(symbol)
        .orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
  }

  private static final GroupLayout GUID_LAYOUT = MemoryLayout.structLayout(
          C_LONG.withName("Data1"),
          C_SHORT.withName("Data2"),
          C_SHORT.withName("Data3"),
          MemoryLayout.sequenceLayout(8, C_CHAR).withName("Data4"))
      .withName("_GUID");

  /**
   * {@snippet lang=c :
   * extern HRESULT CLSIDFromString(LPCOLESTR lpsz, LPCLSID pclsid)
   * }
   */
  private static int CLSIDFromString(MemorySegment lpsz, MemorySegment pclsid) {
    var handle = CLSIDFromString.HANDLE;
    try {
      return (int) handle.invokeExact(lpsz, pclsid);
    } catch (Throwable throwable) {
      throw new AssertionError("failed to invoke `CLSIDFromString`", throwable);
    }
  }

  private static class CLSIDFromString {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(C_LONG, C_POINTER, C_POINTER);

    public static final MethodHandle HANDLE = Linker.nativeLinker()
        .downcallHandle(findOrThrow("CLSIDFromString"), DESC);
  }

  /**
   * {@snippet lang=c :
   * extern HRESULT SHGetKnownFolderPath(const KNOWNFOLDERID *const rfid, DWORD dwFlags, HANDLE hToken, PWSTR *ppszPath)
   * }
   */
  private static int SHGetKnownFolderPath(MemorySegment rfid, int dwFlags, MemorySegment hToken, MemorySegment ppszPath) {
    var handle = SHGetKnownFolderPath.HANDLE;
    try {
      return (int) handle.invokeExact(rfid, dwFlags, hToken, ppszPath);
    } catch (Throwable throwable) {
      throw new AssertionError("failed to invoke `SHGetKnownFolderPath`", throwable);
    }
  }

  private static class SHGetKnownFolderPath {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(C_LONG, C_POINTER, C_LONG, C_POINTER, C_POINTER);

    public static final MethodHandle HANDLE = Linker.nativeLinker()
        .downcallHandle(findOrThrow("SHGetKnownFolderPath"), DESC);
  }

  /**
   * {@snippet lang=c :
   * extern void CoTaskMemFree(LPVOID pv)
   * }
   */
  private static void CoTaskMemFree(MemorySegment pv) {
    var handle = CoTaskMemFree.HANDLE;
    try {
      handle.invokeExact(pv);
    } catch (Throwable throwable) {
      throw new AssertionError("failed to invoke `CoTaskMemFree`", throwable);
    }
  }

  private static class CoTaskMemFree {
    public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(C_POINTER);

    public static final MethodHandle HANDLE = Linker.nativeLinker()
        .downcallHandle(findOrThrow("CoTaskMemFree"), DESC);
  }

}
