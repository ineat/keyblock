package com.keyblock;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Application {
    public static void main(String[] args) {
        BlockchainConnector bc = new BlockchainConnector("https://f89ad600453b458d8dd44554ab59500a@ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98");
        System.out.println(bc.getBlockNumber().getBlockNumber());
    }
}
