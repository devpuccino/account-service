FROM maven:3-eclipse-temurin-21-alpine AS builder
ARG VERSION
COPY ./src /data/build/src
COPY ./pom.xml /data/build/pom.xml
RUN curl -L -O https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar && \
    mv opentelemetry-javaagent.jar /data/build/opentelemetry-javaagent.jar
RUN mvn -f /data/build/pom.xml versions:set -DnewVersion=$VERSION && \
    mvn -f /data/build/pom.xml clean package

FROM eclipse-temurin:21-alpine
ARG APP_NAME="accout-service"
ARG JVM_OPTS
ARG VERSION
ENV JVM_OPTS=$JVM_OPTS
ENV APP_NAME $APP_NAME
ENV APP_VERSION $VERSION
EXPOSE 8080
EXPOSE 18443
RUN mkdir -p /data/$APP_NAME
COPY --from=builder /data/build/target/*.jar /data/$APP_NAME/application.jar
COPY --from=builder /data/build/opentelemetry-javaagent.jar /data/$APP_NAME/opentelemetry-javaagent.jar
COPY ./startup.sh /data/$APP_NAME/startup.sh
RUN chmod +x /data/$APP_NAME/startup.sh

WORKDIR /data/$APP_NAME
CMD ["/bin/sh", "-c", "sh startup.sh"]

