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
    public static final String BINARY = "608060405234801561001057600080fd5b506114df806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630c1f16d91461005c5780634f6016e71461007857806358924b81146100ad5780635d9026b2146100df578063d690d94d146100fb575b600080fd5b61007660048036038101906100719190610ed4565b610117565b005b610092600480360381019061008d9190610f87565b6103d5565b6040516100a496959493929190611177565b60405180910390f35b6100c760048036038101906100c29190610e7c565b61060c565b6040516100d69392919061123a565b60405180910390f35b6100f960048036038101906100f49190610e10565b610983565b005b61011560048036038101906101109190610fdb565b610b23565b005b60006040518060c001604052808873ffffffffffffffffffffffffffffffffffffffff1681526020013373ffffffffffffffffffffffffffffffffffffffff16815260200142815260200183815260200187878080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050815260200185858080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050508152509050806000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020878760405161024b92919061115e565b908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301908051906020019061030d929190610b38565b50608082015181600401908051906020019061032a929190610bbe565b5060a0820151816005019080519060200190610347929190610bbe565b50905050858560405161035b92919061115e565b60405180910390208773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fd0e66d7a7ad5b30e8c8833da0fa84893d3d777fa4767b331c91941fd458d1e028787426040516103c4939291906111ed565b60405180910390a450505050505050565b600060205281600052604060002081805160208101820180518482526020830160208501208183528095505050505050600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169080600201549080600301805461046d906113c0565b80601f0160208091040260200160405190810160405280929190818152602001828054610499906113c0565b80156104e65780601f106104bb576101008083540402835291602001916104e6565b820191906000526020600020905b8154815290600101906020018083116104c957829003601f168201915b5050505050908060040180546104fb906113c0565b80601f0160208091040260200160405190810160405280929190818152602001828054610527906113c0565b80156105745780601f1061054957610100808354040283529160200191610574565b820191906000526020600020905b81548152906001019060200180831161055757829003601f168201915b505050505090806005018054610589906113c0565b80601f01602080910402602001604051908101604052809291908181526020018280546105b5906113c0565b80156106025780601f106105d757610100808354040283529160200191610602565b820191906000526020600020905b8154815290600101906020018083116105e557829003601f168201915b5050505050905086565b600060606000806000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020868660405161066192919061115e565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160028201548152602001600382018054610740906113c0565b80601f016020809104026020016040519081016040528092919081815260200182805461076c906113c0565b80156107b95780601f1061078e576101008083540402835291602001916107b9565b820191906000526020600020905b81548152906001019060200180831161079c57829003601f168201915b505050505081526020016004820180546107d2906113c0565b80601f01602080910402602001604051908101604052809291908181526020018280546107fe906113c0565b801561084b5780601f106108205761010080835404028352916020019161084b565b820191906000526020600020905b81548152906001019060200180831161082e57829003601f168201915b50505050508152602001600582018054610864906113c0565b80601f0160208091040260200160405190810160405280929190818152602001828054610890906113c0565b80156108dd5780601f106108b2576101008083540402835291602001916108dd565b820191906000526020600020905b8154815290600101906020018083116108c057829003601f168201915b50505050508152505090508673ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff161461096657600160006040518060400160405280601881526020017f5375626a656374206f72206b6579206e6f7420666f756e640000000000000000815250909350935093505061097a565b60008160a001518260200151935093509350505b93509350939050565b8373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109bb57600080fd5b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208282604051610a0992919061115e565b9081526020016040518091039020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160009055600382016000610a7e9190610c44565b600482016000610a8e9190610c84565b600582016000610a9e9190610c84565b50508181604051610ab092919061115e565b60405180910390208373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f74b1cd181576b817654ac78213fac8b3c1daaf5ca9986d4d13afbf6a840ecb0a42604051610b15919061121f565b60405180910390a450505050565b610b31338686868686610117565b5050505050565b828054610b44906113c0565b90600052602060002090601f016020900481019282610b665760008555610bad565b82601f10610b7f57805160ff1916838001178555610bad565b82800160010185558215610bad579182015b82811115610bac578251825591602001919060010190610b91565b5b509050610bba9190610cc4565b5090565b828054610bca906113c0565b90600052602060002090601f016020900481019282610bec5760008555610c33565b82601f10610c0557805160ff1916838001178555610c33565b82800160010185558215610c33579182015b82811115610c32578251825591602001919060010190610c17565b5b509050610c409190610cc4565b5090565b508054610c50906113c0565b6000825580601f10610c625750610c81565b601f016020900490600052602060002090810190610c809190610cc4565b5b50565b508054610c90906113c0565b6000825580601f10610ca25750610cc1565b601f016020900490600052602060002090810190610cc09190610cc4565b5b50565b5b80821115610cdd576000816000905550600101610cc5565b5090565b6000610cf4610cef8461129d565b611278565b905082815260208101848484011115610d0c57600080fd5b610d1784828561137e565b509392505050565b6000610d32610d2d846112ce565b611278565b905082815260208101848484011115610d4a57600080fd5b610d5584828561137e565b509392505050565b600081359050610d6c81611492565b92915050565b600082601f830112610d8357600080fd5b8135610d93848260208601610ce1565b91505092915050565b60008083601f840112610dae57600080fd5b8235905067ffffffffffffffff811115610dc757600080fd5b602083019150836001820283011115610ddf57600080fd5b9250929050565b600082601f830112610df757600080fd5b8135610e07848260208601610d1f565b91505092915050565b60008060008060608587031215610e2657600080fd5b6000610e3487828801610d5d565b9450506020610e4587828801610d5d565b935050604085013567ffffffffffffffff811115610e6257600080fd5b610e6e87828801610d9c565b925092505092959194509250565b600080600060408486031215610e9157600080fd5b6000610e9f86828701610d5d565b935050602084013567ffffffffffffffff811115610ebc57600080fd5b610ec886828701610d9c565b92509250509250925092565b60008060008060008060808789031215610eed57600080fd5b6000610efb89828a01610d5d565b965050602087013567ffffffffffffffff811115610f1857600080fd5b610f2489828a01610d9c565b9550955050604087013567ffffffffffffffff811115610f4357600080fd5b610f4f89828a01610d9c565b9350935050606087013567ffffffffffffffff811115610f6e57600080fd5b610f7a89828a01610d72565b9150509295509295509295565b60008060408385031215610f9a57600080fd5b6000610fa885828601610d5d565b925050602083013567ffffffffffffffff811115610fc557600080fd5b610fd185828601610de6565b9150509250929050565b600080600080600060608688031215610ff357600080fd5b600086013567ffffffffffffffff81111561100d57600080fd5b61101988828901610d9c565b9550955050602086013567ffffffffffffffff81111561103857600080fd5b61104488828901610d9c565b9350935050604086013567ffffffffffffffff81111561106357600080fd5b61106f88828901610d72565b9150509295509295909350565b61108581611342565b82525050565b6000611096826112ff565b6110a08185611315565b93506110b081856020860161138d565b6110b981611481565b840191505092915050565b60006110d08385611326565b93506110dd83858461137e565b6110e683611481565b840190509392505050565b60006110fd8385611337565b935061110a83858461137e565b82840190509392505050565b60006111218261130a565b61112b8185611326565b935061113b81856020860161138d565b61114481611481565b840191505092915050565b61115881611374565b82525050565b600061116b8284866110f1565b91508190509392505050565b600060c08201905061118c600083018961107c565b611199602083018861107c565b6111a6604083018761114f565b81810360608301526111b8818661108b565b905081810360808301526111cc8185611116565b905081810360a08301526111e08184611116565b9050979650505050505050565b600060408201905081810360008301526112088185876110c4565b9050611217602083018461114f565b949350505050565b6000602082019050611234600083018461114f565b92915050565b600060608201905061124f600083018661114f565b81810360208301526112618185611116565b9050611270604083018461107c565b949350505050565b6000611282611293565b905061128e82826113f2565b919050565b6000604051905090565b600067ffffffffffffffff8211156112b8576112b7611452565b5b6112c182611481565b9050602081019050919050565b600067ffffffffffffffff8211156112e9576112e8611452565b5b6112f282611481565b9050602081019050919050565b600081519050919050565b600081519050919050565b600082825260208201905092915050565b600082825260208201905092915050565b600081905092915050565b600061134d82611354565b9050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156113ab578082015181840152602081019050611390565b838111156113ba576000848401525b50505050565b600060028204905060018216806113d857607f821691505b602082108114156113ec576113eb611423565b5b50919050565b6113fb82611481565b810181811067ffffffffffffffff8211171561141a57611419611452565b5b80604052505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f8301169050919050565b61149b81611342565b81146114a657600080fd5b5056fea26469706673582212207210bd745427a7b66c66e1b628c6e7ac009423c378591605f6ce69a18632c4f564736f6c63430008040033";

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
