#!/usr/bin/env bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# Create image of carbon aware sdk

mkdir temp
cd temp
git clone https://github.com/Green-Software-Foundation/carbon-aware-sdk.git
cd carbon-aware-sdk
git checkout v1.0.0
cd ./$(git rev-parse --show-cdup)/src
docker build -t carbon_aware:v1 -f CarbonAware.WebApi/src/Dockerfile .
cd $SCRIPT_DIR
rm -rf temp

# Rebuild all of our applications

cd ./carbon-aware-starter
./mvnw clean install
cd $SCRIPT_DIR

cd ./examples/multiple-services/weather-service
./mvnw clean package
cd $SCRIPT_DIR

cd ./examples/multiple-services/hello-service
./mvnw clean package
cd $SCRIPT_DIR