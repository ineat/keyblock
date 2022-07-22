package com.keyblock.blockchain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Manage blockchain context for the connector
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockchainConnection {

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
}
