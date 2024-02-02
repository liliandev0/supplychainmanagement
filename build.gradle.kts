plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-validator:commons-validator:1.7")
    implementation("junit:junit:4.13.1")
    implementation("junit:junit:4.13.1")
    implementation("org.testng:testng:7.1.0")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("mysql:mysql-connector-java:8.0.23")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.slf4j:slf4j-api:1.7.32")
    implementation ("ch.qos.logback:logback-classic:1.2.5")


}

tasks.test {
    useJUnitPlatform()
}