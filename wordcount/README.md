# Word count avec Kafka Stream

## Etapes pour la création du stream wordCount avec la plateforme **Confluent**
1. Démarrer la plateforme confluent  
`./bin/confluent start`
3. Créer un topic input **wordcount-input**  
`./bin/kafka-topics --zookeeper localhost:2181 --create --topic wordcount-input --partitions 1 --replication-factor 1`
4. Créer un topic output **streams-wordcount-output**  
`./bin/kafka-topics --create --topic streams-wordcount-output --zookeeper localhost:2181 --partitions 1 --replication-factor 1`
5. Implémenter et démarrer la classe **WordCount**
6. Démarrer un _console producer_ et insérer du texte  
`./bin/kafka-console-producer --broker-list localhost:9092 --topic wordcount-input`
7. Démarrer un _console consumer_ et inspecter la sortie du stream output  
```
./bin/kafka-console-consumer --topic streams-wordcount-output --from-beginning \
                                --new-consumer --bootstrap-server localhost:9092 \
                                --property print.key=true \
                                --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

## Etapes pour la création du stream wordCount avec la plateforme **apache-kafka**

1. Démarrer zookeeper  
`./bin/zookeeper-server-start.sh config/zookeeper.properties`
2. Démarrer le serveur kafka  
`./bin/kafka-server-start.sh config/server.properties`
3. Créer un topic input **wordcount-input**  
`./bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic wordcount-input --partitions 1 --replication-factor 1`
4. Créer un topic output **streams-wordcount-output**  
`./bin/kafka-topics.sh --create --topic streams-wordcount-output --zookeeper localhost:2181 --partitions 1 --replication-factor 1`
5. Implémenter et démarrer la classe **WordCount**
6. Démarrer un _console producer_ et insérer du texte  
`./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic wordcount-input`
7. Démarrer un _console consumer_ et inspecter la sortie du stream output  
```
./bin/kafka-console-consumer.sh --topic streams-wordcount-output --from-beginning \
                                --new-consumer --bootstrap-server localhost:9092 \
                                --property print.key=true \
                                --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```