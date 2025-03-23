# Usando uma imagem do OpenJDK 21 para rodar a aplicação
FROM eclipse-temurin:21-jdk

# Criando diretório de trabalho
WORKDIR /app

# Copiando o JAR gerado pelo build do Quarkus
COPY target/*-runner.jar app.jar

# Expondo a porta que a aplicação usa
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
