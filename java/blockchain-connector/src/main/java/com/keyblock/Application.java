package com.keyblock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String... args) {

        SimpleClaimsRegistryConnector bc = new SimpleClaimsRegistryConnector(new Context(Context.ContextFlavor.SIMPLECLAIMREGISTRY_INFURA_ROPSTEN));

        String claimId = "isadmin";
        String subjectAddress = "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14";

        log.info("1. "+subjectAddress+" "+claimId+": " +bc.getClaim(subjectAddress,claimId).getValue());

       // TransactionReceipt txR = bc.setClaimSync(subjectAddress, claimId, "true");

       /* log.info("2. "+bc.getClaim(subjectAddress,claimId).getValue());

        String txH = bc.setClaimAsync(subjectAddress, claimId, "false");

        log.info("3.");
        bc.subscribe(txH, new TxReceiver(bc));

        log.info("4. "+bc.getClaim(subjectAddress,claimId).getValue());
*/
        log.info("Done");
/*
        // always admin user
        bc.setClaimSync("0x8433e11fd3c2dc5f9e44558be8fccce8db0fcd1e", claimId, "true");

        // never admin user
        bc.setClaimSync("0xB9E9A10Ba80518A5a70893d14CCcB6b5A9343fEF", claimId, "false");*/

    }

}
