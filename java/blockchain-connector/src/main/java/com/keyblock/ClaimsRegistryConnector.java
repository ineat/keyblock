package com.keyblock;

import com.keyblock.model.Claim;
import com.keyblock.blockchain.CustomGasProvider;
import com.keyblock.blockchain.SmartContract;
import com.keyblock.contract.ClaimsRegistry;
import com.keyblock.model.TxReceipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;

import org.web3j.tuples.generated.Tuple8;


import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * Connection wrapper to use @ClaimsRegistry
 */
public class ClaimsRegistryConnector extends SmartContract implements ClaimsRegistryInterface {

    private static final Logger log = LogManager.getLogger(ClaimsRegistryConnector.class);

    /**
     * @ClaimsRegistry smart contract object
     */
    private ClaimsRegistry contract;

    /**
     * Create a BlockchainCoonector with provided parameters
     * @param endpointUrl RPC endpoint of Ethereum node to connect to
     * @param contractAddress @ClaimsRegistry smart contract address to use
     * @param address public address of account used to create transactions
     * @param privateKey private key of account used to sign transactions
     */
    public ClaimsRegistryConnector(String endpointUrl, String contractAddress, String address, String privateKey) {
        super(endpointUrl, contractAddress, address, privateKey);
        loadContract();

        super.setWeb3j(this.web3j);
        this.gasProvider = new CustomGasProvider();
    }

    /**
     * Load @ClaimsRegistry contract at given address for given account
     */
    private void loadContract() {
        assert (web3j != null);
        assert (credentials != null);
        this.contract = ClaimsRegistry.load(connection.getContractAddress(), this.web3j, this.credentials, gasProvider);
        System.out.println("Contract loaded: "+contract.getContractAddress());
        try {
            System.out.println("Contract check: "+contract.isValid()); // TODO find out how it works
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Claim getClaim(String subjectAddress, String claimId) {
        log.info("get "+claimId+" for "+subjectAddress);
        if(subjectAddress == null || subjectAddress.isEmpty() || claimId == null || claimId.isEmpty()){
            log.error("No data, no claim !");
            return null;
        }

        try {
            Tuple8<BigInteger, String, String, String, BigInteger, byte[], String, String> result = this.contract.getClaim(subjectAddress, claimId).send();
            // 1: return code
            // 2: return message
            // 3: subject address
            // 4: issuer address
            // 5: issuedAt
            // 6: signature
            // 7: claim key
            // 8: claim value

            if(result != null) {
                // Smart contract returns 0 = success
                if (result.component1().toString().equals("0")) {
                    return new Claim(subjectAddress
                            , result.component3() // issuer address
                            , Instant.ofEpochSecond(result.component5().longValue()) // issued at
                            , result.component6().toString() // signature
                            , claimId
                            , result.component8());
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
     private String sendClaimTransaction(String subjectAddress, String claimId, String claimValue) throws IOException, ExecutionException, InterruptedException {

         // build function call
         Function function = new Function(
                 "setClaim",
                 Arrays.asList(new Address(subjectAddress), new Utf8String(claimId), new Utf8String(claimValue), new DynamicBytes("".getBytes())), // TODO compute signature
                 Collections.emptyList());

         return callContractFunction(function);
     }

    @Override
    public String setClaimAsync(String subjectAddress, String claimId, String claimValue) throws IOException, ExecutionException, InterruptedException {

        // build function call
        Function function = new Function(
                "setClaim",
                Arrays.asList(new Address(subjectAddress), new Utf8String(claimId), new Utf8String(claimValue), new DynamicBytes("".getBytes())), // TODO compute signature
                Collections.emptyList());

        return callContractFunction(function);
    }

    @Override
    public TxReceipt setClaimSync(String subjectAddress, String claimId, String claimValue) throws IOException, ExecutionException, InterruptedException {

        // build function call
        Function function = new Function(
                "setClaim",
                Arrays.asList(new Address(subjectAddress), new Utf8String(claimId), new Utf8String(claimValue), new DynamicBytes("".getBytes())), // TODO compute signature
                Collections.emptyList());

        return callContractfunctionSync(function);
    }
}
