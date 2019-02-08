plugins {
    java
    checkstyle
}

checkstyle {
    config = resources.text.fromFile("style.xml")
}

version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}