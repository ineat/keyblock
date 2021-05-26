package com.keyblock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String... args) {

        SimpleClaimsRegistryConnector bc = new SimpleClaimsRegistryConnector(new Context(Context.ContextFlavor.INFURA_ROPSTEN));

        String claimId = "isadmin";
        String subjectAddress = "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14";

       log.info(subjectAddress+" "+claimId+": " +bc.getClaim(subjectAddress,claimId).getValue());

        bc.setClaim(subjectAddress, claimId, "false");
    }
}
