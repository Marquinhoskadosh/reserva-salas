# Etapa 1: Construção da aplicação com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src/ src/

# Executa o build da aplicação
RUN mvn -B clean package -DskipTests

# Etapa 2: Criando a imagem final com JDK 21
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia os arquivos gerados corretamente para o contêiner
COPY --from=builder /app/target/quarkus-app/ /app/

# Define o comando para iniciar a aplicação
CMD ["java", "-jar", "/app/quarkus-run.jar"]








