#!/bin/bash
set -e

java $JVM_OPTS \
  -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name=$APP_NAME \
  -Dotel.exporter.otlp.protocol=http/protobuf \
  -Dotel.exporter.otlp.endpoint=http://192.168.7.100:4318 \
  -Dspring.profiles.active=production \
  -jar application.jar