FROM maven:3.9.1 AS builder

WORKDIR /app

COPY pom.xml ./
COPY src src/

RUN --mount=type=cache,id=m2-cache,sharing=shared,target=/root/.m2 mvn package

FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar .

ENTRYPOINT ["java", "-jar", "capitalgainstaxcalculator.jar"]