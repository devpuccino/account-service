#!/bin/sh

java -jar application.jar 2>&1 | tee /dev/stderr | ./filebeat/filebeat -e -c /data/$APP_NAME/filebeat.yml  > /dev/null 2>&1