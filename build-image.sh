#!/bin/bash
docker build \
  --build-arg VERSION=0.0.1 \
  --build-arg APP_NAME=accout-service \
  --build-arg JVM_OPTS="-Xms512m -XX:+UseG1GC" \
  -t account-service:0.0.1 -t account-service .
