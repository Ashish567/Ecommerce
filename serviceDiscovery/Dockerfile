FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY target/serviceDiscovery-0.0.1-SNAPSHOT.jar /app/serviceDiscovery.jar

# Expose the service port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "/app/serviceDiscovery.jar"]
