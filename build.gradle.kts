plugins {
    java
    checkstyle
    application
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
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}