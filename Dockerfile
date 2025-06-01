FROM maven:3.9-eclipse-temurin-21 as builder
WORKDIR /data/build
COPY . /data/build
RUN mvn clean compile package

FROM eclipse-temurin:21
ENV APP_NAME=account-service
ENV SPRING_PROFILES_ACTIVE=production
WORKDIR /data/$APP_NAME
COPY --from=builder /data/build/target/$APP_NAME*.jar /data/$APP_NAME/application.jar
COPY ./startup.sh /data/$APP_NAME/startup.sh

RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-9.0.1-linux-x86_64.tar.gz && \
    tar xzvf filebeat-9.0.1-linux-x86_64.tar.gz && \
    rm -f filebeat-9.0.1-linux-x86_64.tar.gz && \
    mv filebeat-9.0.1-linux-x86_64 /data/$APP_NAME/filebeat

COPY ./filebeat.yml /data/$APP_NAME/filebeat.yml

RUN chmod +x /data/$APP_NAME/startup.sh
RUN chmod +x /data/$APP_NAME/filebeat/filebeat

EXPOSE 8080
CMD ["./startup.sh"]
