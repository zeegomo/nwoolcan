import net.ltgt.gradle.errorprone.*

plugins {
    java
    checkstyle
    application
    id("net.ltgt.errorprone") version "0.6"
}

checkstyle {
    config = resources.text.fromFile("style.xml")
    maxWarnings = 0
}

application{
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
    annotationProcessor("com.uber.nullaway", "nullaway", "0.6.6")
    errorprone("com.google.errorprone", "error_prone_core", "2.3.2")
    errorproneJavac("com.google.errorprone", "javac", "9+181-r4173-1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    // remove the if condition if you want to run NullAway on test code
    if (!name.toLowerCase().contains("test")) {
        options.errorprone {
            check("NullAway", CheckSeverity.ERROR)
            option("NullAway:AnnotatedPackages", "nwoolcan")
        }
    }
}

tasks.named("check") {
    dependsOn("javadoc")
}