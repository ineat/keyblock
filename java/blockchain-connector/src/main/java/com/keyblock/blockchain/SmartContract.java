package com.keyblock.blockchain;


import com.keyblock.model.TxReceipt;
import com.keyblock.util.CryptoUtils;
import com.keyblock.observable.TransactionNotifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public abstract class SmartContract extends TransactionNotifier  {

    private static final Logger log = LogManager.getLogger(SmartContract.class);

    /**
     * @org.web3j.protocol.Web3j object that wraps blockchain RPC
     */
    protected Web3j web3j;

    /**
     * @org.web3j.crypto.Credentials that represents the Ethereum account used to create transactions
     */
    protected Credentials credentials;

    /**
     * @org.web3j.tx.TransactionManager that handles smart contract operation
     */
    protected TransactionManager clientTxManager;

    /**
     * Gas provider, use to compute right gas amount and price for a call
     */
    protected ContractGasProvider gasProvider;

    /**
     * Connection context for blockchain endpoint, smart contract ...
     */
    protected BlockchainConnection connection;

    protected SmartContract(String endpointUrl, String contractAddress, String ethereumAddress, String ethereumPrivateKey) {
        connection = new BlockchainConnection(endpointUrl, contractAddress, ethereumAddress, ethereumPrivateKey);
        connection.setEthereumPublicKey(CryptoUtils.getPublicKeyInHex(ethereumPrivateKey));
        gasProvider = new CustomGasProvider();
        this.connection();
    }

    /**
     * Create a connection to given endpoint
     */
    protected void connection() {
        this.web3j = Web3j.build(new HttpService(connection.getEndpointUrl()));
        log.info("Connected, current head: "+getBlockNumber().getBlockNumber());
        this.credentials =  Credentials.create(connection.getEthereumPrivateKey(), connection.getEthereumPublicKey());
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
     * Send tx to a smartcontract with function call
     * @param function
     * @return the transaction hash
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String callContractFunction(Function function) throws IOException, ExecutionException, InterruptedException {

        log.info("callContractFunction");

        try {
            // compute nonce
            EthGetTransactionCount ethGetTransactionCount = this.web3j.ethGetTransactionCount(
                    connection.getEthereumAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            log.info("nonce: "+nonce.toString());

            // encode function call to tx data
            String encodedFunction = FunctionEncoder.encode(function);
            log.info("encodedFunction: "+encodedFunction);

            RawTransaction rawTx = RawTransaction.createTransaction(
                    nonce
                    ,gasProvider.getGasPrice(encodedFunction)
                    ,gasProvider.getGasLimit(encodedFunction)
                    ,this.connection.getContractAddress()
                    ,BigInteger.ZERO
                    ,encodedFunction);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTx, this.credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = this.web3j.ethSendRawTransaction(hexValue).send();
            if(ethSendTransaction.getError() != null) {
                log.error(ethSendTransaction.getError().getCode());
                log.error(ethSendTransaction.getError().getMessage());
                log.error(ethSendTransaction.getError().getData());
            }

            String transactionHash = ethSendTransaction.getTransactionHash();
            log.info("Tx hash: "+transactionHash);

            return transactionHash;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public TxReceipt waitForReceipt(String transactionHash) {
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

        return TxReceipt.fromWeb3TransactionReceipt(txReceipt);
    }

    public TxReceipt callContractfunctionSync(Function function) throws IOException, ExecutionException, InterruptedException {
        String txHash = callContractFunction(function);
        return waitForReceipt(txHash);
    }
}
