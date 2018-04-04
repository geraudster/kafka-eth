
# Web3j

We'll use web3j for consuming blockchain data: https://docs.web3j.io/getting_started.html

Kafka Connect will connect to geth client to read data. Geth client retrieves blockain data, it downloads full blockchain (several GB of data), so maybe we should deploy a client in the cloud... or use infura (https://infura.io/)

## Starting client

Start geth client, on [rinkeby](https://www.rinkeby.io/#stats):
```
docker run -it -p 8545:8545 -p 30303:30303 -v /root/ethereum:/root/.ethereum ethereum/client-go:alpine --rpc --rpcaddr "0.0.0.0" --fast --cache=512 --rpcapi personal,db,eth,net,web3 --rinkeby
```

To preserve downloaded block, you can mount `/root/.ethereum` on a local folder:
```
-v <your local path>:/root/.ethereum
```

