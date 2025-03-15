#!/bin/sh

java $JVM_OPTS \
  -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name=$APP_NAME \
  -Dotel.exporter.otlp.protocol=grpc \
  -Dotel.exporter.otlp.endpoint=http://192.168.7.100:4317 \
  -Dspring.profiles.active=production \
  -jar application.jar | ./filebeat/filebeat -e -c /etc/filebeat/filebeat.yml