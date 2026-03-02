import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.dokka)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    explicitApi()

    jvmToolchain(17)
    jvm()

    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.ktor.client.core)
                api(libs.ktor.client.content.negociation)
                api(libs.ktor.serialization.json)

                api(libs.kotlin.coroutines)
                api(libs.kotlin.datetime)
                api(libs.kotlin.serlization.json)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negociation)
                implementation(libs.ktor.serialization.json)

                implementation(libs.kotlin.coroutines)
                implementation(libs.kotlin.datetime)
                implementation(libs.kotlin.serlization.json)
            }
        }

        val cioMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api(libs.ktor.client.cio)
            }
        }

        val cioTest by creating {
            dependsOn(commonTest.get())
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }

        getByName("jvmMain").dependsOn(cioMain)
        getByName("jvmTest").dependsOn(cioTest)
    }
}

dokka {
    moduleName.set("Pocketbase Kotlin")
}
