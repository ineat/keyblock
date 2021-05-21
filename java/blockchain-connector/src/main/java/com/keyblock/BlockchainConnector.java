package com.keyblock;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.concurrent.ExecutionException;

public class BlockchainConnector {

    private Web3j web3j;
    ClaimsRegistry contract;
    private String contractAddress = "0x864cf6e7547C37B06c9C49fa4B3D681B273E8E0b";

    // TODO move to secret manager
    private String endpointUrl = "https://f89ad600453b458d8dd44554ab59500a@ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98";
    private String ethereumPublicKey = "0x41f6B225846863E3C037e92F229cD40f5d575258";
    private String ethereumPrivateKey = "85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723";

    public BlockchainConnector() {
        connection();
        loadContract(contractAddress);
    }

    private void connection() {
        this.web3j = Web3j.build(new HttpService(this.endpointUrl));
        System.out.println("Connected, get current head: "+getBlockNumber().getBlockNumber());
    }

    private void loadContract(String contractAddress) {
        Credentials credentials = Credentials.create(ethereumPrivateKey, ethereumPublicKey);
        ContractGasProvider cgp = new DefaultGasProvider();
        this.contract = ClaimsRegistry.load(contractAddress, web3j, credentials, cgp);
        System.out.println("Contract loaded: "+contract.getContractAddress());
    }

    public EthBlockNumber getBlockNumber() {
        EthBlockNumber result = new EthBlockNumber();
        try {
            result = this.web3j.ethBlockNumber()
                    .sendAsync()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getClaim(String subjectAddress, String claimId) {
       /* TransactionReceipt transactionReceipt = contract.getClaim(
                <param1>,
             ...).send();
        */
        return null;
    }
}
