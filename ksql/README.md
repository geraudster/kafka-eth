# KSQL

## Objectif

L'objectif de ce TP va être d'utiliser [KSQL](https://docs.confluent.io/current/quickstart/ce-quickstart.html) pour effectuer de l'analyse de streams.

## TP
1. Démarrer le client KSQL :

**Windows**
```
docker-compose exec ksql-cli ksql http://ksql-server:8088
```
**Linux**
```
bin/ksql
```

2. Créer le stream `transactions` basé sur le topic `transactions`
3. Afficher 3 éléments de ce streams
4. Afficher les éléments qui ont leur `value` (=amount) supérieure à 1 ETH
5. Créer une query persistante à partir de la query précédente
6. Calculer le montant moyen par adresse par minutes (indice : vous allez devoir créer une TABLE et une WINDOW TUMBLING)
