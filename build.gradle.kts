plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	// jacoco
}

group = "propensi.propensiun"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
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
	implementation("org.springframework.boot:spring-boot-starter-validation:2.7.3")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly ("com.mysql:mysql-connector-j")
	implementation ("org.springframework.boot:spring-boot-starter-validation:2.7.3")
	implementation ("com.fasterxml.jackson.core:jackson-databind")
	implementation ("org.springframework.boot:spring-boot-starter-webflux")
	implementation ("org.springframework.boot:spring-boot-starter-security")


	implementation ("org.postgresql:postgresql")
}

//tasks.withType<Test> {
//	useJUnitPlatform()
//	finalizedBy(tasks.jacocoTestReport)
//
//	task.jacocoTestReport{
//		classDirectories.setFrom(files(classDirectories.files.map {
//			fileTree(it) { exclude("**/*Application**")}
//		}))
//		dependsOn(tasks.test)
//		reports {
//			xml.required.set(false)
//			csv.required.set(false)
//			html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
//		}
//	}
//}

