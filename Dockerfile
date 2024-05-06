FROM eclipse-temurin:21 AS builder
WORKDIR /weatherRedesigned-viewer
COPY . .
RUN ./mvnw package -DskipTests=true

FROM eclipse-temurin:21
WORKDIR /weatherRedesigned-viewer
COPY --from=builder /weatherRedesigned-viewer/target/*.jar weatherRedesigned-viewer-1.0.0.jar
ENTRYPOINT ["java","-jar","weatherRedesigned-viewer-1.0.0.jar"]