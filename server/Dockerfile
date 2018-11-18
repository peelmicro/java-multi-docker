# Start with a base image containing Java runtime and Maven
FROM maven:3.6.0-jdk-12-alpine
WORKDIR /app

COPY ./ ./
RUN mvn install -DskipTests

# Run the jar file 
ENTRYPOINT ["java","-jar","target/server-0.0.1-SNAPSHOT.jar"]