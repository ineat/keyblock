package com.keyblock.test;

import com.keyblock.ClaimsRegistryConnector;
import com.keyblock.ClaimsRegistryInterface;
import com.keyblock.model.Claim;
import com.keyblock.model.TransactionReceipt;
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

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClaimsRegistryTest {

    private static final Logger log = LogManager.getLogger(ClaimsRegistryTest.class);

    ClaimsRegistryInterface registry;

    @InjectMocks
    IAMMock iam;
    @BeforeAll
    public void initRegistry() {
        log.info("Init smart contract");
        this.registry = new ClaimsRegistryConnector(
                "HTTP://127.0.0.1:7545"
                ,"0x1aB579CFc1FBD64D2e12bB05B98ee59E2bFfb556"
                ,"0xbF440eB37F81BC3AD7fBe61DdA4C64EF0aF9A589"
                ,"9bfaae91a5c57277948c7e13a8829422e83d5f0a86a85a7855e2825d9ad92921"
        );
        assertNotNull(registry);
    }

    @Test
    public void whenLookForUnknownUser_thenError() {
        UserMock user = iam.getUser(IAMMock.UNKNOWN_USER);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNull(claim);
    }

    @Test
    public void whenLookForUserNoAddress_thenError() {
        UserMock user = iam.getUser(IAMMock.NO_ADDRESS_USER);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNull(claim);
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
    public void whenUpdateSync_thenAdminChanged() throws IOException, ExecutionException, InterruptedException {
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
    public void whenUpdateAsyncWithWait_thenAdminChanged() throws IOException, ExecutionException, InterruptedException {
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
    public void whenUpdateASyncWithListener_thenAdminChanged() throws ExecutionException, InterruptedException, IOException {
        UserMock user = iam.getUser(IAMMock.USER_TO_UPDATE);
        assertNotNull(user);

        TestTxListener listener = new TestTxListener(registry);

        String txHash = registry.setClaimAsync(user.getUserAddress(), IAMMock.ADMIN_CLAIM, listener.getTestClaimValue());
        registry.subscribe(txHash, listener);
        assertEquals(registry.getListeners().size(),1);
        log.info("Listener has subscribe, wait for notify");

        Future<TransactionReceipt> completableFuture = listener.getReceipt();
        log.info("CompletableFuture received");
        TransactionReceipt receipt = completableFuture.get();
        log.info("Check for receipt");
        assertNotNull(receipt);

    }

}
