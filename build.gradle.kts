plugins {
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.google.firebase:firebase-admin:7.2.0")
	implementation("org.testng:testng:7.1.0")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation ("org.springframework.boot:spring-boot-starter-websocket")
	implementation ("com.fasterxml.jackson.core:jackson-databind:2.12.5")


	compileOnly("org.projectlombok:lombok")
	implementation("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
