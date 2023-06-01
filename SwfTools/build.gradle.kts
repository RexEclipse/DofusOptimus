plugins {
    kotlin("jvm")
}

repositories {
    maven { url = uri("https://repo.maven.apache.org/maven2/") }
}

group = rootProject.group
version = rootProject.version
description = "$group:${project.name}"
java.sourceCompatibility = rootProject.java.sourceCompatibility

dependencies {
    implementation(project(":VLDofusBotCore"))
}

sourceSets.getByName("main") {
    java.srcDir("src/main/kotlin")
    resources.srcDir("src/main/resources")
}