package com.keyblock.model;

import com.keyblock.converter.TransactionReceiptConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.web3j.protocol.core.methods.response.Log;

import java.util.List;

/**
 * Handles the receipt of a transaction
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TxReceipt {

    private String transactionHash;
    private String transactionIndex;
    private String blockHash;
    private String blockNumber;
    private String cumulativeGasUsed;
    private String gasUsed;
    private String contractAddress;
    private String root;
    private String status;
    private String from;
    private String to;
    private List<Log> logs;
    private String logsBloom;
    private String revertReason;

    public static TxReceipt fromWeb3TransactionReceipt(org.web3j.protocol.core.methods.response.TransactionReceipt txReceipt) {
        return new TransactionReceiptConverter().convert(txReceipt);
    }

}
