FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

ENTRYPOINT ["./wait-for-it.sh", "db_userprofile:5432", "--", "java", "-jar", "app.jar"]

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
