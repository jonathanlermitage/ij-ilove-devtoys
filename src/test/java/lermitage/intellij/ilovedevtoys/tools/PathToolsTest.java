package lermitage.intellij.ilovedevtoys.tools;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathToolsTest {

    private static final String CURRENT_USER = "yoda";

    @Nested
    class Convert {

        @ParameterizedTest(name = "[{index}] {0}")
        @MethodSource("lermitage.intellij.ilovedevtoys.tools.PathToolsTest#equivalentSpellingsOfTheSamePath")
        void convertsEveryInputFormatToEveryOutputFormat(String input) {
            PathTools.ConvertedPaths converted = PathTools.convert(input, "C", CURRENT_USER);

            assertEquals("C:\\foo\\bar\\baz", converted.windows());
            assertEquals("C:/foo/bar/baz", converted.windowsAlt());
            assertEquals("/c/foo/bar/baz", converted.gitBash());
            assertEquals("/mnt/c/foo/bar/baz", converted.wsl());
            assertEquals("/foo/bar/baz", converted.unix());
            assertEquals("C", converted.driveLetter());
        }

        @Test
        void usesFallbackDriveLetterWhenInputCarriesNone() {
            PathTools.ConvertedPaths converted = PathTools.convert("/foo/bar", "D", CURRENT_USER);

            assertEquals("D:\\foo\\bar", converted.windows());
            assertEquals("D:/foo/bar", converted.windowsAlt());
            assertEquals("/d/foo/bar", converted.gitBash());
            assertEquals("/mnt/d/foo/bar", converted.wsl());
            assertEquals("/foo/bar", converted.unix());
            assertEquals("D", converted.driveLetter());
        }

        @Test
        void driveLetterCarriedByInputWinsOverFallback() {
            PathTools.ConvertedPaths converted = PathTools.convert("E:\\foo", "D", CURRENT_USER);

            assertEquals("E:\\foo", converted.windows());
            assertEquals("/e/foo", converted.gitBash());
            assertEquals("/mnt/e/foo", converted.wsl());
            assertEquals("E", converted.driveLetter());
        }

        @ParameterizedTest(name = "[{index}] \"{0}\" yields empty output")
        @ValueSource(strings = {"", "   ", "\t"})
        void blankInputYieldsEmptyOutput(String input) {
            PathTools.ConvertedPaths converted = PathTools.convert(input, "C", CURRENT_USER);

            assertEquals("", converted.windows());
            assertEquals("", converted.windowsAlt());
            assertEquals("", converted.gitBash());
            assertEquals("", converted.wsl());
            assertEquals("", converted.unix());
        }

        @Test
        void nullInputYieldsEmptyOutput() {
            PathTools.ConvertedPaths converted = PathTools.convert(null, "C", CURRENT_USER);

            assertEquals("", converted.windows());
            assertEquals("", converted.unix());
        }

        @Test
        void invalidFallbackDriveLetterFallsBackToDefault() {
            assertEquals("C:\\foo", PathTools.convert("/foo", "9", CURRENT_USER).windows());
            assertEquals("C:\\foo", PathTools.convert("/foo", "", CURRENT_USER).windows());
            assertEquals("C:\\foo", PathTools.convert("/foo", null, CURRENT_USER).windows());
        }
    }

    @Nested
    class HomeDirectories {

        @ParameterizedTest(name = "[{index}] {0}")
        @ValueSource(strings = {"~/foo/bar", "/Users/yoda/foo/bar", "/home/yoda/foo/bar", "C:\\Users\\yoda\\foo\\bar", "/c/Users/yoda/foo/bar"})
        void recognizesTheCurrentUserHomeInEveryFormat(String input) {
            PathTools.ConvertedPaths converted = PathTools.convert(input, "C", CURRENT_USER);

            assertEquals("C:\\Users\\yoda\\foo\\bar", converted.windows());
            assertEquals("C:/Users/yoda/foo/bar", converted.windowsAlt());
            assertEquals("/c/Users/yoda/foo/bar", converted.gitBash());
            assertEquals("/mnt/c/Users/yoda/foo/bar", converted.wsl());
            assertEquals("~/foo/bar", converted.unix());
        }

        @Test
        void keepsSomebodyElseHomeExplicitRatherThanCollapsingItToTilde() {
            PathTools.ConvertedPaths converted = PathTools.convert("C:\\Users\\rey\\foo", "C", CURRENT_USER);

            assertEquals("C:\\Users\\rey\\foo", converted.windows());
            assertEquals("/Users/rey/foo", converted.unix());
        }

        @Test
        void expandsBareTildeToTheHomeDirectoryItself() {
            PathTools.ConvertedPaths converted = PathTools.convert("~", "C", CURRENT_USER);

            assertEquals("C:\\Users\\yoda", converted.windows());
            assertEquals("/c/Users/yoda", converted.gitBash());
            assertEquals("~", converted.unix());
        }

        @Test
        void matchesTheUserNameCaseInsensitively() {
            assertEquals("~/foo", PathTools.convert("C:\\Users\\YODA\\foo", "C", CURRENT_USER).unix());
        }

        @Test
        void doesNotTreatTheUsersFolderItselfAsAHomeDirectory() {
            PathTools.ConvertedPaths converted = PathTools.convert("C:\\Users", "C", CURRENT_USER);

            assertEquals("C:\\Users", converted.windows());
            assertEquals("/Users", converted.unix());
        }
    }

    @Nested
    class EdgeCases {

        @Test
        void normalizesTrailingSeparators() {
            assertEquals("C:\\foo\\bar", PathTools.convert("C:\\foo\\bar\\", "C", CURRENT_USER).windows());
            assertEquals("/foo/bar", PathTools.convert("/foo/bar/", "C", CURRENT_USER).unix());
        }

        @Test
        void normalizesMixedAndRepeatedSeparators() {
            assertEquals("C:\\foo\\bar\\baz", PathTools.convert("C:\\foo/bar\\\\baz", "C", CURRENT_USER).windows());
            assertEquals("/foo/bar/baz", PathTools.convert("/foo//bar/baz", "C", CURRENT_USER).unix());
        }

        @Test
        void normalizesALowerCaseDriveLetter() {
            PathTools.ConvertedPaths converted = PathTools.convert("d:\\foo", "C", CURRENT_USER);

            assertEquals("D:\\foo", converted.windows());
            assertEquals("/d/foo", converted.gitBash());
            assertEquals("D", converted.driveLetter());
        }

        @Test
        void rendersADriveRoot() {
            PathTools.ConvertedPaths converted = PathTools.convert("C:\\", "C", CURRENT_USER);

            assertEquals("C:\\", converted.windows());
            assertEquals("C:/", converted.windowsAlt());
            assertEquals("/c/", converted.gitBash());
            assertEquals("/mnt/c/", converted.wsl());
            assertEquals("/", converted.unix());
        }

        @Test
        void rendersARelativePathWithoutADriveOrRoot() {
            PathTools.ConvertedPaths converted = PathTools.convert("foo/bar", "D", CURRENT_USER);

            assertEquals("foo\\bar", converted.windows());
            assertEquals("foo/bar", converted.windowsAlt());
            assertEquals("foo/bar", converted.gitBash());
            assertEquals("foo/bar", converted.wsl());
            assertEquals("foo/bar", converted.unix());
        }

        @Test
        void keepsParentDirectoryReferencesInRelativePaths() {
            PathTools.ConvertedPaths converted = PathTools.convert("..\\foo\\bar", "C", CURRENT_USER);

            assertEquals("..\\foo\\bar", converted.windows());
            assertEquals("../foo/bar", converted.unix());
        }

        @Test
        void rendersAUncPathWithoutADriveLetter() {
            PathTools.ConvertedPaths converted = PathTools.convert("\\\\server\\share\\foo", "D", CURRENT_USER);

            assertEquals("\\\\server\\share\\foo", converted.windows());
            assertEquals("//server/share/foo", converted.windowsAlt());
            assertEquals("//server/share/foo", converted.gitBash());
            assertEquals("//server/share/foo", converted.wsl());
            assertEquals("//server/share/foo", converted.unix());
        }

        @Test
        void readsASingleLetterRootAsAGitBashDrive() {
            PathTools.ConvertedPaths converted = PathTools.convert("/c", "D", CURRENT_USER);

            assertEquals("C:\\", converted.windows());
            assertEquals("/", converted.unix());
            assertEquals("C", converted.driveLetter());
        }

        @Test
        void trimsSurroundingWhitespace() {
            assertEquals("C:\\foo", PathTools.convert("  C:\\foo  ", "C", CURRENT_USER).windows());
        }

        @Test
        void preservesSpacesInsideAPath() {
            PathTools.ConvertedPaths converted = PathTools.convert("C:\\Program Files\\My App", "C", CURRENT_USER);

            assertEquals("C:\\Program Files\\My App", converted.windows());
            assertEquals("/c/Program Files/My App", converted.gitBash());
        }
    }

    @Nested
    class Quote {

        @Test
        void wrapsAPathInDoubleQuotes() {
            assertEquals("\"C:\\Program Files\\My App\"", PathTools.quote("C:\\Program Files\\My App"));
            assertEquals("\"/foo/bar\"", PathTools.quote("/foo/bar"));
        }

        @Test
        void returnsEmptyForNothingToQuote() {
            assertEquals("", PathTools.quote(""));
            assertEquals("", PathTools.quote("   "));
            assertEquals("", PathTools.quote(null));
        }
    }

    @Nested
    class DriveLetterDefaults {

        @Test
        void extractsADriveLetterFromAWindowsPath() {
            assertEquals("D", PathTools.extractDriveLetter("D:\\projects\\foo"));
            assertEquals("C", PathTools.extractDriveLetter("c:/projects/foo"));
        }

        @Test
        void extractsNoDriveLetterFromAPathThatHasNone() {
            assertNull(PathTools.extractDriveLetter("/home/yoda/projects"));
            assertNull(PathTools.extractDriveLetter("\\\\server\\share"));
            assertNull(PathTools.extractDriveLetter(null));
        }

        @Test
        void prefersTheProjectDrive() {
            assertEquals("E", PathTools.defaultDriveLetter("E:\\projects\\foo"));
        }

        @Test
        void fallsBackToASingleLetterWhenTheProjectHasNoDrive() {
            // the fallback reads the OS user home, so assert the shape rather than a value that varies per machine
            assertDriveLetterShape(PathTools.defaultDriveLetter(null));
            assertDriveLetterShape(PathTools.defaultDriveLetter("/home/yoda/projects"));
        }

        private void assertDriveLetterShape(String driveLetter) {
            assertNotNull(driveLetter);
            assertEquals(1, driveLetter.length());
            assertTrue(Character.isUpperCase(driveLetter.charAt(0)), "expected an upper case drive letter, got " + driveLetter);
        }
    }

    @Nested
    class Parse {

        @Test
        void capturesTheDetectedDriveLetterAndSegments() {
            PathTools.ParsedPath parsed = PathTools.parse("C:\\foo\\bar");

            assertEquals("C", parsed.driveLetter());
            assertTrue(parsed.absolute());
            assertEquals(List.of("foo", "bar"), parsed.segments());
        }

        @Test
        void capturesTheHomeOwner() {
            PathTools.ParsedPath parsed = PathTools.parse("/Users/rey/foo");

            assertTrue(parsed.homeRelative());
            assertEquals("rey", parsed.homeUser());
            assertNull(parsed.driveLetter());
            assertEquals(List.of("foo"), parsed.segments());
        }

        @Test
        void flagsAUncPath() {
            assertTrue(PathTools.parse("\\\\server\\share").unc());
            assertTrue(PathTools.parse("//server/share").unc());
        }
    }

    static Stream<String> equivalentSpellingsOfTheSamePath() {
        return Stream.of(
            "C:\\foo\\bar\\baz",
            "C:/foo/bar/baz",
            "/c/foo/bar/baz",
            "/mnt/c/foo/bar/baz",
            "/foo/bar/baz");
    }
}
