plugins {
    id("java")
    id("war")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.apache.tomcat.embed:tomcat-embed-core:9.0.73")
    implementation ("org.apache.tomcat.embed:tomcat-embed-jasper:9.0.73")
    implementation ("org.postgresql:postgresql:42.5.4")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation ("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.test {
    useJUnitPlatform()
}
war {

    webAppDirName = ("src/main/webapp")
}