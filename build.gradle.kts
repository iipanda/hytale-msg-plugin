plugins {
    kotlin("jvm") version "2.2.20"
    id("com.gradleup.shadow") version "9.3.1"
}

group = "com.github.iipanda"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly(files("libs/HytaleServer.jar"))
}

tasks.shadowJar {
    archiveClassifier.set("")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}