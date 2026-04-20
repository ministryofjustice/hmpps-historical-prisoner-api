plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "10.2.1"
  kotlin("plugin.spring") version "2.3.20"
  kotlin("plugin.jpa") version "2.3.20"
}

dependencies {
  runtimeOnly("com.microsoft.sqlserver:mssql-jdbc:13.4.0.jre11")
  runtimeOnly("com.zaxxer:HikariCP")
  runtimeOnly("com.h2database:h2:2.4.240")
  runtimeOnly("org.flywaydb:flyway-sqlserver")

  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:2.1.0")
  implementation("org.springframework.boot:spring-boot-starter-webclient")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-flyway")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")

  testImplementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter-test:2.1.0")
  testImplementation("org.springframework.boot:spring-boot-starter-webclient-test")
  testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
  testImplementation("org.wiremock:wiremock-standalone:3.13.2")
  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.37") {
    exclude(group = "io.swagger.core.v3")
  }
}

kotlin {
  jvmToolchain(25)
  compilerOptions {
    freeCompilerArgs.addAll("-Xannotation-default-target=param-property")
  }
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
  }
}
