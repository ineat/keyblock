// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.10;

contract SimpleClaimsRegistry {

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
        bytes issuerSignature;
        string key;
        string value;
    }

    // registry[subject][key] = Claim
    mapping(address => mapping(string => Claim)) public registry;

    constructor() {

    }

    function setClaim(address subject, string calldata key, string calldata value, bytes memory signature) public {
        Claim memory claim = Claim(subject, msg.sender, block.timestamp, signature, key, value);
        registry[subject][key] = claim;
        emit ClaimSet(msg.sender, subject, key, value, block.timestamp);
    }

    function setSelfClaim(string calldata key, string calldata value, bytes memory signature) public {
        setClaim(msg.sender, key, value, signature);
    }

    function getClaim(address subject, string calldata key) public view returns(uint, string memory, address) {
        Claim memory claim = registry[subject][key];

        if(claim.subject != subject) {
            return (1, "Subject or key not found", address(0));
        }

        return (0, claim.value, claim.issuer);
    }

    function removeClaim(address issuer, address subject, string calldata key) public {
        require(msg.sender == issuer);
        delete registry[subject][key];
        emit ClaimRemoved(msg.sender, subject, key, block.timestamp);
    }

}