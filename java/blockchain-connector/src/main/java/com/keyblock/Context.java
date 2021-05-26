package com.keyblock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Context {

    private static final Logger log = LogManager.getLogger(Context.class);

    public enum ContextFlavor {
        GANACHE("src/main/resources/ganache.properties")
        , INFURA_ROPSTEN("src/main/resources/infura-ropsten.properties");

        private final String file;

        ContextFlavor(String file) {
            this.file = file;
        }
        public String getFile() {
            return file;
        }
    }

    private static String endpointUrlKey="endpoint.url";
    private static String ethereumAddressKey="ethereum.address";
    private static String ethereumPublicKeyKey="ethereum.publicKey" ;
    private static String ethereumPrivateKeyKey="ethereum.privateKey";
    private static String contractAddressKey="contract.address";

    private static Properties properties;

    public Context(ContextFlavor flavor) {

        properties = new Properties();
        try {
            log.debug("Read properties from: "+flavor.getFile());
            InputStream propertiesStream = new FileInputStream(flavor.getFile());
            properties.load(propertiesStream);
        } catch (IOException e) {
            log.error("Fail to load properties: "+e.getMessage());
        }
        log.debug("properties: "+properties);
    }

    public String getEndpointUrl() {
        return properties.getProperty(endpointUrlKey);
    }


    public String getEthereumAddress() {
        return properties.getProperty(ethereumAddressKey);
    }

    public String getEthereumPublicKey() {
        return properties.getProperty(ethereumPublicKeyKey);
    }

    public String getEthereumPrivateKey() {
        // TODO move to secret manager instead of properties file
        return properties.getProperty(ethereumPrivateKeyKey);
    }

    public String getContractAddress() {
        return properties.getProperty(contractAddressKey);
    }
}
