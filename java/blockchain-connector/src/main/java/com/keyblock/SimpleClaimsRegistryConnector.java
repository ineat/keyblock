package com.keyblock;

import com.keyblock.api.Claim;
import com.keyblock.contract.SimpleClaimsRegistry;
import com.keyblock.observable.TransactionNotifier;
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
import org.web3j.protocol.exceptions.TransactionException;
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
public class SimpleClaimsRegistryConnector extends TransactionNotifier implements ClaimsRegistryInterface {

    private static final Logger log = LogManager.getLogger(SimpleClaimsRegistryConnector.class);

    /**
     * @org.web3j.protocol.Web3j object that wraps blockchain RPC
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

    private BlockchainContext context;

    /**
     * Create a BlockchainConnector with default parameters
     */
    public SimpleClaimsRegistryConnector(BlockchainContext context) {
        this(context.getEndpointUrl()
        , context.getContractAddress()
        , context.getEthereumAddress()
        , context.getEthereumPrivateKey());

        this.context = context;

        // for the notifier
        this.setWeb3j(this.web3j);
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
        log.info("Connected, current head: "+getBlockNumber().getBlockNumber());
    }

    /**
     * Load @ClaimsRegistry contract at given address for given account
     * @param contractAddress @ClaimsRegistry smart contract address to use
     */
    private void loadContract(String contractAddress) {
        assert (this.web3j != null);
        assert (this.credentials != null);

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

    @Override
    public Claim getClaim(String subjectAddress, String claimId) {
        log.info("get "+claimId+" for "+subjectAddress);
        if(subjectAddress == null || subjectAddress.isEmpty() || claimId == null || claimId.isEmpty()){
            log.error("No data, no claim !");
            return null;
        }

        try {
            Tuple3<BigInteger, String, String> result = this.contract.getClaim(subjectAddress, claimId).send();
            if(result != null) {
                // Smart contract returns 0 = success
                if (result.component1().toString().equals("0")) {
                    return new Claim(subjectAddress
                            , result.component3()
                            , null
                            , null
                            , claimId
                            , result.component2());
                }
                else {
                    log.error(result.component2());
                }
            }
        } catch (Exception e) {
            log.error("Claim not found for "+subjectAddress);
        }

        return null;
    }

    /**
     * Build a raw transaction from given data
     * @param subjectAddress
     * @param claimId
     * @param claimValue
     * @return the hash of transaction
     */
    private String sendClaimTransaction(String subjectAddress, String claimId, String claimValue) {
        String claimSignature = "";

        try {
            // compute nonce
            EthGetTransactionCount ethGetTransactionCount = this.web3j.ethGetTransactionCount(
                    context.getEthereumAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
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
                    ,this.contract.getContractAddress()
                    ,BigInteger.ZERO
                    ,encodedFunction);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTx, this.credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = this.web3j.ethSendRawTransaction(hexValue).send();
            if(ethSendTransaction.getError() != null)
                log.error(ethSendTransaction.getError().getMessage());

            String transactionHash = ethSendTransaction.getTransactionHash();
            log.info("Tx hash: "+transactionHash);

            return transactionHash;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public com.keyblock.api.TransactionReceipt waitForReceipt(String transactionHash) {
        TransactionReceiptProcessor receiptProcessor =
                new PollingTransactionReceiptProcessor(this.web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY,
                        TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

        TransactionReceipt txReceipt = null;
        try {
            txReceipt = receiptProcessor.waitForTransactionReceipt(transactionHash);
            log.info("Block number: "+txReceipt.getBlockNumber());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }

        return com.keyblock.api.TransactionReceipt.fromWeb3TransactionReceipt(txReceipt);
    }

    @Override
    public String setClaimAsync(String subjectAddress, String claimId, String claimValue) {
        // Send tx and return hash
        return sendClaimTransaction(subjectAddress,claimId, claimValue);
    }

    @Override
    public com.keyblock.api. TransactionReceipt setClaimSync(String subjectAddress, String claimId, String claimValue) {

        // Send tx and user hash to wait for receipt
        String transactionHash = sendClaimTransaction(subjectAddress,claimId, claimValue);
        return waitForReceipt(transactionHash);
    }
}

