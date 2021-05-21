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
public class ClaimsRegistry extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611858806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630c1f16d9146100675780634f6016e71461008357806358924b81146100b85780635d9026b2146100ea578063d690d94d14610106578063eedaa50014610122575b600080fd5b610081600480360381019061007c9190611002565b610152565b005b61009d600480360381019061009891906110b5565b61044e565b6040516100af96959493929190611402565b60405180910390f35b6100d260048036038101906100cd9190610faa565b610685565b6040516100e19392919061150a565b60405180910390f35b61010460048036038101906100ff9190610f3e565b6109fc565b005b610120600480360381019061011b919061116c565b610b9c565b005b61013c60048036038101906101379190611109565b610bb1565b60405161014991906113e7565b60405180910390f35b600061018d3388888888886040516020016101729695949392919061135e565b60405160208183030381529060405280519060200120610c0c565b905060006040518060c001604052808973ffffffffffffffffffffffffffffffffffffffff1681526020013373ffffffffffffffffffffffffffffffffffffffff16815260200142815260200184815260200188888080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050815260200186868080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050508152509050806000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002088886040516102c39291906113a8565b908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550604082015181600201556060820151816003019080519060200190610385929190610c3c565b5060808201518160040190805190602001906103a2929190610cc2565b5060a08201518160050190805190602001906103bf929190610cc2565b5090505086866040516103d39291906113a8565b60405180910390208873ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fd0e66d7a7ad5b30e8c8833da0fa84893d3d777fa4767b331c91941fd458d1e0288884260405161043c939291906114bd565b60405180910390a45050505050505050565b600060205281600052604060002081805160208101820180518482526020830160208501208183528095505050505050600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154908060030180546104e6906116a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610512906116a7565b801561055f5780601f106105345761010080835404028352916020019161055f565b820191906000526020600020905b81548152906001019060200180831161054257829003601f168201915b505050505090806004018054610574906116a7565b80601f01602080910402602001604051908101604052809291908181526020018280546105a0906116a7565b80156105ed5780601f106105c2576101008083540402835291602001916105ed565b820191906000526020600020905b8154815290600101906020018083116105d057829003601f168201915b505050505090806005018054610602906116a7565b80601f016020809104026020016040519081016040528092919081815260200182805461062e906116a7565b801561067b5780601f106106505761010080835404028352916020019161067b565b820191906000526020600020905b81548152906001019060200180831161065e57829003601f168201915b5050505050905086565b600060606000806000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002086866040516106da9291906113a8565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600282015481526020016003820180546107b9906116a7565b80601f01602080910402602001604051908101604052809291908181526020018280546107e5906116a7565b80156108325780601f1061080757610100808354040283529160200191610832565b820191906000526020600020905b81548152906001019060200180831161081557829003601f168201915b5050505050815260200160048201805461084b906116a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610877906116a7565b80156108c45780601f10610899576101008083540402835291602001916108c4565b820191906000526020600020905b8154815290600101906020018083116108a757829003601f168201915b505050505081526020016005820180546108dd906116a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610909906116a7565b80156109565780601f1061092b57610100808354040283529160200191610956565b820191906000526020600020905b81548152906001019060200180831161093957829003601f168201915b50505050508152505090508673ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff16146109df57600160006040518060400160405280601881526020017f5375626a656374206f72206b6579206e6f7420666f756e64000000000000000081525090935093509350506109f3565b60008160a001518260200151935093509350505b93509350939050565b8373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a3457600080fd5b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208282604051610a829291906113a8565b9081526020016040518091039020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160009055600382016000610af79190610d48565b600482016000610b079190610d88565b600582016000610b179190610d88565b50508181604051610b299291906113a8565b60405180910390208373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f74b1cd181576b817654ac78213fac8b3c1daaf5ca9986d4d13afbf6a840ecb0a42604051610b8e91906114ef565b60405180910390a450505050565b610baa338686868686610152565b5050505050565b600060018585858560405160008152602001604052604051610bd69493929190611478565b6020604051602081039080840390855afa158015610bf8573d6000803e3d6000fd5b505050602060405103519050949350505050565b600081604051602001610c1f91906113c1565b604051602081830303815290604052805190602001209050919050565b828054610c48906116a7565b90600052602060002090601f016020900481019282610c6a5760008555610cb1565b82601f10610c8357805160ff1916838001178555610cb1565b82800160010185558215610cb1579182015b82811115610cb0578251825591602001919060010190610c95565b5b509050610cbe9190610dc8565b5090565b828054610cce906116a7565b90600052602060002090601f016020900481019282610cf05760008555610d37565b82601f10610d0957805160ff1916838001178555610d37565b82800160010185558215610d37579182015b82811115610d36578251825591602001919060010190610d1b565b5b509050610d449190610dc8565b5090565b508054610d54906116a7565b6000825580601f10610d665750610d85565b601f016020900490600052602060002090810190610d849190610dc8565b5b50565b508054610d94906116a7565b6000825580601f10610da65750610dc5565b601f016020900490600052602060002090810190610dc49190610dc8565b5b50565b5b80821115610de1576000816000905550600101610dc9565b5090565b6000610df8610df38461156d565b611548565b905082815260208101848484011115610e1057600080fd5b610e1b848285611665565b509392505050565b6000610e36610e318461159e565b611548565b905082815260208101848484011115610e4e57600080fd5b610e59848285611665565b509392505050565b600081359050610e70816117dd565b92915050565b600081359050610e85816117f4565b92915050565b600082601f830112610e9c57600080fd5b8135610eac848260208601610de5565b91505092915050565b60008083601f840112610ec757600080fd5b8235905067ffffffffffffffff811115610ee057600080fd5b602083019150836001820283011115610ef857600080fd5b9250929050565b600082601f830112610f1057600080fd5b8135610f20848260208601610e23565b91505092915050565b600081359050610f388161180b565b92915050565b60008060008060608587031215610f5457600080fd5b6000610f6287828801610e61565b9450506020610f7387828801610e61565b935050604085013567ffffffffffffffff811115610f9057600080fd5b610f9c87828801610eb5565b925092505092959194509250565b600080600060408486031215610fbf57600080fd5b6000610fcd86828701610e61565b935050602084013567ffffffffffffffff811115610fea57600080fd5b610ff686828701610eb5565b92509250509250925092565b6000806000806000806080878903121561101b57600080fd5b600061102989828a01610e61565b965050602087013567ffffffffffffffff81111561104657600080fd5b61105289828a01610eb5565b9550955050604087013567ffffffffffffffff81111561107157600080fd5b61107d89828a01610eb5565b9350935050606087013567ffffffffffffffff81111561109c57600080fd5b6110a889828a01610e8b565b9150509295509295509295565b600080604083850312156110c857600080fd5b60006110d685828601610e61565b925050602083013567ffffffffffffffff8111156110f357600080fd5b6110ff85828601610eff565b9150509250929050565b6000806000806080858703121561111f57600080fd5b600061112d87828801610e76565b945050602061113e87828801610f29565b935050604061114f87828801610e76565b925050606061116087828801610e76565b91505092959194509250565b60008060008060006060868803121561118457600080fd5b600086013567ffffffffffffffff81111561119e57600080fd5b6111aa88828901610eb5565b9550955050602086013567ffffffffffffffff8111156111c957600080fd5b6111d588828901610eb5565b9350935050604086013567ffffffffffffffff8111156111f457600080fd5b61120088828901610e8b565b9150509295509295909350565b61121681611612565b82525050565b61122d61122882611612565b61170a565b82525050565b61123c81611624565b82525050565b61125361124e82611624565b61171c565b82525050565b6000611264826115cf565b61126e81856115e5565b935061127e818560208601611674565b61128781611796565b840191505092915050565b600061129e83856115f6565b93506112ab838584611665565b6112b483611796565b840190509392505050565b60006112cb8385611607565b93506112d8838584611665565b82840190509392505050565b60006112ef826115da565b6112f981856115f6565b9350611309818560208601611674565b61131281611796565b840191505092915050565b600061132a601c83611607565b9150611335826117b4565b601c82019050919050565b6113498161164e565b82525050565b61135881611658565b82525050565b600061136a828961121c565b60148201915061137a828861121c565b60148201915061138b8286886112bf565b91506113988284866112bf565b9150819050979650505050505050565b60006113b58284866112bf565b91508190509392505050565b60006113cc8261131d565b91506113d88284611242565b60208201915081905092915050565b60006020820190506113fc600083018461120d565b92915050565b600060c082019050611417600083018961120d565b611424602083018861120d565b6114316040830187611340565b81810360608301526114438186611259565b9050818103608083015261145781856112e4565b905081810360a083015261146b81846112e4565b9050979650505050505050565b600060808201905061148d6000830187611233565b61149a602083018661134f565b6114a76040830185611233565b6114b46060830184611233565b95945050505050565b600060408201905081810360008301526114d8818587611292565b90506114e76020830184611340565b949350505050565b60006020820190506115046000830184611340565b92915050565b600060608201905061151f6000830186611340565b818103602083015261153181856112e4565b9050611540604083018461120d565b949350505050565b6000611552611563565b905061155e82826116d9565b919050565b6000604051905090565b600067ffffffffffffffff82111561158857611587611767565b5b61159182611796565b9050602081019050919050565b600067ffffffffffffffff8211156115b9576115b8611767565b5b6115c282611796565b9050602081019050919050565b600081519050919050565b600081519050919050565b600082825260208201905092915050565b600082825260208201905092915050565b600081905092915050565b600061161d8261162e565b9050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600060ff82169050919050565b82818337600083830152505050565b60005b83811015611692578082015181840152602081019050611677565b838111156116a1576000848401525b50505050565b600060028204905060018216806116bf57607f821691505b602082108114156116d3576116d2611738565b5b50919050565b6116e282611796565b810181811067ffffffffffffffff8211171561170157611700611767565b5b80604052505050565b600061171582611726565b9050919050565b6000819050919050565b6000611731826117a7565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f8301169050919050565b60008160601b9050919050565b7f19457468657265756d205369676e6564204d6573736167653a0a333200000000600082015250565b6117e681611612565b81146117f157600080fd5b50565b6117fd81611624565b811461180857600080fd5b50565b61181481611658565b811461181f57600080fd5b5056fea2646970667358221220b2ea1765cc8d979fc649197e70b4a2b3866faac8ebaea40abb3cb0ffc4056a3964736f6c63430008040033";

    public static final String FUNC_CHECKSIGNATURE = "checkSignature";

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
    protected ClaimsRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ClaimsRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ClaimsRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ClaimsRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<String> checkSignature(byte[] msgHash, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHECKSIGNATURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(msgHash), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
    public static ClaimsRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ClaimsRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ClaimsRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ClaimsRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ClaimsRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ClaimsRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ClaimsRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ClaimsRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ClaimsRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ClaimsRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<ClaimsRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ClaimsRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ClaimsRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ClaimsRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ClaimsRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ClaimsRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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
