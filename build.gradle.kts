plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "6.1.2"
  kotlin("plugin.spring") version "2.0.21"
  kotlin("plugin.jpa") version "2.0.21"
}

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  runtimeOnly("com.microsoft.sqlserver:mssql-jdbc:12.9.0.jre11-preview")
  runtimeOnly("com.zaxxer:HikariCP")
  runtimeOnly("com.h2database:h2:2.3.232")
  runtimeOnly("org.flywaydb:flyway-sqlserver")

  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:1.1.1")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

  testImplementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter-test:1.1.1")
  testImplementation("org.wiremock:wiremock-standalone:3.10.0")
  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.24") {
    exclude(group = "io.swagger.core.v3")
  }
}

kotlin {
  jvmToolchain(21)
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
  }
}
