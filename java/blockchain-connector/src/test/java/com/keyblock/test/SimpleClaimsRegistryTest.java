package com.keyblock.test;

import com.keyblock.Claim;
import com.keyblock.ClaimsRegistryInterface;
import com.keyblock.Context;
import com.keyblock.SimpleClaimsRegistryConnector;
import com.keyblock.observable.TransactionListener;
import com.keyblock.test.mock.IAMMock;
import com.keyblock.test.mock.UserMock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimpleClaimsRegistryTest {

    private static final Logger log = LogManager.getLogger(SimpleClaimsRegistryTest.class);

    ClaimsRegistryInterface registry;

    @InjectMocks
    IAMMock iam;

    @BeforeAll
    public void initRegistry() {
        log.info("Init smart contract");
        this.registry = new SimpleClaimsRegistryConnector(new Context(Context.ContextFlavor.SIMPLECLAIMREGISTRY_INFURA_ROPSTEN));
        assertNotNull(registry);
    }

    @Test
    public void whenReadAdminUser_ThenIsAdmin() {
        UserMock user = iam.getUser(IAMMock.USER_ALWAYS_ADMIN);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);

        assertEquals(claim.getValue(), "true");
        assertEquals(claim.getSubjectAddress(), user.getUserAddress());
    }

    @Test
    public void whenReadNotAdminUser_ThenIsNotAdmin() {
        UserMock user = iam.getUser(IAMMock.USER_NEVER_ADMIN);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);

        assertEquals(claim.getValue(), "false");
        assertEquals(claim.getSubjectAddress(), user.getUserAddress());
    }

    @Test
    public void whenUpdateSync_thenAdminChanged() {
        UserMock user = iam.getUser(IAMMock.USER_TO_UPDATE);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);
        Boolean value = Boolean.valueOf(claim.getValue());
        Boolean newValue = Boolean.valueOf(!value);

        TransactionReceipt txReceipt = registry.setClaimSync(user.getUserAddress(), IAMMock.ADMIN_CLAIM, newValue.toString());
        assertNotNull(txReceipt);

        Claim newClaim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(newClaim);
        assertEquals(newClaim.getValue(), newValue.toString());
    }

    @Test
    public void whenUpdateAsyncWithWait_thenAdminChanged() {
        UserMock user = iam.getUser(IAMMock.USER_TO_UPDATE);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);
        Boolean value = Boolean.valueOf(claim.getValue());
        Boolean newValue = Boolean.valueOf(!value);

        String txHash = registry.setClaimAsync(user.getUserAddress(), IAMMock.ADMIN_CLAIM, newValue.toString());
        assertNotNull(txHash);

        TransactionReceipt txReceipt = registry.waitForReceipt(txHash);
        assertNotNull(txReceipt);

        Claim newClaim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(newClaim);
        assertEquals(newClaim.getValue(), newValue.toString());
    }

    @Test
    public void whenUpdateASyncWithListener_thenAdminChanged() {

    }

    @Test
    public void whenLookForUnknownUser_thenError() {
        UserMock user = iam.getUser(IAMMock.UNKNOWN_USER);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNull(claim);
    }
}
