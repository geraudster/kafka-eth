# Windows
### Prérequis
* VirtualBox https://www.virtualbox.org/wiki/Downloads
* Docker for Windows https://store.docker.com/editions/community/docker-ce-desktop-windows
Lors de l'installation, Docker propose d'activer Hyper-V. Il faut accepter, l'ordinateur va redémarrer.

### Premiers pas
1. Démarrer ZooKeeper et les containers Kafka

`cd <path-to-this-repository>/`

`docker-compose up -d`

Si vous avez une erreur d'authorisation sur la récupération des images, votre token à Docker Hub à sûrement expiré, tapez la commande suivante avant de refaire un docker-compose up :

`docker logout`

2. Créer un topic input **test**

`docker-compose exec kafka kafka-topics --zookeeper localhost:2181 --create --topic test --partitions 1 --replication-factor 1`

3. Vérifier que le topic soit bien présent

`docker-compose exec kafka kafka-topics --list --zookeeper localhost:2181`

4. Envoyer quelques messages

`docker-compose exec kafka kafka-console-producer --broker-list localhost:9092 --topic test`

5. Consommer les messages

`docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning`

# Linux
### Prérequis
* Java 8/9
### Premiers pas
1. Télécharger et décompresser Confluent Open Source

`curl --remote-name http://packages.confluent.io/archive/4.1/confluent-oss-4.1.1-2.11.tar.gz`

`tar -xzf confluent-oss-4.1.1-2.11.tar.gz`

2. Démarrer la plateforme Confluent

`cd <path-to-confluent>`

`bin/confluent start`

3. Créer un topic input **test**

`bin/kafka-topics --zookeeper localhost:2181 --create --topic test --partitions 1 --replication-factor 1`

4. Vérifier que le topic soit bien présent

`bin/kafka-topics --list --zookeeper localhost:2181`

5. Envoyer quelques messages

`bin/kafka-console-producer --broker-list localhost:9092 --topic test`

6. Consommer les messages

`bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning`

# Mac OS
