package com.keyblock.blockchain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manage blockchain context for the connector
 */
public class BlockchainContext {

    private static final Logger log = LogManager.getLogger(BlockchainContext.class);

    /**
     * Available contexts
     */
    public enum ContextFlavor {

        GANACHE("src/main/resources/properties/ganache.properties")
        , INFURA_ROPSTEN("src/main/resources/properties/infura-ropsten.properties")

        , CLAIMREGISTRY_INFURA_ROPSTEN("src/main/resources/properties/claimsregistry-infura-ropsten.properties")
        , SIMPLECLAIMREGISTRY_INFURA_ROPSTEN("src/main/resources/properties/simpleclaimsregistry-infura-ropsten.properties")
        , CLAIMREGISTRY_GANACHE("src/main/resources/properties/claimsregistry-infura-ropsten.properties")
        , SIMPLECLAIMREGISTRY_GANACHE("src/main/resources/properties/simpleclaimsregistry-infura-ropsten.properties");

        private final String file;

        ContextFlavor(String file) {
            this.file = file;
        }
        public String getFile() {
            return file;
        }
    }

    /**
     * Blockchain RPC endpoint
     */
    private static final String ENDPOINT_URL_KEY ="endpoint.url";

    /**
     * Ethereum address to use to create transactions
     */
    private static final String ETHEREUM_ADDRESS_KEY ="ethereum.address";

    /**
     * Public key of Ethereum address used to create transactions
     */
    private static final String ETHEREUM_PUBLIC_KEY_KEY ="ethereum.publicKey" ;

    /**
     * Private key of Ethereum address used to create transactions
     */
    private static final String ETHEREUM_PRIVATE_KEY_KEY ="ethereum.privateKey";

    /**
     * Smart contract address
     */
    private static final String CONTRACT_ADDRESS_KEY ="contract.address";

    /**
     * Set of properties initialized according to selected flavor
     */
    private static Properties properties;

    /**
     * Create a context, with a set of properties, according to given flavor
     * @param flavor the @ContextFlavor
     */
    public BlockchainContext(ContextFlavor flavor) {

        assert(flavor != null);
        loadProperties(flavor.getFile());
    }

    /**
     * Create a context with a set of properties.
     * @param propertiesFile properties file path
     */
    public BlockchainContext(String propertiesFile) {
        loadProperties(propertiesFile);
    }

    private void loadProperties(String propertiesFile) {
        this.properties = new Properties();
        try {
            log.debug("Read properties from: "+propertiesFile);
            InputStream propertiesStream = new FileInputStream(propertiesFile);
            properties.load(propertiesStream);
        } catch (IOException e) {
            log.error("Fail to load properties: "+e.getMessage());
        }
        log.debug("properties: "+properties);
    }

    /**
     *
     * @return Blockchain RPC endpoint
     */
    public String getEndpointUrl() {
        return this.properties.getProperty(ENDPOINT_URL_KEY);
    }


    /**
     *
     * @return Ethereum address to use to create transactions
     */
    public String getEthereumAddress() {
        return this.properties.getProperty(ETHEREUM_ADDRESS_KEY);
    }

    /**
     *
     * @return Public key of Ethereum address used to create transactions
     */
    public String getEthereumPublicKey() {
        return this.properties.getProperty(ETHEREUM_PUBLIC_KEY_KEY);
    }

    /**
     *
     * @return Private key of Ethereum address used to create transactions
     */
    public String getEthereumPrivateKey() {
        // TODO move to secret manager instead of properties file
        return this.properties.getProperty(ETHEREUM_PRIVATE_KEY_KEY);
    }

    /**
     *
     * @return Smart contract address
     */
    public String getContractAddress() {
        return this.properties.getProperty(CONTRACT_ADDRESS_KEY);
    }
}
