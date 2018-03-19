```
docker pull ethereum/client-go:alpine
docker run -it -p 8545:8545 -p 30303:30303 ethereum/client-go:alpine --rpc --rpcaddr "0.0.0.0" --fast --cache=512 --rpcapi personal,db,eth,net,web3 --testnet
```

