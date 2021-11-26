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
                "https://ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98"
                ,"0xaDe68eCf6F1bC7A4374B58FdFC4DF29Ebc7b26e6"
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

        Claim newClaim = new Claim();
        newClaim.setSubjectAddress(user.getUserAddress());
        newClaim.setKey(IAMMock.ADMIN_CLAIM);
        newClaim.setValue(newValue.toString());
        TxReceipt txReceipt = registry.setClaimSync(newClaim);
        assertNotNull(txReceipt);

        Claim newReadClaim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(newReadClaim);
        assertEquals(newReadClaim.getValue(), newValue.toString());
    }

    @Test
    public void whenUpdateAsyncWithWait_thenAdminChanged() throws IOException, ExecutionException, InterruptedException {
        UserMock user = iam.getUser(IAMMock.USER_TO_UPDATE);
        assertNotNull(user);

        Claim claim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(claim);
        Boolean value = Boolean.valueOf(claim.getValue());
        Boolean newValue = Boolean.valueOf(!value);

        Claim newClaim = new Claim();
        newClaim.setSubjectAddress(user.getUserAddress());
        newClaim.setKey(IAMMock.ADMIN_CLAIM);
        newClaim.setValue(newValue.toString());
        String txHash = registry.setClaimAsync(newClaim);
        assertNotNull(txHash);

        TxReceipt txReceipt = registry.waitForReceipt(txHash);
        assertNotNull(txReceipt);

        Claim newReadClaim = registry.getClaim(user.getUserAddress(),IAMMock.ADMIN_CLAIM);
        assertNotNull(newReadClaim);
        assertEquals(newReadClaim.getValue(), newValue.toString());
    }

    @Test
    public void whenUpdateASyncWithListener_thenAdminChanged() throws ExecutionException, InterruptedException, IOException {
        UserMock user = iam.getUser(IAMMock.USER_TO_UPDATE);
        assertNotNull(user);

        TestTxListener listener = new TestTxListener(registry);

        Claim newClaim = new Claim();
        newClaim.setSubjectAddress(user.getUserAddress());
        newClaim.setKey(IAMMock.ADMIN_CLAIM);
        newClaim.setValue(listener.getTestClaimValue());
        String txHash = registry.setClaimAsync(newClaim);
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
