buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            setUrl("https://oss.sonatype.org/content/groups/public")
        }
    }

    dependencies {
        classpath("io.soffa.gradle:soffa-gradle-plugin:1.1.7")
        classpath("de.marcphilipp.gradle:nexus-publish-plugin:0.4.0")
    }
}

plugins {
    id("io.codearte.nexus-staging").version("0.21.2")
}

apply(plugin = "idea")

subprojects {

    val project = this

    apply(plugin = "io.soffa.java")
    apply(plugin = "io.soffa.junit5")
    apply(plugin = "signing")
    apply(plugin = "de.marcphilipp.nexus-publish")

    configure<JavaPluginExtension> {
        withJavadocJar()
        withSourcesJar()
    }

    configure<PublishingExtension> {
        publications {
            create("mavenJava", MavenPublication::class) {
                from(project.components["java"])
                pom {
                    name.set(project.name)
                    description.set(project.description)
                    versionMapping {
                        usage("java-api") {
                            fromResolutionOf("runtimeClasspath")
                        }
                        usage("java-runtime") {
                            fromResolutionResult()
                        }
                    }
                    url.set("https://github.com/startupstation/${project.name}")
                    inceptionYear.set("2019")
                    organization {
                        name.set("Startup Station")
                        url.set("https://github.com/startupstation")
                    }
                    issueManagement {
                        system.set("GitHub")
                        url.set("https://github.com/startupstation/${project.name}/issues")
                    }
                    scm {
                        connection.set("scm:git:git://git@github.com:startupstation/${project.name}.git")
                        developerConnection.set("scm:git:ssh://git@github.com:startupstation/${project.name}.git")
                        url.set("https://github.com/startupstation/${project.name}")
                    }

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("startupstation")
                            name.set("StartupSTation")
                            email.set("studio@startiption.co")
                            url.set("https://startupstation.co")
                        }
                    }
                }
            }
        }
        configure<de.marcphilipp.gradle.nexus.NexusPublishExtension> {
            repositories {
                sonatype()
            }
        }
    }

    configure<SigningExtension> {
        val publishing = project.extensions.getByName("publishing") as PublishingExtension
        sign(publishing.publications["mavenJava"])
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            setUrl("https://oss.sonatype.org/content/groups/public")
        }
        jcenter()
    }
}

nexusStaging {
    packageGroup = "io.soffa"
    stagingProfileId = "c1ae699bac03ae"
    numberOfRetries = 60
    delayBetweenRetriesInMillis = 5000
    username = project.findProperty("sonatypeUsername")?.toString()
    password = project.findProperty("sonatypePassword")?.toString()
}

