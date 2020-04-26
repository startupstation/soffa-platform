apply(plugin = "io.soffa.lombok")

dependencies {

    implementation("joda-time:joda-time:2.10.6")
    implementation("com.joestelmach:natty:0.13")
    implementation("org.hashids:hashids:1.0.3")
    implementation("com.fasterxml.uuid:java-uuid-generator:4.0.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.3")
    implementation("commons-io:commons-io:2.6")
    implementation("com.amazonaws:aws-java-sdk-s3:1.11.638")

    api("com.auth0:java-jwt:3.8.3")
    api("javax.persistence:javax.persistence-api:2.2")
    api("javax.transaction:javax.transaction-api:1.3")
    api("org.slf4j:slf4j-api:1.7.28")
    api("commons-codec:commons-codec:1.14")
    api("org.apache.commons:commons-lang3:3.10")
    api("com.google.guava:guava:29.0-jre")
    api("javax.validation:validation-api:2.0.1.Final")
    api("org.hibernate.validator:hibernate-validator:6.1.4.Final")
    api("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    api("com.fasterxml.jackson.core:jackson-annotations:2.10.3")
    api("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.3")
    api("com.konghq:unirest-java:3.7.02")
    api("net.jodah:failsafe:2.3.5")

}
