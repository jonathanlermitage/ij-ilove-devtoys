@file:Suppress("VulnerableLibrariesLocal")

import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.benmanes.gradle.versions.reporter.result.Result
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure

plugins {
    id("java")
    id("jacoco")
    id("com.adarshr.test-logger") version "3.2.0" // https://github.com/radarsh/gradle-test-logger-plugin
    id("com.palantir.git-version") version "0.15.0" // https://github.com/palantir/gradle-git-version
    id("org.jetbrains.intellij") version "1.9.0" // https://github.com/JetBrains/gradle-intellij-plugin
    id("com.github.ben-manes.versions") version "0.43.0" // https://github.com/ben-manes/gradle-versions-plugin
    id("biz.lermitage.oga") version "1.1.1"
}

// Import variables from gradle.properties file
val pluginIdeaVersion: String by project
val pluginDownloadIdeaSources: String by project
val pluginVersion: String by project
val pluginJavaVersion: String by project
val testLoggerStyle: String by project

val inCI = System.getenv("CI") != null

version = if (pluginVersion == "auto") {
    val versionDetails: Closure<VersionDetails> by extra
    val lastTag = versionDetails().lastTag
    if (lastTag.startsWith("v", ignoreCase = true)) {
        lastTag.substring(1)
    } else {
        lastTag
    }
} else {
    pluginVersion
}

logger.quiet("Will use IDEA $pluginIdeaVersion and Java $pluginJavaVersion. Plugin version set to $version.")

group = "lermitage.intellij.ilovedevtoys"

repositories {
    mavenCentral()
}

val junitVersion = "5.9.1"
val junitPlatformLauncher = "1.9.1"

dependencies {
    implementation("commons-codec:commons-codec:1.15") // fox Hash
    implementation("com.thedeanda:lorem:2.1") // for Lorem Ipsum
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.0") // for JSON <> YAML
    implementation("org.yaml:snakeyaml:1.33") // for JSON <> YAML
    implementation("com.dampcake:bencode:1.4") // for JSON <> BENCODE
    implementation("net.datafaker:datafaker:1.6.0") // for Data Faker
    implementation("com.cronutils:cron-utils:9.2.0") // for cron expression parser

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformLauncher")
}

intellij {
    downloadSources.set(pluginDownloadIdeaSources.toBoolean() && !System.getenv().containsKey("CI"))
    instrumentCode.set(true)
    pluginName.set("I-Love-DevToys")
    sandboxDir.set("${rootProject.projectDir}/.idea-sandbox/${shortenIdeVersion(pluginIdeaVersion)}")
    updateSinceUntilBuild.set(false)
    version.set(pluginIdeaVersion)
}

testlogger {
    try {
        theme = ThemeType.valueOf(testLoggerStyle)
    } catch (e: Exception) {
        theme = ThemeType.PLAIN
        logger.warn(
            "Invalid testLoggerRichStyle value '$testLoggerStyle', " +
                "will use PLAIN style instead. Accepted values are PLAIN, STANDARD and MOCHA."
        )
    }
    showSimpleNames = true
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = pluginJavaVersion
        targetCompatibility = pluginJavaVersion
        options.compilerArgs = listOf("-Xlint:deprecation")
        options.encoding = "UTF-8"
    }
    withType<Test> {
        useJUnitPlatform()
    }
    jacocoTestReport {
        reports {
            dependsOn(test)
            xml.required.set(false)
            csv.required.set(false)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }
    withType<DependencyUpdatesTask> {
        checkForGradleUpdate = true
        gradleReleaseChannel = "current"
        revision = "release"
        rejectVersionIf {
            isNonStable(candidate.version)
        }
        outputFormatter = closureOf<Result> {
            if (outdated.dependencies.isNotEmpty()) {
                logger.quiet("The following dependencies have later release versions:")
                outdated.dependencies.forEach { dependency ->
                    logger.quiet("- ${dependency.group}:${dependency.name} [${dependency.version} -> ${dependency.available.release}]")
                }
            }
        }
    }
    runIde {
        jvmArgs("-Xms128m")
        jvmArgs("-Xmx1024m")
    }
    buildSearchableOptions {
        enabled = false
    }
}

fun isNonStable(version: String): Boolean {
    if (listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().endsWith(it) }) {
        return false
    }
    return listOf("alpha", "Alpha", "ALPHA", "b", "beta", "Beta", "BETA", "rc", "RC", "M", "EA", "pr", "atlassian").any {
        "(?i).*[.-]${it}[.\\d-]*$".toRegex().matches(version)
    }
}

/** Return an IDE version string without the optional PATCH number.
 * In other words, replace IDE-MAJOR-MINOR(-PATCH) by IDE-MAJOR-MINOR. */
fun shortenIdeVersion(version: String): String {
    if (version.contains("SNAPSHOT", ignoreCase = true)) {
        return version
    }
    val matcher = Regex("[A-Za-z]+[\\-]?[0-9]+[\\.]{1}[0-9]+")
    return try {
        matcher.findAll(version).map { it.value }.toList()[0]
    } catch (e: Exception) {
        logger.warn("Failed to shorten IDE version $version: ${e.message}")
        version
    }
}
