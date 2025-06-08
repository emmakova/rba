#!/bin/bash
set -e

if [[ "$(docker images -q rba-app:latest 2> /dev/null)" == "" ]]; then
  echo "Building Docker image..."
  ./gradlew clean jibDockerBuild
else
  echo "Image already exists, skipping Jib build"
fi

echo "Starting all services with docker-compose..."
docker-compose up -d