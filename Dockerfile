FROM openjdk:21.0.2
WORKDIR /app
COPY ./target/weather-viewer-1.0.0.jar /app
CMD ["java", "-jar", "weather-viewer-1.0.0.jar"]