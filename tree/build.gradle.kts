import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.AbstractDokkaTask
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm")
    id("com.anatawa12.auto-visitor")
    id("com.anatawa12.auto-tostring")
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

autoVisitor.addLib("")
autoToString.addLib()

tasks.test {
    useJUnitPlatform()
}

java {
    withJavadocJar()
}

tasks.withType(AbstractDokkaLeafTask::class).all {
    moduleName.set("class-ast tree")
    dependencies {
        plugins(project(":dokka-plugin"))
        if (name.startsWith("dokkaJavadoc")) {
            plugins("org.jetbrains.dokka:kotlin-as-java-plugin:1.5.0")
        }
    }
    dokkaSourceSets["main"].includes.from("kdoc.md")
}

val dokkaJavadoc: DokkaTask by tasks
val dokkaHtml: DokkaTask by tasks
val dokka: Task by tasks.creating {
    group = "documentation"
    dependsOn(dokkaJavadoc)
    dependsOn(dokkaHtml)
}

tasks.named("javadocJar", Jar::class) {
    dependsOn(dokkaJavadoc)
    from(dokkaJavadoc.outputDirectory)
}
