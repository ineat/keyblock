package com.keyblock;

import com.keyblock.contract.SimpleClaimsRegistry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Connection wrapper to use @SimpleClaimsRegistry
 */
public class SimpleClaimsRegistryConnector {

    private static final Logger log = LogManager.getLogger(SimpleClaimsRegistryConnector.class);

    /**
     * The @Web3j object that wraps blockchain RPC
     */
    private Web3j web3j;

    /**
     * @SimpleClaimsRegistry smart contract object
     */
    private SimpleClaimsRegistry contract;

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
    public SimpleClaimsRegistryConnector() {
        this(DefaultParams.endpointUrl
            , DefaultParams.contractAddress
            , DefaultParams.ethereumPublicKey
            , DefaultParams.ethereumPrivateKey);
    }

    /**
     * Create a BlockchainCoonector with provided parameters
     * @param endpointUrl RPC endpoint of Ethereum node to connect to
     * @param contractAddress @ClaimsRegistry smart contract address to use
     * @param address public address of account used to create transactions
     * @param privateKey private key of account used to sign transactions
     */
    public SimpleClaimsRegistryConnector(String endpointUrl, String contractAddress, String address, String privateKey) {
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
        log.info("Connected, get current head: "+getBlockNumber().getBlockNumber());
    }

    /**
     * Load @ClaimsRegistry contract at given address for given account
     * @param contractAddress @ClaimsRegistry smart contract address to use
     */
    private void loadContract(String contractAddress) {
        assert (web3j != null);
        assert (credentials != null);
        ContractGasProvider cgp = new DefaultGasProvider();
        this.contract = SimpleClaimsRegistry.load(contractAddress, this.web3j, this.credentials, cgp);
        log.info("Contract loaded: "+contract.getContractAddress());
        try {
            log.info("Contract check: "+contract.isValid());
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

    /**
     * Read a @Claim from smart contract
     * @param subjectAddress address of user
     * @param claimId id of claim
     * @return the @Claim that represents the stored value, null il not found
     */
    public Claim getClaim(String subjectAddress, String claimId) {

        try {
            Tuple3<BigInteger, String, String> result = contract.getClaim(subjectAddress, claimId).send();

            return new Claim(subjectAddress
                            , result.component3()
                            , 0
                            , null
                            , claimId
                            , result.component2());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
