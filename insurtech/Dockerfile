FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY nbp.crt /app/nbp.crt

RUN keytool -import -alias nbp -keystore $JAVA_HOME/lib/security/cacerts -file /app/nbp.crt -storepass changeit -noprompt

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]