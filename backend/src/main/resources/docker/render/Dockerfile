FROM eclipse-temurin:17-jdk-jammy

COPY src/main/resources/application-render.properties /app/application.properties

COPY target/zapiszTo-6.9.6-SNAPSHOT.jar .

ENV SPRING_CONFIG_LOCATION=file:/app/application.properties


EXPOSE 8080

CMD java -jar zapiszTo-6.9.6-SNAPSHOT.jar --envname=prod