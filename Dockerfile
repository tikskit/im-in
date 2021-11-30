FROM openjdk:11-jre-slim
COPY /target/im-in-0.0.1-SNAPSHOT.jar /app/im-in-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/im-in-0.0.1-SNAPSHOT.jar"]
