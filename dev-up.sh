#!/bin/bash
set -e

echo "Building Docker image..."
./gradlew clean jibDockerBuild

echo "Starting all services with docker-compose..."
docker-compose up -d
