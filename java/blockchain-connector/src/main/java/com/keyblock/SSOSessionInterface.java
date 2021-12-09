package com.keyblock;

import com.keyblock.model.SSOSession;
import com.keyblock.model.TxReceipt;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Handles connection with SSOSession smart contract
 */
public interface SSOSessionInterface {

    /**
     * Create a session for a user, non blocking.
     * @param ssoSession the SSOSession object to be created
     * @return the transaction hash
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createSessionAsync(SSOSession ssoSession) throws Exception;

    /**
     * Revoke a session. Set end validity date to zero for the user current session. Non blocking
     * @param subjectAddress the ethereum address of subject to be revoked
     * @return the transaction hash
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String revokeSessionAsync(String subjectAddress) throws Exception;

    /**
     * Create a session for a user. Blocking until transaction is validated
     * @param ssoSession
     * @return the transaction receipt
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public TxReceipt createSessionSync(SSOSession ssoSession) throws Exception;

    /**
     * Revoke a session. Set end validity date to zero for the user current session. Blocking until transaction is validated
     * @param subjectAddress the ethereum address of subject to be revoked
     * @return the transaction hash
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public TxReceipt revokeSessionSync(String subjectAddress) throws Exception;

    /**
     * Get the current sso session for a user
     * @param subjectAddress the subject address
     * @return the related SSOSession if exist, null if the address zero is returned
     * @throws Exception
     */
    public SSOSession getSession(String subjectAddress) throws Exception;

    /**
     * Return true if a given user has a current active session
     * @param subjectAddress the ethereum address of user to check session
     * @return true if given user has an active session, false otherwise
     * @throws Exception
     */
    public boolean isSessionActive(String subjectAddress) throws Exception;
}