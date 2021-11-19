package com.keyblock.test;

import com.keyblock.ClaimsRegistryConnector;
import com.keyblock.ClaimsRegistryInterface;
import com.keyblock.model.Claim;
import com.keyblock.model.TxReceipt;
import com.keyblock.test.mock.IAMMock;
import com.keyblock.test.mock.UserMock;
import com.keyblock.util.SignUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.SignatureException;
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
                ,"0xbBf538bdDDda8371c1c3d5BDf2143e8AeC2caA20"
                ,"0x2da92f7beaB763a7E975aecCe8F85B6F54be231e"
                ,"9fe18402c676e7aca98ac2ceb3a3c13fcab84cffa6814a8d996b73608edb6ad6"
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
        assertEquals(claim.getSubjectAddress().toLowerCase(), user.getUserAddress().toLowerCase());
    }

    @Test
    public void whenUpdateSync_thenAdminChanged() throws IOException, ExecutionException, InterruptedException {
        UserMock user = iam.getUser(IAMMock.USER_TO_UPDATE);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);
        Boolean value = Boolean.valueOf(claim.getValue());
        Boolean newValue = Boolean.valueOf(!value);

        TxReceipt txReceipt = registry.setClaimSync(user.getUserAddress(), IAMMock.ADMIN_CLAIM, newValue.toString());
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

        TxReceipt txReceipt = registry.waitForReceipt(txHash);
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

        Future<TxReceipt> completableFuture = listener.getReceipt();
        log.info("CompletableFuture received");
        TxReceipt receipt = completableFuture.get();
        log.info("Check for receipt");
        assertNotNull(receipt);

    }

    @Test
    public void whenCreateClaim_theSignatureRecovered() throws ExecutionException, InterruptedException, IOException, SignatureException {
        UserMock user = iam.getUser(IAMMock.USER_TO_UPDATE);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);

        boolean recover = SignUtil.checkSignature(claim.getSignatureDataString(), claim.getSignature(), claim.getIssuerAddress());
        assertTrue(recover);
    }

    @Test
    public void whenCreateClaimWrongSig_theSignatureNotRecovered() throws ExecutionException, InterruptedException, IOException, SignatureException {
        UserMock user = iam.getUser(IAMMock.USER_ALWAYS_ADMIN);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);

        boolean recover = SignUtil.checkSignature(claim.getSignatureDataString(), claim.getSignature(), claim.getIssuerAddress());
        assertFalse(recover);
    }
}
