# Usa uma imagem do Maven para compilar o código
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -B clean package -DskipTests

# Usa uma imagem do JDK para rodar a aplicação
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*-runner.jar app.jar

# Expor a porta (ajuste se necessário)
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

