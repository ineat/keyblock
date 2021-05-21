package com.keyblock;

public class Application {
    public static void main(String[] args) {

        SimpleClaimsRegistryConnector bc = new SimpleClaimsRegistryConnector();

        System.out.println(bc.getClaim("",""));
    }
}
