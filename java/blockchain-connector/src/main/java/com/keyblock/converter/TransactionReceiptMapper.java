package com.keyblock.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Mapper
public interface TransactionReceiptMapper {

    TransactionReceiptMapper INSTANCE = Mappers.getMapper( TransactionReceiptMapper.class );

    com.keyblock.api.TransactionReceipt fromWeb3jTransactionReceipt(TransactionReceipt from);
}
