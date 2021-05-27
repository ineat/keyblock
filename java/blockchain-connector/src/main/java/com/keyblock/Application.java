package com.keyblock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String... args) {

        SimpleClaimsRegistryConnector bc = new SimpleClaimsRegistryConnector(new Context(Context.ContextFlavor.SIMPLECLAIMREGISTRY_INFURA_ROPSTEN));

        String claimId = "isadmin";
        String subjectAddress = "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14";

        log.info("1. "+subjectAddress+" "+claimId+": " +bc.getClaim(subjectAddress,claimId).getValue());

        TransactionReceipt txR = bc.setClaimSync(subjectAddress, claimId, "true");

        log.info("2. "+bc.getClaim(subjectAddress,claimId).getValue());

        String txH = bc.setClaimAsync(subjectAddress, claimId, "false");

        log.info("3. "+bc.getClaim(subjectAddress,claimId).getValue());

        log.info("Done");
    }
}
