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
    public static final String BINARY = "608060405234801561001057600080fd5b50611551806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630c1f16d91461005c5780634f6016e71461007857806358924b81146100ad5780635d9026b2146100df578063d690d94d146100fb575b600080fd5b61007660048036038101906100719190610f00565b610117565b005b610092600480360381019061008d9190610fc3565b6103d5565b6040516100a4969594939291906111cb565b60405180910390f35b6100c760048036038101906100c29190610ea0565b61060c565b6040516100d69392919061128e565b60405180910390f35b6100f960048036038101906100f49190610e2c565b610983565b005b6101156004803603810190610110919061101f565b610b23565b005b60006040518060c001604052808873ffffffffffffffffffffffffffffffffffffffff1681526020013373ffffffffffffffffffffffffffffffffffffffff16815260200142815260200183815260200187878080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050815260200185858080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050508152509050806000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020878760405161024b9291906111b2565b908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301908051906020019061030d929190610b38565b50608082015181600401908051906020019061032a929190610bbe565b5060a0820151816005019080519060200190610347929190610bbe565b50905050858560405161035b9291906111b2565b60405180910390208773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fd0e66d7a7ad5b30e8c8833da0fa84893d3d777fa4767b331c91941fd458d1e028787426040516103c493929190611241565b60405180910390a450505050505050565b600060205281600052604060002081805160208101820180518482526020830160208501208183528095505050505050600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169080600201549080600301805461046d90611414565b80601f016020809104026020016040519081016040528092919081815260200182805461049990611414565b80156104e65780601f106104bb576101008083540402835291602001916104e6565b820191906000526020600020905b8154815290600101906020018083116104c957829003601f168201915b5050505050908060040180546104fb90611414565b80601f016020809104026020016040519081016040528092919081815260200182805461052790611414565b80156105745780601f1061054957610100808354040283529160200191610574565b820191906000526020600020905b81548152906001019060200180831161055757829003601f168201915b50505050509080600501805461058990611414565b80601f01602080910402602001604051908101604052809291908181526020018280546105b590611414565b80156106025780601f106105d757610100808354040283529160200191610602565b820191906000526020600020905b8154815290600101906020018083116105e557829003601f168201915b5050505050905086565b600060606000806000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002086866040516106619291906111b2565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820154815260200160038201805461074090611414565b80601f016020809104026020016040519081016040528092919081815260200182805461076c90611414565b80156107b95780601f1061078e576101008083540402835291602001916107b9565b820191906000526020600020905b81548152906001019060200180831161079c57829003601f168201915b505050505081526020016004820180546107d290611414565b80601f01602080910402602001604051908101604052809291908181526020018280546107fe90611414565b801561084b5780601f106108205761010080835404028352916020019161084b565b820191906000526020600020905b81548152906001019060200180831161082e57829003601f168201915b5050505050815260200160058201805461086490611414565b80601f016020809104026020016040519081016040528092919081815260200182805461089090611414565b80156108dd5780601f106108b2576101008083540402835291602001916108dd565b820191906000526020600020905b8154815290600101906020018083116108c057829003601f168201915b50505050508152505090508673ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff161461096657600160006040518060400160405280601881526020017f5375626a656374206f72206b6579206e6f7420666f756e640000000000000000815250909350935093505061097a565b60008160a001518260200151935093509350505b93509350939050565b8373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109bb57600080fd5b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208282604051610a099291906111b2565b9081526020016040518091039020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160009055600382016000610a7e9190610c44565b600482016000610a8e9190610c84565b600582016000610a9e9190610c84565b50508181604051610ab09291906111b2565b60405180910390208373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f74b1cd181576b817654ac78213fac8b3c1daaf5ca9986d4d13afbf6a840ecb0a42604051610b159190611273565b60405180910390a450505050565b610b31338686868686610117565b5050505050565b828054610b4490611414565b90600052602060002090601f016020900481019282610b665760008555610bad565b82601f10610b7f57805160ff1916838001178555610bad565b82800160010185558215610bad579182015b82811115610bac578251825591602001919060010190610b91565b5b509050610bba9190610cc4565b5090565b828054610bca90611414565b90600052602060002090601f016020900481019282610bec5760008555610c33565b82601f10610c0557805160ff1916838001178555610c33565b82800160010185558215610c33579182015b82811115610c32578251825591602001919060010190610c17565b5b509050610c409190610cc4565b5090565b508054610c5090611414565b6000825580601f10610c625750610c81565b601f016020900490600052602060002090810190610c809190610cc4565b5b50565b508054610c9090611414565b6000825580601f10610ca25750610cc1565b601f016020900490600052602060002090810190610cc09190610cc4565b5b50565b5b80821115610cdd576000816000905550600101610cc5565b5090565b6000610cf4610cef846112f1565b6112cc565b905082815260208101848484011115610d1057610d0f6114e4565b5b610d1b8482856113d2565b509392505050565b6000610d36610d3184611322565b6112cc565b905082815260208101848484011115610d5257610d516114e4565b5b610d5d8482856113d2565b509392505050565b600081359050610d7481611504565b92915050565b600082601f830112610d8f57610d8e6114da565b5b8135610d9f848260208601610ce1565b91505092915050565b60008083601f840112610dbe57610dbd6114da565b5b8235905067ffffffffffffffff811115610ddb57610dda6114d5565b5b602083019150836001820283011115610df757610df66114df565b5b9250929050565b600082601f830112610e1357610e126114da565b5b8135610e23848260208601610d23565b91505092915050565b60008060008060608587031215610e4657610e456114ee565b5b6000610e5487828801610d65565b9450506020610e6587828801610d65565b935050604085013567ffffffffffffffff811115610e8657610e856114e9565b5b610e9287828801610da8565b925092505092959194509250565b600080600060408486031215610eb957610eb86114ee565b5b6000610ec786828701610d65565b935050602084013567ffffffffffffffff811115610ee857610ee76114e9565b5b610ef486828701610da8565b92509250509250925092565b60008060008060008060808789031215610f1d57610f1c6114ee565b5b6000610f2b89828a01610d65565b965050602087013567ffffffffffffffff811115610f4c57610f4b6114e9565b5b610f5889828a01610da8565b9550955050604087013567ffffffffffffffff811115610f7b57610f7a6114e9565b5b610f8789828a01610da8565b9350935050606087013567ffffffffffffffff811115610faa57610fa96114e9565b5b610fb689828a01610d7a565b9150509295509295509295565b60008060408385031215610fda57610fd96114ee565b5b6000610fe885828601610d65565b925050602083013567ffffffffffffffff811115611009576110086114e9565b5b61101585828601610dfe565b9150509250929050565b60008060008060006060868803121561103b5761103a6114ee565b5b600086013567ffffffffffffffff811115611059576110586114e9565b5b61106588828901610da8565b9550955050602086013567ffffffffffffffff811115611088576110876114e9565b5b61109488828901610da8565b9350935050604086013567ffffffffffffffff8111156110b7576110b66114e9565b5b6110c388828901610d7a565b9150509295509295909350565b6110d981611396565b82525050565b60006110ea82611353565b6110f48185611369565b93506111048185602086016113e1565b61110d816114f3565b840191505092915050565b6000611124838561137a565b93506111318385846113d2565b61113a836114f3565b840190509392505050565b6000611151838561138b565b935061115e8385846113d2565b82840190509392505050565b60006111758261135e565b61117f818561137a565b935061118f8185602086016113e1565b611198816114f3565b840191505092915050565b6111ac816113c8565b82525050565b60006111bf828486611145565b91508190509392505050565b600060c0820190506111e060008301896110d0565b6111ed60208301886110d0565b6111fa60408301876111a3565b818103606083015261120c81866110df565b90508181036080830152611220818561116a565b905081810360a0830152611234818461116a565b9050979650505050505050565b6000604082019050818103600083015261125c818587611118565b905061126b60208301846111a3565b949350505050565b600060208201905061128860008301846111a3565b92915050565b60006060820190506112a360008301866111a3565b81810360208301526112b5818561116a565b90506112c460408301846110d0565b949350505050565b60006112d66112e7565b90506112e28282611446565b919050565b6000604051905090565b600067ffffffffffffffff82111561130c5761130b6114a6565b5b611315826114f3565b9050602081019050919050565b600067ffffffffffffffff82111561133d5761133c6114a6565b5b611346826114f3565b9050602081019050919050565b600081519050919050565b600081519050919050565b600082825260208201905092915050565b600082825260208201905092915050565b600081905092915050565b60006113a1826113a8565b9050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156113ff5780820151818401526020810190506113e4565b8381111561140e576000848401525b50505050565b6000600282049050600182168061142c57607f821691505b602082108114156114405761143f611477565b5b50919050565b61144f826114f3565b810181811067ffffffffffffffff8211171561146e5761146d6114a6565b5b80604052505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b61150d81611396565b811461151857600080fd5b5056fea26469706673582212209f30a91ed76f7b0ece502d1cde430942096507b4bca01037750161817b07ff6464736f6c63430008050033";

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
