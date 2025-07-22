# Estágio 1: Construir a aplicação com o Maven
FROM maven:3.8-openjdk-17 AS build

# Copia apenas o pom.xml primeiro para otimizar o cache
COPY conectapet-backend/pom.xml /app/pom.xml
WORKDIR /app
RUN mvn dependency:go-offline

# Agora copia o resto do código-fonte do backend
COPY conectapet-backend/src /app/src

# Executa o comando para compilar e empacotar a aplicação
RUN mvn package -DskipTests

# Estágio 2: Executar a aplicação
FROM openjdk:17-slim

WORKDIR /app

# Copia o arquivo .jar com o NOME CORRETO que vimos no log
COPY --from=build /app/target/conectapet-backend-0.0.1-SNAPSHOT.jar .

# Expõe a porta 8080 para o Render
EXPOSE 8080

# Comando para iniciar a aplicação, também com o NOME CORRETO
CMD ["java", "-jar", "conectapet-backend-0.0.1-SNAPSHOT.jar"]
