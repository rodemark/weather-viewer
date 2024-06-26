FROM eclipse-temurin:21 AS builder
WORKDIR /weather-viewer
COPY . .
RUN ./mvnw package -DskipTests=true

FROM eclipse-temurin:21
WORKDIR /weatherCurrent-viewer
COPY --from=builder /weather-viewer/target/*.jar weather-viewer-1.0.0.jar
ENTRYPOINT ["java","-jar","weather-viewer-1.0.0.jar"]