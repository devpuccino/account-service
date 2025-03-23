FROM maven:3-eclipse-temurin-21-alpine AS builder
ARG VERSION
COPY ./src /data/build/src
COPY ./pom.xml /data/build/pom.xml
RUN curl -L -O https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar && \
    mv opentelemetry-javaagent.jar /data/build/opentelemetry-javaagent.jar
RUN curl -L -O https://artifacts.elastic.co/downloads/logstash/logstash-8.17.3-linux-x86_64.tar.gz && \
    tar xzvf logstash-8.17.3-linux-x86_64.tar.gz && \
    mv  logstash-8.17.3 /data/build/logstash
RUN mvn -f /data/build/pom.xml versions:set -DnewVersion=$VERSION && \
    mvn -f /data/build/pom.xml clean package

FROM eclipse-temurin:21
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
COPY --from=builder /data/build/logstash /data/$APP_NAME/logstash
COPY ./startup.sh /data/$APP_NAME/startup.sh
COPY ./docker/pipeline.conf /etc/logstash/pipeline.conf
COPY ./docker/logstash-log4j2.properties /data/$APP_NAME/logstash/config/log4j2.properties
RUN ln -fs /usr/share/zoneinfo/Asia/Bangkok /etc/localtime && \
    echo 'Asia/Bangkok' > /etc/timezone
RUN cd /data/$APP_NAME/logstash && \
    chmod +x bin/logstash-plugin && \
    ./bin/logstash-plugin install logstash-output-opensearch logstash-filter-multiline
RUN chmod +x /data/$APP_NAME/startup.sh
RUN chmod +x /data/$APP_NAME/logstash/bin/logstash
WORKDIR /data/$APP_NAME
ENTRYPOINT ["sh","startup.sh"]
