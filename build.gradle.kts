import groovy.xml.dom.DOMCategory.attributes
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.0"
}



group = "io.github.abelhegedus"
version = "0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    implementation("io.vertx", "vertx-lang-kotlin", "3.5.3") {
        exclude("org.jetbrains.kotlin","kotlin-stdlib-jre8")
        exclude("org.jetbrains.kotlin","kotlin-stdlib-jre7")
    }
    implementation("io.vertx", "vertx-web", "3.5.3")
    implementation("io.vertx", "vertx-web-api-contract", "3.5.3")

    testCompile("junit", "junit", "4.12")
    testCompile("org.jetbrains.kotlin", "kotlin-test-junit")
    testCompile("io.vertx", "vertx-unit", "3.5.3")
    testCompile("io.vertx", "vertx-web-client", "3.5.3")
}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

//application {
//    mainClassName = "WebServerKt"
//    applicationName = "VertexDemoKotlin"
//}

val fatJar = task("fatJar", type = Jar::class) {
//    baseName = application.applicationName
    manifest {
        attributes["Main-Class"] = "WebServerKt"
    }
    from(configurations.runtime.map {
        if (it.isDirectory) it else zipTree(it)
    })
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}