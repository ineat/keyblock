// SPDX-License-Identifier: UNLICENCED
pragma solidity 0.8.9;


/**
 * Smart contract to handle SSO sessions for cross organisation SSO through blockchain.
 */
contract SSOSession {

    event SessionCreated(
        address subject,
        address issuer,
        uint issuanceDate,
        uint endValidityDate
    );

    event SessionRevoked(
        address subject,
        address issuer,
        uint issuanceDate,
        uint initialEndValidityDate
    );

    struct Session {
        string sessionId;
        address subject;
        address issuer;
        uint issuanceDate;
        uint endValidityDate;
        bytes signature;
    }

    // user address => session
    mapping (address => Session) private sessions;

    // sessionId => session
    mapping (string => Session) private sessionsById;


    /**
     * Creates a new session for a user, or update it if the session has been create by the same issuer
     */
    function createSession(string calldata sessionId, address subject, uint endValidityDate, bytes calldata signature) public returns (string memory) {

        Session storage session = sessions[subject];

        // Session must not exist yet, or be created by the same issuer, or be expired
        require( ( session.subject == address(0x0) )
        || ( session.subject != address(0x0) && session.issuer == msg.sender )
            || ( session.subject != address(0x0) && session.issuer != msg.sender && session.endValidityDate < block.timestamp )
        ,"Cannot override an existing valid session created by another provider");


        session.sessionId = sessionId;
        session.subject = subject;
        session.issuer = msg.sender;
        session.issuanceDate = block.timestamp;
        session.endValidityDate = endValidityDate;
        session.signature = signature;

        sessionsById[sessionId] = session;

        emit SessionCreated(subject, msg.sender, block.timestamp, endValidityDate);

        return sessionId;
    }


    /**
     * Revoke a session. It will look for a session for given user. If a session is found, the end validity date will be set to block.timestamp.
     * Only session issuer can revoke a session.
     * @param subject the concerned subject address
     */
    function revokeSession(address subject) public {

        Session storage session = sessions[subject];

        // Check that a session exists for the given subject
        require(session.subject == subject, "No session found for subject");

        // Check that only issuer can revoke session it created
        require(session.issuer == msg.sender, "Only issuer can revoke a session");

        uint initialEndValidityDate = session.endValidityDate;

        // Set new validity date timestamp to zero
        session.endValidityDate = block.timestamp;

        delete sessionsById[session.sessionId];

        emit SessionRevoked(session.subject, session.issuer, session.issuanceDate, initialEndValidityDate);

    }


    /**
     * Get a session from subject address
     */
    function getSession(address subject) public view returns (Session memory) {

        Session memory session = sessions[subject];

        if(session.subject == address(0x0))
            return session; // TODO what can be done in such a case ?
        else
            return session;
    }

    /**
     * Return hashed data for a session
     */
    function getSessionHashData(string calldata sessionId) public view returns (bytes32) {

        Session memory session = sessionsById[sessionId];

        return keccak256(abi.encodePacked(session.sessionId, session.issuer, session.subject));

    }

}