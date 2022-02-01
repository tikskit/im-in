# Импорт адрессов РФ из классификатора КЛАДР РФ

Содержит docker образ, в котором будет выкачиваться 

Запуск образа
docker start
Запуск 
java -jar kladr-import-0.0.1-SNAPSHOT.jar ru.tikskit.imin.kladrimport.config.BatchConfig ImportFromKLADR

java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 kladr-import-0.0.1-SNAPSHOT.jar ru.tikskit.imin.kladrimport.config.BatchConfig ImportFromKLADR

java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 target\kladr-import-0.0.1-SNAPSHOT.jar ru.tikskit.imin.kladrimport.KladrImportApplication ImportFromKLADR