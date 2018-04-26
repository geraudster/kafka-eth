# Practice

Use [KSQL](https://docs.confluent.io/current/quickstart/ce-quickstart.html) to perform stream analysis.

Start ksql client :
```
bin/ksql
```

Then follow the steps: 

* Create a stream named `transactions` based on `transactions` topic
* Display 3 events from that stream
* Display events with `value` (=amount) greater than 1 ETH
* Create a persistent query from the previous query
* Compute average amount by address per minutes (hint: you'll have to create a TABLE, and a WINDOW TUMBLING)
