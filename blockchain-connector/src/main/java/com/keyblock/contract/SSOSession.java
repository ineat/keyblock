package com.keyblock.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class SSOSession extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611a39806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80631fa5d6a4146100515780636f0dccba1461006d5780638c8e13b91461009d578063dc1b058b146100cd575b600080fd5b61006b60048036038101906100669190611105565b6100fd565b005b61008760048036038101906100829190611223565b6103a6565b6040516100949190611363565b60405180910390f35b6100b760048036038101906100b29190611105565b61089f565b6040516100c491906114d2565b60405180910390f35b6100e760048036038101906100e291906114f4565b610b24565b6040516100f4919061155a565b60405180910390f35b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508173ffffffffffffffffffffffffffffffffffffffff168160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16146101d1576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101c8906115c1565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff168160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610263576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161025a9061162d565b60405180910390fd5b60008160040154905060008260040181905550600182600001604051610289919061174d565b9081526020016040518091039020600080820160006102a89190610d78565b6001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055600382016000905560048201600090556005820160006103169190610db8565b50507f26372039e9b9d76124333c21780d8f3bffc11594f0b8429c66146d05f1fe63d58260010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168360020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168460030154846040516103999493929190611782565b60405180910390a1505050565b606060008060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050600073ffffffffffffffffffffffffffffffffffffffff168160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614806104f55750600073ffffffffffffffffffffffffffffffffffffffff168160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141580156104f457503373ffffffffffffffffffffffffffffffffffffffff168160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b5b806105cb5750600073ffffffffffffffffffffffffffffffffffffffff168160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141580156105ac57503373ffffffffffffffffffffffffffffffffffffffff168160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614155b80156105ca575042816004015482600301546105c891906117f6565b105b5b61060a576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610601906118e4565b60405180910390fd5b878782600001919061061d929190610df8565b50858160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550338160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555042816003018190555084816004018190555083838260050191906106c9929190610e7e565b5080600189896040516106dd929190611938565b908152602001604051809103902060008201816000019080546106ff9061167c565b61070a929190610f04565b506001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506002820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600382015481600301556004820154816004015560058201816005019080546108019061167c565b61080c929190610f91565b509050507f7df0a1e899ee3257ecc2bec498eeee1a019cec8ed34ebf53ff9cbd5d8d515821863342886040516108459493929190611782565b60405180910390a187878080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509150509695505050505050565b6108a761101e565b60008060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206040518060c00160405290816000820180546109029061167c565b80601f016020809104026020016040519081016040528092919081815260200182805461092e9061167c565b801561097b5780601f106109505761010080835404028352916020019161097b565b820191906000526020600020905b81548152906001019060200180831161095e57829003601f168201915b505050505081526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016003820154815260200160048201548152602001600582018054610a549061167c565b80601f0160208091040260200160405190810160405280929190818152602001828054610a809061167c565b8015610acd5780601f10610aa257610100808354040283529160200191610acd565b820191906000526020600020905b815481529060010190602001808311610ab057829003601f168201915b5050505050815250509050600073ffffffffffffffffffffffffffffffffffffffff16816020015173ffffffffffffffffffffffffffffffffffffffff161415610b1a5780915050610b1f565b809150505b919050565b60008060018484604051610b39929190611938565b90815260200160405180910390206040518060c0016040529081600082018054610b629061167c565b80601f0160208091040260200160405190810160405280929190818152602001828054610b8e9061167c565b8015610bdb5780601f10610bb057610100808354040283529160200191610bdb565b820191906000526020600020905b815481529060010190602001808311610bbe57829003601f168201915b505050505081526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016003820154815260200160048201548152602001600582018054610cb49061167c565b80601f0160208091040260200160405190810160405280929190818152602001828054610ce09061167c565b8015610d2d5780601f10610d0257610100808354040283529160200191610d2d565b820191906000526020600020905b815481529060010190602001808311610d1057829003601f168201915b5050505050815250509050806000015181604001518260200151604051602001610d59939291906119ca565b6040516020818303038152906040528051906020012091505092915050565b508054610d849061167c565b6000825580601f10610d965750610db5565b601f016020900490600052602060002090810190610db49190611080565b5b50565b508054610dc49061167c565b6000825580601f10610dd65750610df5565b601f016020900490600052602060002090810190610df49190611080565b5b50565b828054610e049061167c565b90600052602060002090601f016020900481019282610e265760008555610e6d565b82601f10610e3f57803560ff1916838001178555610e6d565b82800160010185558215610e6d579182015b82811115610e6c578235825591602001919060010190610e51565b5b509050610e7a9190611080565b5090565b828054610e8a9061167c565b90600052602060002090601f016020900481019282610eac5760008555610ef3565b82601f10610ec557803560ff1916838001178555610ef3565b82800160010185558215610ef3579182015b82811115610ef2578235825591602001919060010190610ed7565b5b509050610f009190611080565b5090565b828054610f109061167c565b90600052602060002090601f016020900481019282610f325760008555610f80565b82601f10610f435780548555610f80565b82800160010185558215610f8057600052602060002091601f016020900482015b82811115610f7f578254825591600101919060010190610f64565b5b509050610f8d9190611080565b5090565b828054610f9d9061167c565b90600052602060002090601f016020900481019282610fbf576000855561100d565b82601f10610fd0578054855561100d565b8280016001018555821561100d57600052602060002091601f016020900482015b8281111561100c578254825591600101919060010190610ff1565b5b50905061101a9190611080565b5090565b6040518060c0016040528060608152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff1681526020016000815260200160008152602001606081525090565b5b80821115611099576000816000905550600101611081565b5090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006110d2826110a7565b9050919050565b6110e2816110c7565b81146110ed57600080fd5b50565b6000813590506110ff816110d9565b92915050565b60006020828403121561111b5761111a61109d565b5b6000611129848285016110f0565b91505092915050565b600080fd5b600080fd5b600080fd5b60008083601f84011261115757611156611132565b5b8235905067ffffffffffffffff81111561117457611173611137565b5b6020830191508360018202830111156111905761118f61113c565b5b9250929050565b6000819050919050565b6111aa81611197565b81146111b557600080fd5b50565b6000813590506111c7816111a1565b92915050565b60008083601f8401126111e3576111e2611132565b5b8235905067ffffffffffffffff811115611200576111ff611137565b5b60208301915083600182028301111561121c5761121b61113c565b5b9250929050565b600080600080600080608087890312156112405761123f61109d565b5b600087013567ffffffffffffffff81111561125e5761125d6110a2565b5b61126a89828a01611141565b9650965050602061127d89828a016110f0565b945050604061128e89828a016111b8565b935050606087013567ffffffffffffffff8111156112af576112ae6110a2565b5b6112bb89828a016111cd565b92509250509295509295509295565b600081519050919050565b600082825260208201905092915050565b60005b838110156113045780820151818401526020810190506112e9565b83811115611313576000848401525b50505050565b6000601f19601f8301169050919050565b6000611335826112ca565b61133f81856112d5565b935061134f8185602086016112e6565b61135881611319565b840191505092915050565b6000602082019050818103600083015261137d818461132a565b905092915050565b600082825260208201905092915050565b60006113a1826112ca565b6113ab8185611385565b93506113bb8185602086016112e6565b6113c481611319565b840191505092915050565b6113d8816110c7565b82525050565b6113e781611197565b82525050565b600081519050919050565b600082825260208201905092915050565b6000611414826113ed565b61141e81856113f8565b935061142e8185602086016112e6565b61143781611319565b840191505092915050565b600060c083016000830151848203600086015261145f8282611396565b915050602083015161147460208601826113cf565b50604083015161148760408601826113cf565b50606083015161149a60608601826113de565b5060808301516114ad60808601826113de565b5060a083015184820360a08601526114c58282611409565b9150508091505092915050565b600060208201905081810360008301526114ec8184611442565b905092915050565b6000806020838503121561150b5761150a61109d565b5b600083013567ffffffffffffffff811115611529576115286110a2565b5b61153585828601611141565b92509250509250929050565b6000819050919050565b61155481611541565b82525050565b600060208201905061156f600083018461154b565b92915050565b7f4e6f2073657373696f6e20666f756e6420666f72207375626a65637400000000600082015250565b60006115ab601c836112d5565b91506115b682611575565b602082019050919050565b600060208201905081810360008301526115da8161159e565b9050919050565b7f4f6e6c79206973737565722063616e207265766f6b6520612073657373696f6e600082015250565b60006116176020836112d5565b9150611622826115e1565b602082019050919050565b600060208201905081810360008301526116468161160a565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061169457607f821691505b602082108114156116a8576116a761164d565b5b50919050565b600081905092915050565b60008190508160005260206000209050919050565b600081546116db8161167c565b6116e581866116ae565b94506001821660008114611700576001811461171157611744565b60ff19831686528186019350611744565b61171a856116b9565b60005b8381101561173c5781548189015260018201915060208101905061171d565b838801955050505b50505092915050565b600061175982846116ce565b915081905092915050565b61176d816110c7565b82525050565b61177c81611197565b82525050565b60006080820190506117976000830187611764565b6117a46020830186611764565b6117b16040830185611773565b6117be6060830184611773565b95945050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061180182611197565b915061180c83611197565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff03821115611841576118406117c7565b5b828201905092915050565b7f43616e6e6f74206f7665727269646520616e206578697374696e672076616c6960008201527f642073657373696f6e206372656174656420627920616e6f746865722070726f60208201527f7669646572000000000000000000000000000000000000000000000000000000604082015250565b60006118ce6045836112d5565b91506118d98261184c565b606082019050919050565b600060208201905081810360008301526118fd816118c1565b9050919050565b82818337600083830152505050565b600061191f83856116ae565b935061192c838584611904565b82840190509392505050565b6000611945828486611913565b91508190509392505050565b600061195c826112ca565b61196681856116ae565b93506119768185602086016112e6565b80840191505092915050565b60008160601b9050919050565b600061199a82611982565b9050919050565b60006119ac8261198f565b9050919050565b6119c46119bf826110c7565b6119a1565b82525050565b60006119d68286611951565b91506119e282856119b3565b6014820191506119f282846119b3565b60148201915081905094935050505056fea2646970667358221220ce26769c89f8eb77f6167d79a1b95074b3a8fcbca5708573859d4c409020752764736f6c634300080a0033";

    public static final String FUNC_CREATESESSION = "createSession";

    public static final String FUNC_GETSESSION = "getSession";

    public static final String FUNC_GETSESSIONHASHDATA = "getSessionHashData";

    public static final String FUNC_REVOKESESSION = "revokeSession";

    public static final Event SESSIONCREATED_EVENT = new Event("SessionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SESSIONREVOKED_EVENT = new Event("SessionRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected SSOSession(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SSOSession(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SSOSession(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SSOSession(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<SessionCreatedEventResponse> getSessionCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SESSIONCREATED_EVENT, transactionReceipt);
        ArrayList<SessionCreatedEventResponse> responses = new ArrayList<SessionCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SessionCreatedEventResponse typedResponse = new SessionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.subject = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.issuer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.issuanceDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.validityTime = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SessionCreatedEventResponse> sessionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SessionCreatedEventResponse>() {
            @Override
            public SessionCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SESSIONCREATED_EVENT, log);
                SessionCreatedEventResponse typedResponse = new SessionCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.subject = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.issuer = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.issuanceDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.validityTime = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SessionCreatedEventResponse> sessionCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SESSIONCREATED_EVENT));
        return sessionCreatedEventFlowable(filter);
    }

    public List<SessionRevokedEventResponse> getSessionRevokedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SESSIONREVOKED_EVENT, transactionReceipt);
        ArrayList<SessionRevokedEventResponse> responses = new ArrayList<SessionRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SessionRevokedEventResponse typedResponse = new SessionRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.subject = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.issuer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.issuanceDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.initialValidityTime = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SessionRevokedEventResponse> sessionRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SessionRevokedEventResponse>() {
            @Override
            public SessionRevokedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SESSIONREVOKED_EVENT, log);
                SessionRevokedEventResponse typedResponse = new SessionRevokedEventResponse();
                typedResponse.log = log;
                typedResponse.subject = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.issuer = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.issuanceDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.initialValidityTime = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SessionRevokedEventResponse> sessionRevokedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SESSIONREVOKED_EVENT));
        return sessionRevokedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createSession(String sessionId, String subject, BigInteger validityTime, byte[] signature) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATESESSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(sessionId), 
                new org.web3j.abi.datatypes.Address(160, subject), 
                new org.web3j.abi.datatypes.generated.Uint256(validityTime), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Session> getSession(String subject) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSESSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, subject)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Session>() {}));
        return executeRemoteCallSingleValueReturn(function, Session.class);
    }

    public RemoteFunctionCall<byte[]> getSessionHashData(String sessionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSESSIONHASHDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(sessionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeSession(String subject) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOKESESSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, subject)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SSOSession load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SSOSession(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SSOSession load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SSOSession(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SSOSession load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SSOSession(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SSOSession load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SSOSession(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SSOSession> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SSOSession.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SSOSession> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SSOSession.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SSOSession> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SSOSession.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SSOSession> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SSOSession.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Session extends DynamicStruct {
        public String sessionId;

        public String subject;

        public String issuer;

        public BigInteger issuanceDate;

        public BigInteger validityTime;

        public byte[] signature;

        public Session(String sessionId, String subject, String issuer, BigInteger issuanceDate, BigInteger validityTime, byte[] signature) {
            super(new org.web3j.abi.datatypes.Utf8String(sessionId),new org.web3j.abi.datatypes.Address(subject),new org.web3j.abi.datatypes.Address(issuer),new org.web3j.abi.datatypes.generated.Uint256(issuanceDate),new org.web3j.abi.datatypes.generated.Uint256(validityTime),new org.web3j.abi.datatypes.DynamicBytes(signature));
            this.sessionId = sessionId;
            this.subject = subject;
            this.issuer = issuer;
            this.issuanceDate = issuanceDate;
            this.validityTime = validityTime;
            this.signature = signature;
        }

        public Session(Utf8String sessionId, Address subject, Address issuer, Uint256 issuanceDate, Uint256 validityTime, DynamicBytes signature) {
            super(sessionId,subject,issuer,issuanceDate,validityTime,signature);
            this.sessionId = sessionId.getValue();
            this.subject = subject.getValue();
            this.issuer = issuer.getValue();
            this.issuanceDate = issuanceDate.getValue();
            this.validityTime = validityTime.getValue();
            this.signature = signature.getValue();
        }
    }

    public static class SessionCreatedEventResponse extends BaseEventResponse {
        public String subject;

        public String issuer;

        public BigInteger issuanceDate;

        public BigInteger validityTime;
    }

    public static class SessionRevokedEventResponse extends BaseEventResponse {
        public String subject;

        public String issuer;

        public BigInteger issuanceDate;

        public BigInteger initialValidityTime;
    }
}
