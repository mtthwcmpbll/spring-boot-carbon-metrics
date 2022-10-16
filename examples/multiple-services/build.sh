#!/usr/bin/env bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# Rebuild all of our applications

cd weather-service
./mvnw clean package
cd $SCRIPT_DIR

cd hello-service
./mvnw clean package
cd $SCRIPT_DIR

# Build all of the docker images for our components
docker-compose build