package com.keyblock;

import com.keyblock.api.SSOSession;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface SSOSessionInterface {

    public String createSession(String sessionId, String subjectAddress, long endValidityDateTimestamp, String signature) throws IOException, ExecutionException, InterruptedException;

    public String revokeSession(String subjectAddress) throws IOException, ExecutionException, InterruptedException;

    public SSOSession getSession(String subjectAddress) throws Exception;

    public String getSessionHashData(String sessionId);

}