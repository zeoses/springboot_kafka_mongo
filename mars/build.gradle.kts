import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
	kotlin("plugin.jpa") version "1.9.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.github.oshai:kotlin-logging-jvm:4.0.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	
	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.9")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
	runtimeOnly("org.springdoc:springdoc-openapi-kotlin:1.6.9")
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.9")
	
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
