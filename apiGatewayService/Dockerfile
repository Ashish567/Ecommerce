FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY target/spring-cloud-gateway-overview-1.0.0.jar /app/apiGatewayService.jar

# Expose the service port
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "/app/apiGatewayService.jar"]
