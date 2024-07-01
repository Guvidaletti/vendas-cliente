FROM maven:3.9.8 as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
WORKDIR /app
#COPY ./target/*.jar ./application.jar
COPY --from=build ./build/target/*.jar ./application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]