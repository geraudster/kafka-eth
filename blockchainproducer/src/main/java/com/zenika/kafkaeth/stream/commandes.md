## Commandes utiles

* Lancer un console consumer pour le topic **nb-tx-by-user-data**
```
./bin/kafka-console-consumer --topic nb-tx-by-user-data --from-beginning \
                                --new-consumer --bootstrap-server localhost:9092 \
                                --property print.key=true \
                                --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
``` 

* Lancer un console consumer pour le topic **value-by-user-data**
```
./bin/kafka-console-consumer --topic value-by-user-data --from-beginning \
                                --new-consumer --bootstrap-server localhost:9092 \
                                --property print.key=true \
                                --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
``` 
