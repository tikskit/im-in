FROM openjdk:11-jre-slim
COPY /target/imin-app-0.0.1-SNAPSHOT.jar /app/imin-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/imin-app-0.0.1-SNAPSHOT.jar"]