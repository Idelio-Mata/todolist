# Estágio de compilação
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean install

# Estágio de execução
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/todolist-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
