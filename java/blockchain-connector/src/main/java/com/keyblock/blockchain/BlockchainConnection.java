package com.keyblock.blockchain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manage blockchain context for the connector
 */
public class BlockchainConnection {

    private static final Logger log = LogManager.getLogger(BlockchainConnection.class);


    /**
     * Blockchain RPC endpoint
     */
    private String endpointUrl;


    /**
     * Chain ID
     */
    private Integer chainId;

    /**
     * Ethereum address to use to create transactions
     */
    private String ethereumAddress;

    /**
     * Private key of Ethereum address used to create transactions
     */
    private String ethereumPrivateKey;

    private String ethereumPublicKey;

    /**
     * Smart contract address
     */
    private String contractAddress;

    /**
     * Create a context
     */
    public BlockchainConnection(String endpointUrl, String contractAddress, String ethereumAddress, String ethereumPrivateKey) {

        this.endpointUrl = endpointUrl;
        this.contractAddress = contractAddress;
        this.ethereumAddress = ethereumAddress;
        this.ethereumPrivateKey = ethereumPrivateKey;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getEthereumAddress() {
        return ethereumAddress;
    }

    public void setEthereumAddress(String ethereumAddress) {
        this.ethereumAddress = ethereumAddress;
    }

    public String getEthereumPrivateKey() {
        return ethereumPrivateKey;
    }

    public void setEthereumPrivateKey(String ethereumPrivateKey) {
        this.ethereumPrivateKey = ethereumPrivateKey;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getEthereumPublicKey() {
        return ethereumPublicKey;
    }

    public void setEthereumPublicKey(String ethereumPublicKey) {
        this.ethereumPublicKey = ethereumPublicKey;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }
}
