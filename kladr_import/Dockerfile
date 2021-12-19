FROM alpine:3.15.0

# get the KLADR database
RUN apk add p7zip
RUN mkdir /download
RUN wget https://gnivc.ru/html/gnivcsoft/KLADR/Base.7z -O /download/Base.7z
RUN mkdir /db
RUN 7za e /download/Base.7z -o/db
# run the import
COPY /target/kladr-import-0.0.1-SNAPSHOT.jar /app/kladr-import-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/kladr-import-0.0.1-SNAPSHOT.jar"]