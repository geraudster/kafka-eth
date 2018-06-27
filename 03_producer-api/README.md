# La blockchain en tant que producer de Kafka

## Objectif

L'objectif de ce TP va être d'envoyer les transactions de la blockchain dans un topic Kafka pour ensuite les manipuler avec les Kafka Streams.

## Exo 1 - Mise en oeuvre du `KafkaProducer`
1. Complétez la classe `BlockchainProducer` afin d'envoyer les transactions de la blockchain dans le topic `transactions`

2. Démarrer le projet
```
./mvnw compile exec:java -Dexec.args=<geth client url>
```

Vous pouvez utiliser https://client.one.geth-znk.cf/ ou https://client.two.geth-znk.cf/ comme url.

/!\ Sous Windows, vous risquez d'avoir des problèmes de certificats.
Il faudra alors télécharger le certificat depuis un navigateur, et l'ajouter au keystore de votre jre grâce à la commande suivante :
```
keytool -import -alias <my-alias> -keystore <path-to-jre>\lib\security\cacerts -file <path-to-cer>\cert.crt
```

3. Vérifier que les messages arrivent bien dans le topic `transactions`

**Windows**
```
docker-compose exec schema-registry \
    kafka-avro-console-consumer --bootstrap-server kafka:29092 --topic transactions --from-beginning
```
**Linux**
```
bin/kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic transactions --from-beginning
```

#### Tips

* `KafkaProducer`: le client Kafka chargé d'envoyer les `ProducerRecord`.
  * Config
    * bootstrap.servers: 1 or + IPs of Kafka brokers (i.e. : localhost:9092)
    * key.serializer and value.serializer: Class names for key/value serialization
    * schema.registry.url: when using Avro serialization (i.e. : http://localhost:8081)
  * Serializer : IntegerSerializer, StringSerializer, JsonSerializer, KafkaAvroSerializer…
  * send() → méthode pour envoyer un record (retourne un `Future`)

* `ProducerRecord`: La clé/valeur à envoyer
  * (topic, clé, valeur) ici la clé est le hash de la transaction, la valeur est l'objet `Transaction`
  * on utilise le `KafkaAvroSerializer` pour la valeur, il va se baser sur le schéma pour la sérialisation des objets `Transation`

* Si le type `Transaction` apparaît en erreur dans votre IDE, lancer un `mvn generate-sources`


## Exo 2 - Utilisation des Kafka Streams

Nous allons maintenant consommer les données importées dans Kafka pour calculer quelques stats.

### Nombre de transactions par utilisateurs

* À partir du topic `transactions`, calculer le nombre de transactions par utilisateur (champ `fromAddress` de la classe `Transaction`)
* Placer le résultat dans un topic `nb_tx_by_user`

### Cumul des transactions par utilisateurs

* À partir du topic `transactions`, calculer le montant (champ `value`) des transactions par utilisateur (champ `fromAddress` de la classe `Transaction`)
* Placer le résultat dans un topic `value_by_user`

Il faudra utiliser l'opérateur `reduce`.
