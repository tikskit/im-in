FROM alpine:3.15.0

RUN apk add p7zip
RUN mkdir /download
RUN wget https://gnivc.ru/html/gnivcsoft/KLADR/Base.7z -O /download/Base.7z
RUN mkdir /db
RUN 7za e /download/Base.7z -o/db
RUN ls /db
