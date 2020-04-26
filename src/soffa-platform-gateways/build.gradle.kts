apply(plugin = "io.soffa.lombok")
apply(plugin = "io.soffa.spring")

dependencies {

    api(project(":soffa-platform-core"))

    api("org.springframework.retry:spring-retry:1.2.5.RELEASE")
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.cloud:spring-cloud-starter-vault-config:2.2.2.RELEASE")

    api("org.liquibase:liquibase-core:3.8.9")
    api("org.postgresql:postgresql")
    api("com.auth0:java-jwt:3.8.3")

}

