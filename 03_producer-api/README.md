# La blockchain en tant que producer de Kafka

## Objectif

L'objectif de ce TP va être d'envoyer les transactions de la blockchain dans un topic Kafka pour ensuite les manipuler avec lmes Kafka Streams.

## TP
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
