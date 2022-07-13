FROM maven:3.8-jdk-11 AS builder

COPY . /app
WORKDIR /app
RUN mvn clean compile install -P dev

# app
#FROM amd64/openjdk:16-jdk
FROM amd64/openjdk:11
WORKDIR /app
COPY --from=builder /app/target/configServer-0.0.1-SNAPSHOT.jar /app/
#COPY ./gateway.sh /app/gateway.sh
#RUN chmod +x /app/gateway.sh
#ENTRYPOINT [ "/app/gateway.sh" ]
ENTRYPOINT ["java", "-jar", "/app/configServer-0.0.1-SNAPSHOT.jar"]
