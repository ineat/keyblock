package com.keyblock.converter;

import com.keyblock.model.TxReceipt;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Mapper
public interface TransactionReceiptMapper {

    TransactionReceiptMapper INSTANCE = Mappers.getMapper( TransactionReceiptMapper.class );

    TxReceipt fromWeb3jTransactionReceipt(TransactionReceipt from);
}
