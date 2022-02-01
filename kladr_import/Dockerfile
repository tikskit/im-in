FROM alpine:3.15.0

# get the KLADR database
RUN apk add p7zip
RUN mkdir /download
RUN wget https://gnivc.ru/html/gnivcsoft/KLADR/Base.7z -O /download/Base.7z
RUN mkdir /db
RUN 7za e /download/Base.7z -o/db

FROM openjdk:13.0.2-jdk
RUN mkdir /db
COPY --from=0 /db/KLADR.dbf /db/KLADR.dbf
COPY /target/kladr-import-0.0.1-SNAPSHOT.jar /app/kladr-import-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#EXPOSE 5005
ENTRYPOINT ["java", \
#    "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005", \
    "-jar", \
    "/app/kladr-import-0.0.1-SNAPSHOT.jar", \
    "org.springframework.batch.core.launch.support.CommandLineJobRunner", \
    "ru.tikskit.imin.kladrimport.config.BatchConfig", "ImportFromKLADR", "file.name=/db/KLADR.dbf"]