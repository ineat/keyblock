package com.keyblock.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class SignatureHelper extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061036d806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806376cd7cbc1461003b578063eedaa50014610057575b600080fd5b61005560048036038101906100509190610155565b610087565b005b610071600480360381019061006c9190610211565b61008b565b60405161007e91906102b9565b60405180910390f35b5050565b6000600185858585604051600081526020016040526040516100b094939291906102f2565b6020604051602081039080840390855afa1580156100d2573d6000803e3d6000fd5b505050602060405103519050949350505050565b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b60008083601f840112610115576101146100f0565b5b8235905067ffffffffffffffff811115610132576101316100f5565b5b60208301915083600182028301111561014e5761014d6100fa565b5b9250929050565b6000806020838503121561016c5761016b6100e6565b5b600083013567ffffffffffffffff81111561018a576101896100eb565b5b610196858286016100ff565b92509250509250929050565b6000819050919050565b6101b5816101a2565b81146101c057600080fd5b50565b6000813590506101d2816101ac565b92915050565b600060ff82169050919050565b6101ee816101d8565b81146101f957600080fd5b50565b60008135905061020b816101e5565b92915050565b6000806000806080858703121561022b5761022a6100e6565b5b6000610239878288016101c3565b945050602061024a878288016101fc565b935050604061025b878288016101c3565b925050606061026c878288016101c3565b91505092959194509250565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006102a382610278565b9050919050565b6102b381610298565b82525050565b60006020820190506102ce60008301846102aa565b92915050565b6102dd816101a2565b82525050565b6102ec816101d8565b82525050565b600060808201905061030760008301876102d4565b61031460208301866102e3565b61032160408301856102d4565b61032e60608301846102d4565b9594505050505056fea264697066735822122077a8e77ab6a51851f41b25f332fdc715e78f75c7e6850c34f0891c7f0094d3c264736f6c63430008090033";

    public static final String FUNC_CHECKSIGNATURE = "checkSignature";

    public static final String FUNC_SIGN = "sign";

    @Deprecated
    protected SignatureHelper(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SignatureHelper(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SignatureHelper(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SignatureHelper(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> checkSignature(byte[] msgHash, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(FUNC_CHECKSIGNATURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(msgHash), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> sign(byte[] data) {
        final Function function = new Function(
                FUNC_SIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SignatureHelper load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SignatureHelper(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SignatureHelper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SignatureHelper(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SignatureHelper load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SignatureHelper(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SignatureHelper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SignatureHelper(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SignatureHelper> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SignatureHelper.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SignatureHelper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SignatureHelper.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SignatureHelper> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SignatureHelper.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SignatureHelper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SignatureHelper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
