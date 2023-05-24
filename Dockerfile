FROM gradle:latest as builder
WORKDIR /app
COPY src ./src
COPY build.gradle ./
RUN gradle clean build


FROM eclipse-temurin:latest
RUN mkdir /app
WORKDIR app
COPY --from=builder /app/build/libs/*.jar /app/compose-service.jar
CMD  ["java", "-jar",  "compose-service.jar"]

