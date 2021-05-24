package com.keyblock;

import com.keyblock.contract.SimpleClaimsRegistry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * Connection wrapper to use @SimpleClaimsRegistry
 */
public class SimpleClaimsRegistryConnector implements ClaimsRegistryInterface {

    private static final Logger log = LogManager.getLogger(SimpleClaimsRegistryConnector.class);

    /**
     * The @Web3j object that wraps blockchain RPC
     */
    private Web3j web3j;

    /**
     * @com.keyblock.contract.SimpleClaimsRegistry smart contract object
     */
    private SimpleClaimsRegistry contract;

    /**
     * @org.web3j.crypto.Credentials that represents the Ethereum account used to create transactions
     */
    private Credentials credentials;

    /**
     * @org.web3j.tx.TransactionManager that handles smart contract operation
     */
    private TransactionManager clientTxManager;

    private static class DefaultParams {

        // TODO move to secret manager
        private static final String endpointUrl = "https://f89ad600453b458d8dd44554ab59500a@ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98";
        private static final String ethereumAddress = "0x41f6B225846863E3C037e92F229cD40f5d575258";
        private static final String ethereumPublicKey = "";
        private static final String ethereumPrivateKey = "85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723";
        private static final String contractAddress = "0xad9388311e96031d9cF2D1370826D8940d057362";
    }

    /**
     * Create a BlockchainConnector with default parameters
     */
    public SimpleClaimsRegistryConnector() {
        this(DefaultParams.endpointUrl
            , DefaultParams.contractAddress
            , DefaultParams.ethereumAddress
            , DefaultParams.ethereumPrivateKey);
    }

    /**
     * Create a BlockchainConnector with provided parameters
     * @param endpointUrl RPC endpoint of Ethereum node to connect to
     * @param contractAddress @ClaimsRegistry smart contract address to use
     * @param address public address of account used to create transactions
     * @param privateKey private key of account used to sign transactions
     */
    public SimpleClaimsRegistryConnector(String endpointUrl, String contractAddress, String address, String privateKey) {
        connection(endpointUrl);
        this.credentials =  Credentials.create(privateKey);
        this.clientTxManager = new ClientTransactionManager(this.web3j, address);
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
        this.contract = SimpleClaimsRegistry.load(contractAddress, this.web3j, this.clientTxManager, cgp);
        log.info("Contract loaded: "+contract.getContractAddress());

        try {
            log.info("Contract check: "+contract.isValid()); // TODO find out how it works
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
     * @param claimId key of @Claim
     * @return the @Claim that represents the stored value, null il not found
     */
    @Override
    public Claim getClaim(String subjectAddress, String claimId) {

        try {
            Tuple3<BigInteger, String, String> result = contract.getClaim(subjectAddress, claimId).send();

            return new Claim(subjectAddress
                            , result.component3()
                            , null
                            , null
                            , claimId
                            , result.component2());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Call smart contract function SimpleClaimsRegistry.setClaim to create a new @Claim
     * @param subjectAddress user address the @Claim is related to
     * @param claimId key of @Claim to set
     * @param claimValue value of @Claim to set
     */
    @Override
    public void setClaim(String subjectAddress, String claimId, String claimValue) {
        String claimSignature = "";

        try {
            // compute nonce
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    DefaultParams.ethereumAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            log.debug("nonce: "+nonce.toString());

            // build function call
            Function function = new Function(
                    "setClaim",
                    Arrays.asList(new Address(subjectAddress), new Utf8String(claimId), new Utf8String(claimValue), new DynamicBytes(claimSignature.getBytes())),
                    Collections.emptyList());

            // encode function call to tx data
            String encodedFunction = FunctionEncoder.encode(function);
            log.debug("encodedFunction: "+encodedFunction);

            RawTransaction rawTx = RawTransaction.createTransaction(
                    nonce
                    ,DefaultGasProvider.GAS_PRICE
                    ,BigInteger.valueOf(600000L) // TODO find a way to compute it more accurately
                    ,contract.getContractAddress()
                    ,BigInteger.ZERO
                    ,encodedFunction);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTx, this.credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            if(ethSendTransaction.getError() != null)
                log.error(ethSendTransaction.getError().getMessage());

            String transactionHash = ethSendTransaction.getTransactionHash();
            log.info("Tx hash: "+transactionHash);

            // Wait for receipt
           TransactionReceiptProcessor receiptProcessor =
                    new PollingTransactionReceiptProcessor(web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY,
                            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

            TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(transactionHash);
            log.info("Block number: "+txReceipt.getBlockNumber());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

