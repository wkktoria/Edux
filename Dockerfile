FROM openjdk:21-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src

COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk
VOLUME /tmp

COPY --from=build /app/target/*.jar edux.jar
ENTRYPOINT ["java", "-jar", "/edux.jar"]
EXPOSE 8081