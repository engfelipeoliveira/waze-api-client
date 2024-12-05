# Use an OpenJDK 21 runtime image
FROM eclipse-temurin:21-jdk-alpine

ARG CACHEBUST=1

# Set the working directory inside the container
WORKDIR /waze-api-client

# Copy the application JAR file into the container
COPY /target/waze-api-client-0.0.1-SNAPSHOT.jar waze-api-client-0.0.1-SNAPSHOT.jar

# Run the Java application
CMD ["java", "-jar", "waze-api-client-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=pro"]
