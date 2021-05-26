package com.keyblock.test;

import com.keyblock.Context;
import com.keyblock.SimpleClaimsRegistryConnector;
import org.junit.Test;

public class ClaimsRegistryTest {

    SimpleClaimsRegistryConnector registry;

    @Test
    public void testClaims() {
        registry = new SimpleClaimsRegistryConnector(new Context(Context.ContextFlavor.SIMPLECLAIMREGISTRY_INFURA_ROPSTEN));

        /*registry.setClaim();

        registry.getClaim();*/
    }

}
