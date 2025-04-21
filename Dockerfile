FROM openjdk:21

WORKDIR /app

COPY target/cgpi-0.0.1-SNAPSHOT.jar /app/cgpi-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "cgpi-0.0.1-SNAPSHOT.jar"]