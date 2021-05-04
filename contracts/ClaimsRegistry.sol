// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract ClaimsRegistry {

    event ClaimSet(
        address indexed issuer,
        address indexed subject,
        string indexed key,
        string value,
        uint updatedAt);

    event ClaimRemoved(
        address indexed issuer,
        address indexed subject,
        string indexed key,
        uint removedAt);

    struct Claim {
        address subject;
        address issuer;
        uint issuedAt;
        bytes32 issuerSignature;
        string key;
        string value;
    }

    // registry[subject][key] = Claim
    mapping(address => mapping(string => Claim)) public registry;

    constructor() {

    }

    function setClaim(address subject, string calldata key, string calldata value) public {

        bytes32 signature = keccak256(abi.encode(subject, key, value));

        Claim memory claim = Claim(subject, msg.sender, block.timestamp, signature, key, value);
        registry[subject][key] = claim;
        emit ClaimSet(msg.sender, subject, key, value, block.timestamp);
    }

    function setSelfClaim(string calldata key, string calldata value) public {
        setClaim(msg.sender, key, value);
    }

    function getClaim(address subject, string calldata key) public view returns(string memory) {
        Claim memory claim = registry[subject][key];
        require(claim.subject == subject,"Unknown user");
        return claim.value;
    }

    function removeClaim(address issuer, address subject, string calldata key) public {
        require(msg.sender == issuer);
        delete registry[subject][key];
        emit ClaimRemoved(msg.sender, subject, key, block.timestamp);
    }

}