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
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple8;
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
    public static final String BINARY = "608060405234801561001057600080fd5b50611b0b806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630c1f16d91461005c5780634f6016e71461007857806358924b81146100ad5780635d9026b2146100e4578063d690d94d14610100575b600080fd5b61007660048036038101906100719190611360565b61011c565b005b610092600480360381019061008d9190611423565b610425565b6040516100a496959493929190611671565b60405180910390f35b6100c760048036038101906100c29190611300565b61065c565b6040516100db989796959493929190611774565b60405180910390f35b6100fe60048036038101906100f9919061128c565b610a53565b005b61011a6004803603810190610115919061147f565b610c29565b005b61012886868633610c3e565b610167576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161015e90611719565b60405180910390fd5b60006040518060c001604052808873ffffffffffffffffffffffffffffffffffffffff1681526020013373ffffffffffffffffffffffffffffffffffffffff16815260200142815260200183815260200187878080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050815260200185858080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050508152509050806000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020878760405161029b929190611658565b908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301908051906020019061035d929190610f98565b50608082015181600401908051906020019061037a92919061101e565b5060a082015181600501908051906020019061039792919061101e565b5090505085856040516103ab929190611658565b60405180910390208773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fd0e66d7a7ad5b30e8c8833da0fa84893d3d777fa4767b331c91941fd458d1e02878742604051610414939291906116e7565b60405180910390a450505050505050565b600060205281600052604060002081805160208101820180518482526020830160208501208183528095505050505050600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154908060030180546104bd90611956565b80601f01602080910402602001604051908101604052809291908181526020018280546104e990611956565b80156105365780601f1061050b57610100808354040283529160200191610536565b820191906000526020600020905b81548152906001019060200180831161051957829003601f168201915b50505050509080600401805461054b90611956565b80601f016020809104026020016040519081016040528092919081815260200182805461057790611956565b80156105c45780601f10610599576101008083540402835291602001916105c4565b820191906000526020600020905b8154815290600101906020018083116105a757829003601f168201915b5050505050908060050180546105d990611956565b80601f016020809104026020016040519081016040528092919081815260200182805461060590611956565b80156106525780601f1061062757610100808354040283529160200191610652565b820191906000526020600020905b81548152906001019060200180831161063557829003601f168201915b5050505050905086565b600060606000806000606080606060008060008d73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208b8b6040516106ba929190611658565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820154815260200160038201805461079990611956565b80601f01602080910402602001604051908101604052809291908181526020018280546107c590611956565b80156108125780601f106107e757610100808354040283529160200191610812565b820191906000526020600020905b8154815290600101906020018083116107f557829003601f168201915b5050505050815260200160048201805461082b90611956565b80601f016020809104026020016040519081016040528092919081815260200182805461085790611956565b80156108a45780601f10610879576101008083540402835291602001916108a4565b820191906000526020600020905b81548152906001019060200180831161088757829003601f168201915b505050505081526020016005820180546108bd90611956565b80601f01602080910402602001604051908101604052809291908181526020018280546108e990611956565b80156109365780601f1061090b57610100808354040283529160200191610936565b820191906000526020600020905b81548152906001019060200180831161091957829003601f168201915b50505050508152505090508b73ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff16146109fe57600160008060006040518060400160405280601881526020017f5375626a656374206f72206b6579206e6f7420666f756e6400000000000000008152509291906040518060200160405280600081525060405180602001604052806000815250604051806020016040528060008152509850985098509850985098509850985050610a46565b6000816000015182602001518360400151846060015185608001518660a001516040518060200160405280600081525095949392919098509850985098509850985098509850505b9397509397509397509397565b8373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610ac1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ab890611739565b60405180910390fd5b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208282604051610b0f929190611658565b9081526020016040518091039020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160009055600382016000610b8491906110a4565b600482016000610b9491906110e4565b600582016000610ba491906110e4565b50508181604051610bb6929190611658565b60405180910390208373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f74b1cd181576b817654ac78213fac8b3c1daaf5ca9986d4d13afbf6a840ecb0a42604051610c1b9190611759565b60405180910390a450505050565b610c3733868686868661011c565b5050505050565b6000806000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208585604051610c8f929190611658565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160028201548152602001600382018054610d6e90611956565b80601f0160208091040260200160405190810160405280929190818152602001828054610d9a90611956565b8015610de75780601f10610dbc57610100808354040283529160200191610de7565b820191906000526020600020905b815481529060010190602001808311610dca57829003601f168201915b50505050508152602001600482018054610e0090611956565b80601f0160208091040260200160405190810160405280929190818152602001828054610e2c90611956565b8015610e795780601f10610e4e57610100808354040283529160200191610e79565b820191906000526020600020905b815481529060010190602001808311610e5c57829003601f168201915b50505050508152602001600582018054610e9290611956565b80601f0160208091040260200160405190810160405280929190818152602001828054610ebe90611956565b8015610f0b5780601f10610ee057610100808354040283529160200191610f0b565b820191906000526020600020905b815481529060010190602001808311610eee57829003601f168201915b5050505050815250509050600073ffffffffffffffffffffffffffffffffffffffff16816020015173ffffffffffffffffffffffffffffffffffffffff161415610f59576001915050610f90565b806020015173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16149150505b949350505050565b828054610fa490611956565b90600052602060002090601f016020900481019282610fc6576000855561100d565b82601f10610fdf57805160ff191683800117855561100d565b8280016001018555821561100d579182015b8281111561100c578251825591602001919060010190610ff1565b5b50905061101a9190611124565b5090565b82805461102a90611956565b90600052602060002090601f01602090048101928261104c5760008555611093565b82601f1061106557805160ff1916838001178555611093565b82800160010185558215611093579182015b82811115611092578251825591602001919060010190611077565b5b5090506110a09190611124565b5090565b5080546110b090611956565b6000825580601f106110c257506110e1565b601f0160209004906000526020600020908101906110e09190611124565b5b50565b5080546110f090611956565b6000825580601f106111025750611121565b601f0160209004906000526020600020908101906111209190611124565b5b50565b5b8082111561113d576000816000905550600101611125565b5090565b600061115461114f84611833565b61180e565b9050828152602081018484840111156111705761116f611a26565b5b61117b848285611914565b509392505050565b600061119661119184611864565b61180e565b9050828152602081018484840111156111b2576111b1611a26565b5b6111bd848285611914565b509392505050565b6000813590506111d481611abe565b92915050565b600082601f8301126111ef576111ee611a1c565b5b81356111ff848260208601611141565b91505092915050565b60008083601f84011261121e5761121d611a1c565b5b8235905067ffffffffffffffff81111561123b5761123a611a17565b5b60208301915083600182028301111561125757611256611a21565b5b9250929050565b600082601f83011261127357611272611a1c565b5b8135611283848260208601611183565b91505092915050565b600080600080606085870312156112a6576112a5611a30565b5b60006112b4878288016111c5565b94505060206112c5878288016111c5565b935050604085013567ffffffffffffffff8111156112e6576112e5611a2b565b5b6112f287828801611208565b925092505092959194509250565b60008060006040848603121561131957611318611a30565b5b6000611327868287016111c5565b935050602084013567ffffffffffffffff81111561134857611347611a2b565b5b61135486828701611208565b92509250509250925092565b6000806000806000806080878903121561137d5761137c611a30565b5b600061138b89828a016111c5565b965050602087013567ffffffffffffffff8111156113ac576113ab611a2b565b5b6113b889828a01611208565b9550955050604087013567ffffffffffffffff8111156113db576113da611a2b565b5b6113e789828a01611208565b9350935050606087013567ffffffffffffffff81111561140a57611409611a2b565b5b61141689828a016111da565b9150509295509295509295565b6000806040838503121561143a57611439611a30565b5b6000611448858286016111c5565b925050602083013567ffffffffffffffff81111561146957611468611a2b565b5b6114758582860161125e565b9150509250929050565b60008060008060006060868803121561149b5761149a611a30565b5b600086013567ffffffffffffffff8111156114b9576114b8611a2b565b5b6114c588828901611208565b9550955050602086013567ffffffffffffffff8111156114e8576114e7611a2b565b5b6114f488828901611208565b9350935050604086013567ffffffffffffffff81111561151757611516611a2b565b5b611523888289016111da565b9150509295509295909350565b611539816118d8565b82525050565b600061154a82611895565b61155481856118ab565b9350611564818560208601611923565b61156d81611a35565b840191505092915050565b600061158483856118bc565b9350611591838584611914565b61159a83611a35565b840190509392505050565b60006115b183856118cd565b93506115be838584611914565b82840190509392505050565b60006115d5826118a0565b6115df81856118bc565b93506115ef818560208601611923565b6115f881611a35565b840191505092915050565b60006116106028836118bc565b915061161b82611a46565b604082019050919050565b6000611633601c836118bc565b915061163e82611a95565b602082019050919050565b6116528161190a565b82525050565b60006116658284866115a5565b91508190509392505050565b600060c0820190506116866000830189611530565b6116936020830188611530565b6116a06040830187611649565b81810360608301526116b2818661153f565b905081810360808301526116c681856115ca565b905081810360a08301526116da81846115ca565b9050979650505050505050565b60006040820190508181036000830152611702818587611578565b90506117116020830184611649565b949350505050565b6000602082019050818103600083015261173281611603565b9050919050565b6000602082019050818103600083015261175281611626565b9050919050565b600060208201905061176e6000830184611649565b92915050565b60006101008201905061178a600083018b611649565b818103602083015261179c818a6115ca565b90506117ab6040830189611530565b6117b86060830188611530565b6117c56080830187611649565b81810360a08301526117d7818661153f565b905081810360c08301526117eb81856115ca565b905081810360e08301526117ff81846115ca565b90509998505050505050505050565b6000611818611829565b90506118248282611988565b919050565b6000604051905090565b600067ffffffffffffffff82111561184e5761184d6119e8565b5b61185782611a35565b9050602081019050919050565b600067ffffffffffffffff82111561187f5761187e6119e8565b5b61188882611a35565b9050602081019050919050565b600081519050919050565b600081519050919050565b600082825260208201905092915050565b600082825260208201905092915050565b600081905092915050565b60006118e3826118ea565b9050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015611941578082015181840152602081019050611926565b83811115611950576000848401525b50505050565b6000600282049050600182168061196e57607f821691505b60208210811415611982576119816119b9565b5b50919050565b61199182611a35565b810181811067ffffffffffffffff821117156119b0576119af6119e8565b5b80604052505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4f6e6c79206973737565722063616e2075706461746520616e2065786973746960008201527f6e6720636c61696d000000000000000000000000000000000000000000000000602082015250565b7f4f6e6c79206973737565722063616e2072656d6f766520636c61696d00000000600082015250565b611ac7816118d8565b8114611ad257600080fd5b5056fea2646970667358221220b3730561ca61a11b92a4e9652fb87c1dc6b7387aeabb42fbefbb193586cea0bf64736f6c63430008050033";

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

    public RemoteFunctionCall<Tuple8<BigInteger, String, String, String, BigInteger, byte[], String, String>> getClaim(String subject, String key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, subject), 
                new org.web3j.abi.datatypes.Utf8String(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple8<BigInteger, String, String, String, BigInteger, byte[], String, String>>(function,
                new Callable<Tuple8<BigInteger, String, String, String, BigInteger, byte[], String, String>>() {
                    @Override
                    public Tuple8<BigInteger, String, String, String, BigInteger, byte[], String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<BigInteger, String, String, String, BigInteger, byte[], String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (byte[]) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (String) results.get(7).getValue());
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
