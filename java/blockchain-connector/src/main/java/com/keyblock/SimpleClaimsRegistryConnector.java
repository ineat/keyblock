package com.keyblock;

import com.keyblock.contract.SimpleClaimsRegistry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
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

    private TransactionManager txManager;

    private static class DefaultParams {

        // TODO move to secret manager
        private static String endpointUrl = "https://f89ad600453b458d8dd44554ab59500a@ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98";
        private static String ethereumAddress = "0x41f6B225846863E3C037e92F229cD40f5d575258";
        private static String ethereumPublicKey = "";
        private static String ethereumPrivateKey = "85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723";
        private static String contractAddress = "0xad9388311e96031d9cF2D1370826D8940d057362";
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
     * Create a BlockchainCoonector with provided parameters
     * @param endpointUrl RPC endpoint of Ethereum node to connect to
     * @param contractAddress @ClaimsRegistry smart contract address to use
     * @param address public address of account used to create transactions
     * @param privateKey private key of account used to sign transactions
     */
    public SimpleClaimsRegistryConnector(String endpointUrl, String contractAddress, String address, String privateKey) {
        connection(endpointUrl);

        this.credentials =  Credentials.create(privateKey, this.getPublicKeyInHex(privateKey));
        /*log.info("Address: "+credentials.getAddress());
        log.info("Pub k: "+credentials.getEcKeyPair().getPublicKey());
        log.info("Priv k: "+credentials.getEcKeyPair().getPrivateKey());*/

        this.txManager = new ClientTransactionManager(this.web3j, DefaultParams.ethereumAddress);
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
        this.contract = SimpleClaimsRegistry.load(contractAddress, this.web3j, this.txManager, cgp);
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
                            , null
                            , null
                            , claimId
                            , result.component2());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setClaim(String subjectAddress, String claimId, String claimValue) {
        String claimSignature = "";

        try {
 /*           RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);
            RawTransaction rawTx = createRawTx(subjectAddress, claimId, claimValue);
            String signedMessage = txManager.sign(rawTx);

           txManager.sendCall(DefaultParams.contractAddress, signedMessage, );
            EthSendTransaction ethSendTx = txManager.signAndSend(rawTx);

            Optional<TransactionReceipt> transactionReceipt =
                    web3j.ethGetTransactionReceipt(ethSendTx.getTransactionHash()).send().getTransactionReceipt();

            org.web3j.protocol.core.methods.response.EthSendTransaction transactionResponse =
                    web3j.ethSendRawTransaction(signedMessage).sendAsync().get();

            String transactionHash = transactionResponse.getTransactionHash();
*/

            Function function = new Function(
                    "setClaim",
                    Arrays.asList(new Address(subjectAddress), new Utf8String(claimId), new Utf8String(claimValue), new DynamicBytes("".getBytes())),
                    Collections.emptyList());
/*
            String encodedFunction = FunctionEncoder.encode(function);

            EthSendTransaction transactionResponse = txManager.sendTransaction(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, DefaultParams.contractAddress, encodedFunction, new BigInteger("0"), false);
*/


            //Encode function values in transaction data format
            String txData = FunctionEncoder.encode(function);

            TransactionManager txManager = new FastRawTransactionManager(web3j, credentials);

            String txHash = txManager.sendTransaction(new BigInteger("900000000"), new BigInteger("410000000000"),
                    DefaultParams.contractAddress, txData, BigInteger.ZERO).getTransactionHash();

            log.info("tx: "+txHash);

            TransactionReceiptProcessor receiptProcessor =
                    new PollingTransactionReceiptProcessor(web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY,
                            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

            TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(txHash);

           log.info("txReceipt: "+txReceipt);
           /*  log.info("raw: "+transactionResponse.getRawResponse());
            log.info("res: "+transactionResponse.getResult());*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RawTransaction createRawTx(String subjectAddress, String claimId, String claimValue) throws ExecutionException, InterruptedException {

        // compute Claim signature
        String signature = "";

        // compute nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                DefaultParams.ethereumAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        log.debug("nonce: "+nonce.toString());

        // create tx data
        Function function = new Function(
                "setClaim",
                Arrays.asList(new Address(subjectAddress), new Utf8String(claimId), new Utf8String(claimValue), new DynamicBytes(signature.getBytes())),
                Collections.emptyList());

        String encodedFunction = FunctionEncoder.encode(function);
        log.debug("encodedFunction: "+encodedFunction);

        RawTransaction rawTx = RawTransaction.createTransaction(
                nonce
                ,DefaultGasProvider.GAS_PRICE
                ,DefaultGasProvider.GAS_LIMIT
                ,DefaultParams.contractAddress
                ,encodedFunction);

        return rawTx;
    }

    public  String getPublicKeyInHex(String privateKeyInHex) {
        BigInteger privateKeyInBT = new BigInteger(privateKeyInHex, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKeyInBT);
        BigInteger publicKeyInBT = aPair.getPublicKey();
        String sPublickeyInHex = publicKeyInBT.toString(16);
        return sPublickeyInHex;
    }
}
