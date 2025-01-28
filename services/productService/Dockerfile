FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="Ashish57" name="productService"
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["sleep","10"]