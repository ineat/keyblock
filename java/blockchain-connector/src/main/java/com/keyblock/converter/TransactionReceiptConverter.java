package com.keyblock.converter;

import com.keyblock.model.TxReceipt;
import org.springframework.core.convert.converter.Converter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public  class TransactionReceiptConverter implements Converter<TransactionReceipt, TxReceipt> {

    @Override
    public TxReceipt convert(TransactionReceipt from) {
        return TransactionReceiptMapper.INSTANCE.fromWeb3jTransactionReceipt(from);
    }

    @Override
    public <U> Converter<TransactionReceipt, U> andThen(Converter<? super TxReceipt, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}

