plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "8.3.7"
  kotlin("plugin.spring") version "2.2.10"
  kotlin("plugin.jpa") version "2.2.10"
}

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  runtimeOnly("com.microsoft.sqlserver:mssql-jdbc:13.2.0.jre11")
  runtimeOnly("com.zaxxer:HikariCP")
  runtimeOnly("com.h2database:h2:2.3.232")
  runtimeOnly("org.flywaydb:flyway-sqlserver")

  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:1.5.0")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.11")

  testImplementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter-test:1.5.0")
  testImplementation("org.wiremock:wiremock-standalone:3.13.1")
  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.32") {
    exclude(group = "io.swagger.core.v3")
  }
}

kotlin {
  jvmToolchain(21)
  compilerOptions {
    freeCompilerArgs.addAll("-Xjvm-default=all", "-Xwhen-guards", "-Xannotation-default-target=param-property")
  }
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
  }
}
