package com.keyblock.converter;

import org.springframework.core.convert.converter.Converter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public  class TransactionReceiptConverter implements Converter<TransactionReceipt, com.keyblock.api.TransactionReceipt> {

    @Override
    public com.keyblock.api.TransactionReceipt convert(TransactionReceipt from) {
        return TransactionReceiptMapper.INSTANCE.fromWeb3jTransactionReceipt(from);
    }

    @Override
    public <U> Converter<TransactionReceipt, U> andThen(Converter<? super com.keyblock.api.TransactionReceipt, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}

