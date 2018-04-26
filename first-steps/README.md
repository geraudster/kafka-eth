## Premiers pas

1. Démarrer la plateforme confluent  
`bin/confluent start`
2. Créer un topic input **test**  
`bin/kafka-topics --zookeeper localhost:2181 --create --topic test --partitions 1 --replication-factor 1`
3. Vérifier que le topic est bien présent  
`bin/kafka-topics --list --zookeeper localhost:2181`
4. Envoyer quelques messages  
`bin/kafka-console-producer --broker-list localhost:9092 --topic test`
5. Consommer les messages  
`bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning`
