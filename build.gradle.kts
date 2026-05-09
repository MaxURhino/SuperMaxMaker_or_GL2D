plugins {
    kotlin("jvm") version "2.3.0"
    id("application")
}

group = "net.max_rhino.gl2d"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java")

    val lwjglVersion = "3.4.1"
    val jomlVersion = "1.10.8"
    val lwjglNatives = "natives-windows"

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test"))

        implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

        implementation("org.lwjgl", "lwjgl")
        implementation("org.lwjgl", "lwjgl-fmod")
        implementation("org.lwjgl", "lwjgl-freetype")
        implementation("org.lwjgl", "lwjgl-glfw")
        implementation("org.lwjgl", "lwjgl-nanovg")
        implementation("org.lwjgl", "lwjgl-nfd")
        implementation("org.lwjgl", "lwjgl-openal")
        implementation("org.lwjgl", "lwjgl-opengl")
        implementation("org.lwjgl", "lwjgl-sdl")
        implementation("org.lwjgl", "lwjgl-spng")
        implementation("org.lwjgl", "lwjgl-stb")
        implementation("org.lwjgl", "lwjgl-tinyfd")
        implementation("org.lwjgl", "lwjgl", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-freetype", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-nanovg", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-sdl", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-spng", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
        implementation("org.lwjgl", "lwjgl-tinyfd", classifier = lwjglNatives)
        implementation("org.joml", "joml", jomlVersion)

        implementation("com.google.code.gson", "gson", "2.14.0")
        implementation("org.apache.commons", "commons-lang3", "3.19.0")
        implementation("org.apache.commons", "commons-compress", "1.28.0")

        implementation("org.slf4j", "slf4j-api", "2.0.17")
    }

    kotlin {
        jvmToolchain(25)
    }

    tasks.test {
        useJUnitPlatform()
    }
}

application {
    mainClass.set("net.max_rhino.gl2d.test.Test")
}