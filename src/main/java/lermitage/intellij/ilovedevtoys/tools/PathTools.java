package lermitage.intellij.ilovedevtoys.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts a filesystem path between the textual formats used by Windows, Git Bash, WSL and Unix/macOS.
 * <p>
 * The input format is auto-detected, normalized into a {@link ParsedPath} that no longer depends on any
 * particular spelling, then rendered once per output format. Two detection rules are worth knowing:
 * <ul>
 *     <li>{@code /c/foo} is read as a Git Bash path, not as a Unix directory named {@code /c}. The two are
 *     indistinguishable from the string alone, and Git Bash is what this tool exists for.</li>
 *     <li>A user home directory is recognized in any format ({@code ~/foo}, {@code /Users/yoda/foo},
 *     {@code /home/yoda/foo}, {@code C:\Users\yoda\foo}) and re-rendered per format. When rendering a Unix
 *     path, a home that is not the current user's is spelled {@code /Users/<name>}, so a Linux
 *     {@code /home/<name>} belonging to somebody else normalizes to the macOS spelling.</li>
 * </ul>
 */
@SuppressWarnings("DuplicatedCode") // Code is cleaner with a few duplicated lines in path checks
public class PathTools {

    /** Drive letter used when the input carries none and no better default is available. */
    public static final String DEFAULT_DRIVE_LETTER = "C";

    /** Folder holding user home directories on Windows, and the macOS spelling used for Unix output. */
    private static final String USERS_FOLDER = "Users";

    private static final Pattern WINDOWS_PATH = Pattern.compile("^([A-Za-z]):[\\\\/]?(.*)$");
    private static final Pattern UNC_PATH = Pattern.compile("^(?:\\\\\\\\|//)(.+)$");
    private static final Pattern WSL_PATH = Pattern.compile("^/mnt/([A-Za-z])(?:/(.*))?$");
    private static final Pattern GIT_BASH_PATH = Pattern.compile("^/([A-Za-z])(?:/(.*))?$");
    private static final Pattern HOME_PATH = Pattern.compile("^~(?:/(.*))?$");
    private static final Pattern UNIX_HOME_PATH = Pattern.compile("^/(?:Users|home)/([^/]+)(?:/(.*))?$", Pattern.CASE_INSENSITIVE);
    private static final Pattern DRIVE_LETTER = Pattern.compile("^([A-Za-z]):");
    private static final Pattern SEPARATORS = Pattern.compile("[\\\\/]+");

    /**
     * A path stripped of its original spelling.
     *
     * @param driveLetter  upper case drive letter carried by the input, or {@code null} if it carried none
     * @param absolute     whether the path is rooted
     * @param homeRelative whether {@code segments} are relative to a user home directory
     * @param homeUser     owner of that home directory, or {@code null} when the input just said {@code ~}
     * @param unc          whether the path is a UNC / network share, in which case no drive letter applies
     * @param segments     path elements, never empty strings, never containing a separator
     */
    public record ParsedPath(
        String driveLetter,
        boolean absolute,
        boolean homeRelative,
        String homeUser,
        boolean unc,
        List<String> segments) {
    }

    /**
     * One input path rendered in every supported format.
     *
     * @param driveLetter the drive letter actually used, which is the one carried by the input when it had one
     */
    public record ConvertedPaths(
        String windows,
        String windowsAlt,
        String gitBash,
        String wsl,
        String unix,
        String driveLetter) {
    }

    /** Converts {@code input} to every supported format, expanding {@code ~} with the current OS user. */
    public static ConvertedPaths convert(String input, String fallbackDriveLetter) {
        return convert(input, fallbackDriveLetter, System.getProperty("user.name"));
    }

    /**
     * Converts {@code input} to every supported format.
     *
     * @param fallbackDriveLetter drive letter to use when the input carries none; invalid values fall back
     *                            to {@link #DEFAULT_DRIVE_LETTER}
     * @param currentUser         user whose home {@code ~} refers to
     */
    public static ConvertedPaths convert(String input, String fallbackDriveLetter, String currentUser) {
        try {
            ParsedPath path = parse(input);
            String driveLetter = effectiveDriveLetter(path, fallbackDriveLetter);
            return new ConvertedPaths(
                toWindows(path, driveLetter, currentUser),
                toWindowsAlt(path, driveLetter, currentUser),
                toGitBash(path, driveLetter, currentUser),
                toWsl(path, driveLetter, currentUser),
                toUnix(path, currentUser),
                driveLetter);
        } catch (Exception e) {
            String error = "Error: " + e.getMessage();
            return new ConvertedPaths(error, error, error, error, error, normalizeDriveLetter(fallbackDriveLetter));
        }
    }

    /** Detects the format of {@code input} and normalizes it. A blank input yields an empty path. */
    public static ParsedPath parse(String input) {
        String trimmed = input == null ? "" : input.trim();
        if (trimmed.isEmpty()) {
            return new ParsedPath(null, false, false, null, false, List.of());
        }

        Matcher windows = WINDOWS_PATH.matcher(trimmed);
        if (windows.matches()) {
            return detectWindowsHome(upperCaseDrive(windows.group(1)), splitSegments(windows.group(2)));
        }
        Matcher unc = UNC_PATH.matcher(trimmed);
        if (unc.matches()) {
            return new ParsedPath(null, true, false, null, true, splitSegments(unc.group(1)));
        }
        Matcher wsl = WSL_PATH.matcher(trimmed);
        if (wsl.matches()) {
            return detectWindowsHome(upperCaseDrive(wsl.group(1)), splitSegments(wsl.group(2)));
        }
        Matcher gitBash = GIT_BASH_PATH.matcher(trimmed);
        if (gitBash.matches()) {
            return detectWindowsHome(upperCaseDrive(gitBash.group(1)), splitSegments(gitBash.group(2)));
        }
        Matcher home = HOME_PATH.matcher(trimmed);
        if (home.matches()) {
            return new ParsedPath(null, true, true, null, false, splitSegments(home.group(1)));
        }
        Matcher unixHome = UNIX_HOME_PATH.matcher(trimmed);
        if (unixHome.matches()) {
            return new ParsedPath(null, true, true, unixHome.group(1), false, splitSegments(unixHome.group(2)));
        }

        boolean absolute = trimmed.startsWith("/") || trimmed.startsWith("\\");
        return new ParsedPath(null, absolute, false, null, false, splitSegments(trimmed));
    }

    /** Renders the Windows form, for example {@code C:\foo\bar}. */
    public static String toWindows(ParsedPath path, String driveLetter, String currentUser) {
        if (isEmpty(path)) {
            return "";
        }
        if (path.unc()) {
            return "\\\\" + join(path.segments(), "\\");
        }
        List<String> segments = expandHome(path, currentUser);
        if (!path.absolute()) {
            return join(segments, "\\");
        }
        return effectiveDriveLetter(path, driveLetter) + ":\\" + join(segments, "\\");
    }

    /** Renders the Windows form with forward slashes, for example {@code C:/foo/bar}. Windows accepts it too. */
    public static String toWindowsAlt(ParsedPath path, String driveLetter, String currentUser) {
        if (isEmpty(path)) {
            return "";
        }
        if (path.unc()) {
            return "//" + join(path.segments(), "/");
        }
        List<String> segments = expandHome(path, currentUser);
        if (!path.absolute()) {
            return join(segments, "/");
        }
        return effectiveDriveLetter(path, driveLetter) + ":/" + join(segments, "/");
    }

    /** Renders the Git Bash form, for example {@code /c/foo/bar}. */
    public static String toGitBash(ParsedPath path, String driveLetter, String currentUser) {
        return toSlashRootedPath(path, driveLetter, currentUser, "/");
    }

    /** Renders the WSL form, for example {@code /mnt/c/foo/bar}. */
    public static String toWsl(ParsedPath path, String driveLetter, String currentUser) {
        return toSlashRootedPath(path, driveLetter, currentUser, "/mnt/");
    }

    /**
     * Renders the Unix/macOS form, for example {@code /foo/bar}. A path inside the current user's home is
     * spelled {@code ~/foo/bar}; a path inside somebody else's home keeps their name.
     */
    public static String toUnix(ParsedPath path, String currentUser) {
        if (isEmpty(path)) {
            return "";
        }
        if (path.unc()) {
            return "//" + join(path.segments(), "/");
        }
        if (!path.absolute()) {
            return join(path.segments(), "/");
        }
        if (path.homeRelative()) {
            String suffix = path.segments().isEmpty() ? "" : "/" + join(path.segments(), "/");
            boolean ownHome = path.homeUser() == null || path.homeUser().equalsIgnoreCase(currentUser);
            return ownHome ? "~" + suffix : "/" + USERS_FOLDER + "/" + path.homeUser() + suffix;
        }
        return "/" + join(path.segments(), "/");
    }

    /** Wraps {@code path} in double quotes, so a path containing spaces can be pasted into a shell as is. */
    public static String quote(String path) {
        if (path == null || path.isBlank()) {
            return "";
        }
        return "\"" + path + "\"";
    }

    /**
     * Picks the drive letter to offer by default: the one the project lives on, else the one the user home
     * lives on, else {@link #DEFAULT_DRIVE_LETTER}. Both sources are absent on Unix and macOS.
     *
     * @param projectBasePath path of the current project, which may be {@code null} or a UNC path
     */
    public static String defaultDriveLetter(String projectBasePath) {
        String fromProject = extractDriveLetter(projectBasePath);
        if (fromProject != null) {
            return fromProject;
        }
        String fromUserHome = extractDriveLetter(System.getProperty("user.home"));
        return fromUserHome == null ? DEFAULT_DRIVE_LETTER : fromUserHome;
    }

    /** Returns the upper case drive letter {@code path} starts with, or {@code null} if it has none. */
    public static String extractDriveLetter(String path) {
        if (path == null) {
            return null;
        }
        Matcher matcher = DRIVE_LETTER.matcher(path.trim());
        return matcher.find() ? upperCaseDrive(matcher.group(1)) : null;
    }

    private static String toSlashRootedPath(ParsedPath path, String driveLetter, String currentUser, String root) {
        if (isEmpty(path)) {
            return "";
        }
        if (path.unc()) {
            return "//" + join(path.segments(), "/");
        }
        List<String> segments = expandHome(path, currentUser);
        if (!path.absolute()) {
            return join(segments, "/");
        }
        String drive = effectiveDriveLetter(path, driveLetter).toLowerCase(Locale.ROOT);
        return root + drive + "/" + join(segments, "/");
    }

    /**
     * Flags a Windows path as being home relative when it sits under {@code Users\<name>}, so that the Unix
     * rendering can collapse it back to {@code ~}.
     */
    private static ParsedPath detectWindowsHome(String driveLetter, List<String> segments) {
        if (segments.size() >= 2 && USERS_FOLDER.equalsIgnoreCase(segments.get(0))) {
            return new ParsedPath(driveLetter, true, true, segments.get(1), false,
                List.copyOf(segments.subList(2, segments.size())));
        }
        return new ParsedPath(driveLetter, true, false, null, false, segments);
    }

    /** Prepends {@code Users\<name>} to a home relative path, so it can be rendered as a full path. */
    private static List<String> expandHome(ParsedPath path, String currentUser) {
        if (!path.homeRelative()) {
            return path.segments();
        }
        String user = path.homeUser() == null ? currentUser : path.homeUser();
        List<String> expanded = new ArrayList<>();
        expanded.add(USERS_FOLDER);
        expanded.add(user == null || user.isBlank() ? "user" : user);
        expanded.addAll(path.segments());
        return expanded;
    }

    private static boolean isEmpty(ParsedPath path) {
        return !path.absolute() && !path.homeRelative() && path.segments().isEmpty();
    }

    private static String effectiveDriveLetter(ParsedPath path, String fallback) {
        return path.driveLetter() == null ? normalizeDriveLetter(fallback) : path.driveLetter();
    }

    private static String normalizeDriveLetter(String driveLetter) {
        if (driveLetter == null || driveLetter.isBlank() || !Character.isLetter(driveLetter.trim().charAt(0))) {
            return DEFAULT_DRIVE_LETTER;
        }
        return upperCaseDrive(driveLetter.trim().substring(0, 1));
    }

    private static String upperCaseDrive(String driveLetter) {
        return driveLetter.toUpperCase(Locale.ROOT);
    }

    private static List<String> splitSegments(String path) {
        if (path == null || path.isEmpty()) {
            return List.of();
        }
        List<String> segments = new ArrayList<>();
        for (String segment : SEPARATORS.split(path)) {
            if (!segment.isEmpty()) {
                segments.add(segment);
            }
        }
        return List.copyOf(segments);
    }

    private static String join(List<String> segments, String separator) {
        return String.join(separator, segments);
    }
}
