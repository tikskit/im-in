FROM openjdk:11-jre-slim
COPY /target/imin-0.0.1-SNAPSHOT.jar.jar /app/imin-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/imin-0.0.1-SNAPSHOT.jar"]
