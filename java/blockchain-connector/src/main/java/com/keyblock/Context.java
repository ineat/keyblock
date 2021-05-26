package com.keyblock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class Context {

    private static final Logger log = LogManager.getLogger(Context.class);

    /**
     *
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
     *
     */
    private static final String ENDPOINT_URL_KEY ="endpoint.url";

    /**
     *
     */
    private static final String ETHEREUM_ADDRESS_KEY ="ethereum.address";

    /**
     *
     */
    private static final String ETHEREUM_PUBLIC_KEY_KEY ="ethereum.publicKey" ;

    /**
     *
     */
    private static final String ETHEREUM_PRIVATE_KEY_KEY ="ethereum.privateKey";

    /**
     *
     */
    private static final String CONTRACT_ADDRESS_KEY ="contract.address";

    /**
     *
     */
    private static Properties properties;

    /**
     *
     * @param flavor
     */
    public Context(ContextFlavor flavor) {

        assert(flavor != null);

        this.properties = new Properties();
        try {
            log.debug("Read properties from: "+flavor.getFile());
            InputStream propertiesStream = new FileInputStream(flavor.getFile());
            properties.load(propertiesStream);
        } catch (IOException e) {
            log.error("Fail to load properties: "+e.getMessage());
        }
        log.debug("properties: "+properties);
    }

    /**
     *
     * @return
     */
    public String getEndpointUrl() {
        return this.properties.getProperty(ENDPOINT_URL_KEY);
    }


    /**
     *
     * @return
     */
    public String getEthereumAddress() {
        return this.properties.getProperty(ETHEREUM_ADDRESS_KEY);
    }

    /**
     *
     * @return
     */
    public String getEthereumPublicKey() {
        return this.properties.getProperty(ETHEREUM_PUBLIC_KEY_KEY);
    }

    /**
     *
     * @return
     */
    public String getEthereumPrivateKey() {
        // TODO move to secret manager instead of properties file
        return this.properties.getProperty(ETHEREUM_PRIVATE_KEY_KEY);
    }

    /**
     *
     * @return
     */
    public String getContractAddress() {
        return this.properties.getProperty(CONTRACT_ADDRESS_KEY);
    }
}
