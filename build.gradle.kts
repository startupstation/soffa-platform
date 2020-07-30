buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            setUrl("https://maven.pkg.github.com/startupstation/artifacts")
            credentials {
                username = "public"
                password = "c5dcf5af86c496cf11ecc1291c39c8f7619b642e"
            }
        }
    }

    dependencies {
        classpath("io.soffa.gradle:soffa-gradle-plugin:1.1.7")
        // classpath("de.marcphilipp.gradle:nexus-publish-plugin:0.4.0")
    }
}
/*

plugins {
    id("io.codearte.nexus-staging").version("0.21.2")
}
*/

apply(plugin = "idea")

subprojects {

    val project = this

    apply(plugin = "io.soffa.java")
    apply(plugin = "io.soffa.junit5")
    apply(plugin = "maven-publish")
    //apply(plugin = "de.marcphilipp.nexus-publish")

    configure<JavaPluginExtension> {
        withJavadocJar()
        withSourcesJar()
    }

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackagesPublish"
                setUrl("https://maven.pkg.github.com/startupstation/artifacts")
                credentials {
                    username = project.findProperty("mvnUsername").toString()
                    password = project.findProperty("mvnPassword").toString()
                }
            }
        }
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
        /*configure<de.marcphilipp.gradle.nexus.NexusPublishExtension> {
            clientTimeout.set(java.time.Duration.ofSeconds(90))
            repositories {
                sonatype()
            }
        }*/
    }

    /*configure<SigningExtension> {
        val publishing = project.extensions.getByName("publishing") as PublishingExtension
        sign(publishing.publications["mavenJava"])
    }*/

    repositories {
        // mavenLocal()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            setUrl("https://maven.pkg.github.com/startupstation/artifacts")
        }
        jcenter()
    }
}
/*

nexusStaging {
    packageGroup = "io.soffa"
    stagingProfileId = "c1ae699bac03ae"
    numberOfRetries = 60
    delayBetweenRetriesInMillis = 5000
    username = project.findProperty("sonatypeUsername")?.toString()
    password = project.findProperty("sonatypePassword")?.toString()
}

*/
