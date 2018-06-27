# Word count avec Kafka Stream

## Etapes pour la création du stream wordCount avec la plateforme **Confluent**
1. Démarrer la plateforme confluent

**Windows**
```
docker-compose up -d
```
**Linux**
```
bin/confluent start
```

2. Créer un topic input **wordcount-input**

**Windows**
```
docker-compose exec kafka \
    kafka-topics --create --topic wordcount-input \
                 --zookeeper zookeeper:2181 \
                 --partitions 1 --replication-factor 1
```
**Linux**
```
bin/kafka-topics --create --topic wordcount-input \
                 --zookeeper localhost:2181 \
                 --partitions 1 --replication-factor 1
```

3. Créer un topic output **streams-wordcount-output**

**Windows**
```
docker-compose exec kafka \
    kafka-topics --create --topic streams-wordcount-output \
                 --zookeeper zookeeper:2181 \
                 --partitions 1 --replication-factor 1
```
**Linux**
```
bin/kafka-topics --create --topic streams-wordcount-output \
                 --zookeeper localhost:2181 \
                 --partitions 1 --replication-factor 1
```

4. Implémenter et démarrer la classe **WordCount**


5. Démarrer un _console producer_ et insérer du texte

**Windows**
```
docker-compose exec kafka \
    kafka-console-producer --broker-list kafka:9092 --topic wordcount-input
```
**Linux**
```
bin/kafka-console-producer --broker-list localhost:9092 --topic wordcount-input
```

6. Démarrer un _console consumer_ et inspecter la sortie du stream output

**Windows**

```
docker-compose exec kafka \
    kafka-console-consumer --topic streams-wordcount-output --from-beginning \
                           --bootstrap-server kafka:9092 \
                           --property print.key=true \
                           --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```
**Linux**
```
bin/kafka-console-consumer --topic streams-wordcount-output --from-beginning \
                           --bootstrap-server localhost:9092 \
                           --property print.key=true \
                           --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

