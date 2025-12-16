FROM eclipse-temurin:21-jdk AS base
WORKDIR /app

FROM base AS build
COPY . .
RUN ./gradlew build -x test

FROM base AS dev
WORKDIR /app
COPY src .
COPY gradlew .
COPY gradlew.bat .
COPY gradle .
COPY build.gradle.kts .
COPY settings.gradle.kts .
EXPOSE 8761
CMD ["./gradlew", "bootRun"]


FROM eclipse-temurin:21-jre-alpine AS prod
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]