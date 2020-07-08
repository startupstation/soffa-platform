apply(plugin = "io.soffa.lombok")
apply(plugin = "io.soffa.spring")

dependencies {

    api(project(":soffa-platform-core"))
    api("org.springframework.retry:spring-retry:1.2.5.RELEASE")
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.boot:spring-boot-starter-aop")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.cloud:spring-cloud-starter-vault-config:2.2.2.RELEASE")
    api("org.springdoc:springdoc-openapi-webflux-ui:1.3.0")
    api("org.springdoc:springdoc-openapi-security:1.3.0")
    api("org.liquibase:liquibase-core:3.9.0")
    api("org.postgresql:postgresql")
    api("com.auth0:java-jwt:3.10.3")
    api("com.auth0:jwks-rsa:0.12.0")
    implementation("xerces:xercesImpl:2.12.0")

}

