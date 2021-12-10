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
    public static final String BINARY = "608060405234801561001057600080fd5b50611b0b806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630c1f16d91461005c5780634f6016e71461007857806358924b81146100ad5780635d9026b2146100e4578063d690d94d14610100575b600080fd5b61007660048036038101906100719190611359565b61011c565b005b610092600480360381019061008d91906114bd565b610425565b6040516100a49695949392919061161e565b60405180910390f35b6100c760048036038101906100c29190611694565b61065c565b6040516100db9897969594939291906116f4565b60405180910390f35b6100fe60048036038101906100f9919061178e565b610a53565b005b61011a60048036038101906101159190611802565b610c29565b005b61012886868633610c3e565b610167576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161015e90611925565b60405180910390fd5b60006040518060c001604052808873ffffffffffffffffffffffffffffffffffffffff1681526020013373ffffffffffffffffffffffffffffffffffffffff16815260200142815260200183815260200187878080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050815260200185858080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050508152509050806000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020878760405161029b929190611975565b908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301908051906020019061035d929190610f98565b50608082015181600401908051906020019061037a92919061101e565b5060a082015181600501908051906020019061039792919061101e565b5090505085856040516103ab929190611975565b60405180910390208773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fd0e66d7a7ad5b30e8c8833da0fa84893d3d777fa4767b331c91941fd458d1e02878742604051610414939291906119bb565b60405180910390a450505050505050565b600060205281600052604060002081805160208101820180518482526020830160208501208183528095505050505050600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154908060030180546104bd90611a1c565b80601f01602080910402602001604051908101604052809291908181526020018280546104e990611a1c565b80156105365780601f1061050b57610100808354040283529160200191610536565b820191906000526020600020905b81548152906001019060200180831161051957829003601f168201915b50505050509080600401805461054b90611a1c565b80601f016020809104026020016040519081016040528092919081815260200182805461057790611a1c565b80156105c45780601f10610599576101008083540402835291602001916105c4565b820191906000526020600020905b8154815290600101906020018083116105a757829003601f168201915b5050505050908060050180546105d990611a1c565b80601f016020809104026020016040519081016040528092919081815260200182805461060590611a1c565b80156106525780601f1061062757610100808354040283529160200191610652565b820191906000526020600020905b81548152906001019060200180831161063557829003601f168201915b5050505050905086565b600060606000806000606080606060008060008d73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208b8b6040516106ba929190611975565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820154815260200160038201805461079990611a1c565b80601f01602080910402602001604051908101604052809291908181526020018280546107c590611a1c565b80156108125780601f106107e757610100808354040283529160200191610812565b820191906000526020600020905b8154815290600101906020018083116107f557829003601f168201915b5050505050815260200160048201805461082b90611a1c565b80601f016020809104026020016040519081016040528092919081815260200182805461085790611a1c565b80156108a45780601f10610879576101008083540402835291602001916108a4565b820191906000526020600020905b81548152906001019060200180831161088757829003601f168201915b505050505081526020016005820180546108bd90611a1c565b80601f01602080910402602001604051908101604052809291908181526020018280546108e990611a1c565b80156109365780601f1061090b57610100808354040283529160200191610936565b820191906000526020600020905b81548152906001019060200180831161091957829003601f168201915b50505050508152505090508b73ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff16146109fe57600160008060006040518060400160405280601881526020017f5375626a656374206f72206b6579206e6f7420666f756e6400000000000000008152509291906040518060200160405280600081525060405180602001604052806000815250604051806020016040528060008152509850985098509850985098509850985050610a46565b6000816000015182602001518360400151846060015185608001518660a001516040518060200160405280600081525095949392919098509850985098509850985098509850505b9397509397509397509397565b8373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610ac1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ab890611a9a565b60405180910390fd5b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208282604051610b0f929190611975565b9081526020016040518091039020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160009055600382016000610b8491906110a4565b600482016000610b9491906110e4565b600582016000610ba491906110e4565b50508181604051610bb6929190611975565b60405180910390208373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f74b1cd181576b817654ac78213fac8b3c1daaf5ca9986d4d13afbf6a840ecb0a42604051610c1b9190611aba565b60405180910390a450505050565b610c3733868686868661011c565b5050505050565b6000806000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208585604051610c8f929190611975565b90815260200160405180910390206040518060c00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160028201548152602001600382018054610d6e90611a1c565b80601f0160208091040260200160405190810160405280929190818152602001828054610d9a90611a1c565b8015610de75780601f10610dbc57610100808354040283529160200191610de7565b820191906000526020600020905b815481529060010190602001808311610dca57829003601f168201915b50505050508152602001600482018054610e0090611a1c565b80601f0160208091040260200160405190810160405280929190818152602001828054610e2c90611a1c565b8015610e795780601f10610e4e57610100808354040283529160200191610e79565b820191906000526020600020905b815481529060010190602001808311610e5c57829003601f168201915b50505050508152602001600582018054610e9290611a1c565b80601f0160208091040260200160405190810160405280929190818152602001828054610ebe90611a1c565b8015610f0b5780601f10610ee057610100808354040283529160200191610f0b565b820191906000526020600020905b815481529060010190602001808311610eee57829003601f168201915b5050505050815250509050600073ffffffffffffffffffffffffffffffffffffffff16816020015173ffffffffffffffffffffffffffffffffffffffff161415610f59576001915050610f90565b806020015173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16149150505b949350505050565b828054610fa490611a1c565b90600052602060002090601f016020900481019282610fc6576000855561100d565b82601f10610fdf57805160ff191683800117855561100d565b8280016001018555821561100d579182015b8281111561100c578251825591602001919060010190610ff1565b5b50905061101a9190611124565b5090565b82805461102a90611a1c565b90600052602060002090601f01602090048101928261104c5760008555611093565b82601f1061106557805160ff1916838001178555611093565b82800160010185558215611093579182015b82811115611092578251825591602001919060010190611077565b5b5090506110a09190611124565b5090565b5080546110b090611a1c565b6000825580601f106110c257506110e1565b601f0160209004906000526020600020908101906110e09190611124565b5b50565b5080546110f090611a1c565b6000825580601f106111025750611121565b601f0160209004906000526020600020908101906111209190611124565b5b50565b5b8082111561113d576000816000905550600101611125565b5090565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061118082611155565b9050919050565b61119081611175565b811461119b57600080fd5b50565b6000813590506111ad81611187565b92915050565b600080fd5b600080fd5b600080fd5b60008083601f8401126111d8576111d76111b3565b5b8235905067ffffffffffffffff8111156111f5576111f46111b8565b5b602083019150836001820283011115611211576112106111bd565b5b9250929050565b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6112668261121d565b810181811067ffffffffffffffff821117156112855761128461122e565b5b80604052505050565b6000611298611141565b90506112a4828261125d565b919050565b600067ffffffffffffffff8211156112c4576112c361122e565b5b6112cd8261121d565b9050602081019050919050565b82818337600083830152505050565b60006112fc6112f7846112a9565b61128e565b90508281526020810184848401111561131857611317611218565b5b6113238482856112da565b509392505050565b600082601f8301126113405761133f6111b3565b5b81356113508482602086016112e9565b91505092915050565b600080600080600080608087890312156113765761137561114b565b5b600061138489828a0161119e565b965050602087013567ffffffffffffffff8111156113a5576113a4611150565b5b6113b189828a016111c2565b9550955050604087013567ffffffffffffffff8111156113d4576113d3611150565b5b6113e089828a016111c2565b9350935050606087013567ffffffffffffffff81111561140357611402611150565b5b61140f89828a0161132b565b9150509295509295509295565b600067ffffffffffffffff8211156114375761143661122e565b5b6114408261121d565b9050602081019050919050565b600061146061145b8461141c565b61128e565b90508281526020810184848401111561147c5761147b611218565b5b6114878482856112da565b509392505050565b600082601f8301126114a4576114a36111b3565b5b81356114b484826020860161144d565b91505092915050565b600080604083850312156114d4576114d361114b565b5b60006114e28582860161119e565b925050602083013567ffffffffffffffff81111561150357611502611150565b5b61150f8582860161148f565b9150509250929050565b61152281611175565b82525050565b6000819050919050565b61153b81611528565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561157b578082015181840152602081019050611560565b8381111561158a576000848401525b50505050565b600061159b82611541565b6115a5818561154c565b93506115b581856020860161155d565b6115be8161121d565b840191505092915050565b600081519050919050565b600082825260208201905092915050565b60006115f0826115c9565b6115fa81856115d4565b935061160a81856020860161155d565b6116138161121d565b840191505092915050565b600060c0820190506116336000830189611519565b6116406020830188611519565b61164d6040830187611532565b818103606083015261165f8186611590565b9050818103608083015261167381856115e5565b905081810360a083015261168781846115e5565b9050979650505050505050565b6000806000604084860312156116ad576116ac61114b565b5b60006116bb8682870161119e565b935050602084013567ffffffffffffffff8111156116dc576116db611150565b5b6116e8868287016111c2565b92509250509250925092565b60006101008201905061170a600083018b611532565b818103602083015261171c818a6115e5565b905061172b6040830189611519565b6117386060830188611519565b6117456080830187611532565b81810360a08301526117578186611590565b905081810360c083015261176b81856115e5565b905081810360e083015261177f81846115e5565b90509998505050505050505050565b600080600080606085870312156117a8576117a761114b565b5b60006117b68782880161119e565b94505060206117c78782880161119e565b935050604085013567ffffffffffffffff8111156117e8576117e7611150565b5b6117f4878288016111c2565b925092505092959194509250565b60008060008060006060868803121561181e5761181d61114b565b5b600086013567ffffffffffffffff81111561183c5761183b611150565b5b611848888289016111c2565b9550955050602086013567ffffffffffffffff81111561186b5761186a611150565b5b611877888289016111c2565b9350935050604086013567ffffffffffffffff81111561189a57611899611150565b5b6118a68882890161132b565b9150509295509295909350565b7f4f6e6c79206973737565722063616e2075706461746520616e2065786973746960008201527f6e6720636c61696d000000000000000000000000000000000000000000000000602082015250565b600061190f6028836115d4565b915061191a826118b3565b604082019050919050565b6000602082019050818103600083015261193e81611902565b9050919050565b600081905092915050565b600061195c8385611945565b93506119698385846112da565b82840190509392505050565b6000611982828486611950565b91508190509392505050565b600061199a83856115d4565b93506119a78385846112da565b6119b08361121d565b840190509392505050565b600060408201905081810360008301526119d681858761198e565b90506119e56020830184611532565b949350505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611a3457607f821691505b60208210811415611a4857611a476119ed565b5b50919050565b7f4f6e6c79206973737565722063616e2072656d6f766520636c61696d00000000600082015250565b6000611a84601c836115d4565b9150611a8f82611a4e565b602082019050919050565b60006020820190508181036000830152611ab381611a77565b9050919050565b6000602082019050611acf6000830184611532565b9291505056fea264697066735822122082911b66106092369bbd48d4650f6ecf13941f17ba392f8ec97283f68004e85664736f6c634300080a0033";

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
