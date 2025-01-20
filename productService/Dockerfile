FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY target/product-0.0.1-SNAPSHOT.jar /app/productService.jar

# Expose the service port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/productService.jar"]
