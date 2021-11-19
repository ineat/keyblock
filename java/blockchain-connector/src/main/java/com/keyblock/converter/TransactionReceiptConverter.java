package com.keyblock.converter;

import org.springframework.core.convert.converter.Converter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public  class TransactionReceiptConverter implements Converter<TransactionReceipt, com.keyblock.model.TransactionReceipt> {

    @Override
    public com.keyblock.model.TransactionReceipt convert(TransactionReceipt from) {
        return TransactionReceiptMapper.INSTANCE.fromWeb3jTransactionReceipt(from);
    }

    @Override
    public <U> Converter<TransactionReceipt, U> andThen(Converter<? super com.keyblock.model.TransactionReceipt, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}

