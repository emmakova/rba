# RBA Application

A Spring Boot service for processing credit cards, and integrates with external APIs using WireMock for mocking.

---

## 🚀 Features

- Card status update handling
- WireMock for mocking downstream APIs
- Dockerized environment with Kafka and WireMock containers
- Profile-based configuration for local and containerized development

---

## 📦 Prerequisites

- Java 21+
- Docker + Docker Compose

---

## 🛠️ Running the app with docker-compose

1. build the app image using `./gradlew clean jibDockerBuild`
2. start kafka, wiremock and the app with `docker-compose up -d`

You can start everything with a single command: `./dev-up.sh` 
Make sure the script has execution permission first `chmod +x ./dev-up.sh`

To stop and clean up all running containers, use: `docker-compose down`


## 🛠️ Running the app only (dev profile)

1. build the app using `./gradlew clean build -x test`
2. start the app using `./gradlew bootRun --args='--spring.profiles.active=dev'`
