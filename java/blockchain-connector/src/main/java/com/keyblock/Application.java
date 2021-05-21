package com.keyblock;

public class Application {
    public static void main(String[] args) {

        SimpleClaimsRegistryConnector bc = new SimpleClaimsRegistryConnector();

        String claimId = "isadmin";
        String subjectAddress = "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14";

        System.out.println(subjectAddress+" "+claimId+": " +bc.getClaim(subjectAddress,claimId).getValue());
    }

}
