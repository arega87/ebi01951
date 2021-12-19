#!/bin/bash
docker-compose down
mvn clean install -DskipTests
docker-compose up -d --build