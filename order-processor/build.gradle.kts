plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.services.wo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("net.ravendb:ravendb:6.0.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0") {
		exclude(group = "org.mockito", module = "mockito-core")
	}
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
	testImplementation("org.mockito:mockito-core:5.5.0")
	testImplementation("org.mockito:mockito-inline:5.2.0")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
