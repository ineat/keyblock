package com.keyblock.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple6;
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
public class SimpleClaimsRegistry extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611551806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630c1f16d91461005c5780634f6016e71461007857806358924b81146100ad5780635d9026b2146100df578063d690d94d146100fb575b600080fd5b61007660048036038101906100719190610ef9565b610117565b005b610092600480360381019061008d919061105d565b6103d5565b6040516100a4969594939291906111be565b60405180910390f35b6100c760048036038101906100c29190611234565b61060c565b6040516100d693929190611294565b60405180910390f35b6100f960048036038101906100f491906112d2565b610983565b005b61011560048036038101906101109190611346565b610b23565b005b60006040518060c001604052808873ffffffffffffffffffffffffffffffffffffffff1681526020013373ffffffffffffffffffffffffffffffffffffffff16815260200142815260200183815260200187878080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050815260200185858080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050508152509050806000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020878760405161024b929190611427565b908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301908051906020019061030d929190610b38565b50608082015181600401908051906020019061032a929190610bbe565b5060a0820151816005019080519060200190610347929190610bbe565b50905050858560405161035b929190611427565b60405180910390208773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fd0e66d7a7ad5b30e8c8833da0fa84893d3d777fa4767b331c91941fd458d1e028787426040516103c49392919061146d565b60405180910390a450505050505050565b600060205281600052604060002081805160208101820180518482526020830160208501208183528095505050505050600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169080600201549080600301805461046d906114ce565b80601f0160208091040260200160405190810160405280929190818152602001828054610499906114ce565b80156104e65780601f106104bb576101008083540402835291602001916104e6565b820191906000526020600020905b8154815290600101906020018083116104c957829003601f168201915b5050505050908060040180546104fb906114ce565b80601f0160208091040260200160405190810160405280929190818152602001828054610527906114ce565b80156105745780601f1061054957610100808354040283529160200191610574565b820191906000526020600020905b81548152906001019060200180831161055757829003601f168201915b505050505090806005018054610589906114ce565b80601f01602080910402602001604051908101604052809291908181526020018280546105b5906114ce565b80156106025780601f106105d757610100808354040283529160200191610602565b820191906000526020600020905b8154815290600101906020018083116105e557829003601f168201915b5050505050905086565b600060606000806000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208686604051610661929190611427565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160028201548152602001600382018054610740906114ce565b80601f016020809104026020016040519081016040528092919081815260200182805461076c906114ce565b80156107b95780601f1061078e576101008083540402835291602001916107b9565b820191906000526020600020905b81548152906001019060200180831161079c57829003601f168201915b505050505081526020016004820180546107d2906114ce565b80601f01602080910402602001604051908101604052809291908181526020018280546107fe906114ce565b801561084b5780601f106108205761010080835404028352916020019161084b565b820191906000526020600020905b81548152906001019060200180831161082e57829003601f168201915b50505050508152602001600582018054610864906114ce565b80601f0160208091040260200160405190810160405280929190818152602001828054610890906114ce565b80156108dd5780601f106108b2576101008083540402835291602001916108dd565b820191906000526020600020905b8154815290600101906020018083116108c057829003601f168201915b50505050508152505090508673ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff161461096657600160006040518060400160405280601881526020017f5375626a656374206f72206b6579206e6f7420666f756e640000000000000000815250909350935093505061097a565b60008160a001518260200151935093509350505b93509350939050565b8373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109bb57600080fd5b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208282604051610a09929190611427565b9081526020016040518091039020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160009055600382016000610a7e9190610c44565b600482016000610a8e9190610c84565b600582016000610a9e9190610c84565b50508181604051610ab0929190611427565b60405180910390208373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f74b1cd181576b817654ac78213fac8b3c1daaf5ca9986d4d13afbf6a840ecb0a42604051610b159190611500565b60405180910390a450505050565b610b31338686868686610117565b5050505050565b828054610b44906114ce565b90600052602060002090601f016020900481019282610b665760008555610bad565b82601f10610b7f57805160ff1916838001178555610bad565b82800160010185558215610bad579182015b82811115610bac578251825591602001919060010190610b91565b5b509050610bba9190610cc4565b5090565b828054610bca906114ce565b90600052602060002090601f016020900481019282610bec5760008555610c33565b82601f10610c0557805160ff1916838001178555610c33565b82800160010185558215610c33579182015b82811115610c32578251825591602001919060010190610c17565b5b509050610c409190610cc4565b5090565b508054610c50906114ce565b6000825580601f10610c625750610c81565b601f016020900490600052602060002090810190610c809190610cc4565b5b50565b508054610c90906114ce565b6000825580601f10610ca25750610cc1565b601f016020900490600052602060002090810190610cc09190610cc4565b5b50565b5b80821115610cdd576000816000905550600101610cc5565b5090565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610d2082610cf5565b9050919050565b610d3081610d15565b8114610d3b57600080fd5b50565b600081359050610d4d81610d27565b92915050565b600080fd5b600080fd5b600080fd5b60008083601f840112610d7857610d77610d53565b5b8235905067ffffffffffffffff811115610d9557610d94610d58565b5b602083019150836001820283011115610db157610db0610d5d565b5b9250929050565b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610e0682610dbd565b810181811067ffffffffffffffff82111715610e2557610e24610dce565b5b80604052505050565b6000610e38610ce1565b9050610e448282610dfd565b919050565b600067ffffffffffffffff821115610e6457610e63610dce565b5b610e6d82610dbd565b9050602081019050919050565b82818337600083830152505050565b6000610e9c610e9784610e49565b610e2e565b905082815260208101848484011115610eb857610eb7610db8565b5b610ec3848285610e7a565b509392505050565b600082601f830112610ee057610edf610d53565b5b8135610ef0848260208601610e89565b91505092915050565b60008060008060008060808789031215610f1657610f15610ceb565b5b6000610f2489828a01610d3e565b965050602087013567ffffffffffffffff811115610f4557610f44610cf0565b5b610f5189828a01610d62565b9550955050604087013567ffffffffffffffff811115610f7457610f73610cf0565b5b610f8089828a01610d62565b9350935050606087013567ffffffffffffffff811115610fa357610fa2610cf0565b5b610faf89828a01610ecb565b9150509295509295509295565b600067ffffffffffffffff821115610fd757610fd6610dce565b5b610fe082610dbd565b9050602081019050919050565b6000611000610ffb84610fbc565b610e2e565b90508281526020810184848401111561101c5761101b610db8565b5b611027848285610e7a565b509392505050565b600082601f83011261104457611043610d53565b5b8135611054848260208601610fed565b91505092915050565b6000806040838503121561107457611073610ceb565b5b600061108285828601610d3e565b925050602083013567ffffffffffffffff8111156110a3576110a2610cf0565b5b6110af8582860161102f565b9150509250929050565b6110c281610d15565b82525050565b6000819050919050565b6110db816110c8565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561111b578082015181840152602081019050611100565b8381111561112a576000848401525b50505050565b600061113b826110e1565b61114581856110ec565b93506111558185602086016110fd565b61115e81610dbd565b840191505092915050565b600081519050919050565b600082825260208201905092915050565b600061119082611169565b61119a8185611174565b93506111aa8185602086016110fd565b6111b381610dbd565b840191505092915050565b600060c0820190506111d360008301896110b9565b6111e060208301886110b9565b6111ed60408301876110d2565b81810360608301526111ff8186611130565b905081810360808301526112138185611185565b905081810360a08301526112278184611185565b9050979650505050505050565b60008060006040848603121561124d5761124c610ceb565b5b600061125b86828701610d3e565b935050602084013567ffffffffffffffff81111561127c5761127b610cf0565b5b61128886828701610d62565b92509250509250925092565b60006060820190506112a960008301866110d2565b81810360208301526112bb8185611185565b90506112ca60408301846110b9565b949350505050565b600080600080606085870312156112ec576112eb610ceb565b5b60006112fa87828801610d3e565b945050602061130b87828801610d3e565b935050604085013567ffffffffffffffff81111561132c5761132b610cf0565b5b61133887828801610d62565b925092505092959194509250565b60008060008060006060868803121561136257611361610ceb565b5b600086013567ffffffffffffffff8111156113805761137f610cf0565b5b61138c88828901610d62565b9550955050602086013567ffffffffffffffff8111156113af576113ae610cf0565b5b6113bb88828901610d62565b9350935050604086013567ffffffffffffffff8111156113de576113dd610cf0565b5b6113ea88828901610ecb565b9150509295509295909350565b600081905092915050565b600061140e83856113f7565b935061141b838584610e7a565b82840190509392505050565b6000611434828486611402565b91508190509392505050565b600061144c8385611174565b9350611459838584610e7a565b61146283610dbd565b840190509392505050565b60006040820190508181036000830152611488818587611440565b905061149760208301846110d2565b949350505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806114e657607f821691505b602082108114156114fa576114f961149f565b5b50919050565b600060208201905061151560008301846110d2565b9291505056fea264697066735822122082faccdde04208bb69141a8bf0a5ffa13cdecf1bc9032bd03c2b03a0e2fe5e2a64736f6c63430008090033";

    public static final String FUNC_GETCLAIM = "getClaim";

    public static final String FUNC_REGISTRY = "registry";

    public static final String FUNC_REMOVECLAIM = "removeClaim";

    public static final String FUNC_SETCLAIM = "setClaim";

    public static final String FUNC_SETSELFCLAIM = "setSelfClaim";

    public static final Event CLAIMREMOVED_EVENT = new Event("ClaimRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CLAIMSET_EVENT = new Event("ClaimSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected SimpleClaimsRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleClaimsRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SimpleClaimsRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SimpleClaimsRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ClaimRemovedEventResponse> getClaimRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLAIMREMOVED_EVENT, transactionReceipt);
        ArrayList<ClaimRemovedEventResponse> responses = new ArrayList<ClaimRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClaimRemovedEventResponse typedResponse = new ClaimRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.issuer = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.subject = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.key = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.removedAt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClaimRemovedEventResponse> claimRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClaimRemovedEventResponse>() {
            @Override
            public ClaimRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLAIMREMOVED_EVENT, log);
                ClaimRemovedEventResponse typedResponse = new ClaimRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.issuer = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.subject = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.key = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.removedAt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClaimRemovedEventResponse> claimRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMREMOVED_EVENT));
        return claimRemovedEventFlowable(filter);
    }

    public List<ClaimSetEventResponse> getClaimSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLAIMSET_EVENT, transactionReceipt);
        ArrayList<ClaimSetEventResponse> responses = new ArrayList<ClaimSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClaimSetEventResponse typedResponse = new ClaimSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.issuer = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.subject = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.key = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.updatedAt = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClaimSetEventResponse> claimSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClaimSetEventResponse>() {
            @Override
            public ClaimSetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLAIMSET_EVENT, log);
                ClaimSetEventResponse typedResponse = new ClaimSetEventResponse();
                typedResponse.log = log;
                typedResponse.issuer = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.subject = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.key = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.updatedAt = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClaimSetEventResponse> claimSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMSET_EVENT));
        return claimSetEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, String, String>> getClaim(String subject, String key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, subject), 
                new org.web3j.abi.datatypes.Utf8String(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, String, String>>(function,
                new Callable<Tuple3<BigInteger, String, String>>() {
                    @Override
                    public Tuple3<BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple6<String, String, BigInteger, byte[], String, String>> registry(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REGISTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Utf8String(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, BigInteger, byte[], String, String>>(function,
                new Callable<Tuple6<String, String, BigInteger, byte[], String, String>>() {
                    @Override
                    public Tuple6<String, String, BigInteger, byte[], String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, BigInteger, byte[], String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (byte[]) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> removeClaim(String issuer, String subject, String key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVECLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, issuer), 
                new org.web3j.abi.datatypes.Address(160, subject), 
                new org.web3j.abi.datatypes.Utf8String(key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setClaim(String subject, String key, String value, byte[] signature) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, subject), 
                new org.web3j.abi.datatypes.Utf8String(key), 
                new org.web3j.abi.datatypes.Utf8String(value), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSelfClaim(String key, String value, byte[] signature) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSELFCLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(key), 
                new org.web3j.abi.datatypes.Utf8String(value), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SimpleClaimsRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleClaimsRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SimpleClaimsRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleClaimsRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SimpleClaimsRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SimpleClaimsRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SimpleClaimsRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SimpleClaimsRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SimpleClaimsRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleClaimsRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SimpleClaimsRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleClaimsRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleClaimsRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleClaimsRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleClaimsRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleClaimsRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ClaimRemovedEventResponse extends BaseEventResponse {
        public String issuer;

        public String subject;

        public byte[] key;

        public BigInteger removedAt;
    }

    public static class ClaimSetEventResponse extends BaseEventResponse {
        public String issuer;

        public String subject;

        public byte[] key;

        public String value;

        public BigInteger updatedAt;
    }
}
