# Start with a base image containing Java runtime and Maven
FROM maven:3.6.0-jdk-12-alpine
WORKDIR /app

COPY ./ ./
RUN mvn install -DskipTests

# Run the jar file 
ENTRYPOINT ["java","-cp","target/worker-1.0-SNAPSHOT-jar-with-dependencies.jar","com.peelmicro.App"]