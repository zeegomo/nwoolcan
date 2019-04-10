import com.github.spotbugs.SpotBugsTask
import net.ltgt.gradle.errorprone.*

plugins {
    java
    checkstyle
    application
    id("com.github.spotbugs") version "1.7.1"
    id("net.ltgt.errorprone") version "0.6"
}

checkstyle {
    config = resources.text.fromFile("style.xml")
    maxWarnings = 0
}

spotbugs {
    toolVersion = "4.0.0-beta1"
    effort = "max"
}

application {
    mainClassName = "nwoolcan.application.Main"
}

version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    implementation("io.github.classgraph", "classgraph", "4.8.8")
    compileOnly("com.google.code.findbugs", "jsr305", "3.0.2")
    errorprone("com.uber.nullaway", "nullaway", "0.6.6")
    errorprone("com.google.errorprone", "error_prone_core", "2.3.2")
    errorproneJavac("com.google.errorprone", "javac", "9+181-r4173-1")
    implementation("org.apache.commons", "commons-lang3", "3.8.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.errorprone {
        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "nwoolcan")
    }
}

tasks.withType<SpotBugsTask> {
    reports.xml.isEnabled = false
    reports.html.isEnabled = true
}

tasks.named("check") {
    dependsOn("javadoc")
}