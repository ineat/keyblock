package com.keyblock;

import com.keyblock.contract.ClaimsRegistry;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Connection wrapper to use @ClaimsRegistry
 */
public class ClaimsRegistryConnector implements ClaimsRegistryInterface {
    /**
     * The @Web3j object that wraps blockchain RPC
     */
    private Web3j web3j;

    /**
     * @ClaimsRegistry smart contract object
     */
    private ClaimsRegistry contract;

    /**
     * @Credential that represents the Ethereum account used to create transactions
     */
    private Credentials credentials;

    private static class DefaultParams {

        // TODO move to secret manager
        private static String endpointUrl = "https://f89ad600453b458d8dd44554ab59500a@ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98";
        private static String ethereumPublicKey = "0x41f6B225846863E3C037e92F229cD40f5d575258";
        private static String ethereumPrivateKey = "85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723";
        private static String contractAddress = "0xad9388311e96031d9cF2D1370826D8940d057362";
    }

    /**
     * Create a BlockchainConnector with default parameters
     */
    public ClaimsRegistryConnector() {
        this(DefaultParams.endpointUrl
            ,DefaultParams.contractAddress
            ,DefaultParams.ethereumPublicKey
            ,DefaultParams.ethereumPrivateKey);
    }

    /**
     * Create a BlockchainCoonector with provided parameters
     * @param endpointUrl RPC endpoint of Ethereum node to connect to
     * @param contractAddress @ClaimsRegistry smart contract address to use
     * @param address public address of account used to create transactions
     * @param privateKey private key of account used to sign transactions
     */
    public ClaimsRegistryConnector(String endpointUrl, String contractAddress, String address, String privateKey) {
        connection(endpointUrl);
        this.credentials =  Credentials.create(privateKey, address);
        loadContract(contractAddress);
    }

    /**
     * Create a connection to given endpoint
     * @param endpointUrl RPC endpoint of Ethereum node to connect to
     */
    private void connection(String endpointUrl) {
        this.web3j = Web3j.build(new HttpService(endpointUrl));
        System.out.println("Connected, get current head: "+getBlockNumber().getBlockNumber());
    }

    /**
     * Load @ClaimsRegistry contract at given address for given account
     * @param contractAddress @ClaimsRegistry smart contract address to use
     */
    private void loadContract(String contractAddress) {
        assert (web3j != null);
        assert (credentials != null);
        ContractGasProvider cgp = new DefaultGasProvider();
        this.contract = ClaimsRegistry.load(contractAddress, this.web3j, this.credentials, cgp);
        System.out.println("Contract loaded: "+contract.getContractAddress());
        try {
            System.out.println("Contract check: "+contract.isValid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get current head block number, a blockchain way to ping
     * @return the blockchain head of node we are connected to
     */
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

    @Override
    public Claim getClaim(String subjectAddress, String claimId) {
        return null;
    }

    @Override
    public void setClaim(String subjectAddress, String claimId, String claimValue) {
        
    }

    @Override
    public TransactionReceipt setClaimSync(String subjectAddress, String claimId, String claimValue) {
        return null;
    }

    @Override
    public String setClaimAsync(String subjectAddress, String claimId, String claimValue) {
        return null;
    }
}
