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

    function setClaim(address subject, string calldata key, string calldata value, bytes memory signature) public {


        bytes32 claimHash = prefixed(keccak256(abi.encodePacked(msg.sender, subject, key, value)));

        require(recoverSigner(claimHash, signature) == msg.sender,"Do you really sign this?");

        Claim memory claim = Claim(subject, msg.sender, block.timestamp, signature, key, value);
        registry[subject][key] = claim;
        emit ClaimSet(msg.sender, subject, key, value, block.timestamp);
    }

    function setSelfClaim(string calldata key, string calldata value) public {
        setClaim(msg.sender, key, value);
    }

    function getClaim(address subject, string calldata key) public view returns(string memory) {
        Claim memory claim = registry[subject][key];
        // require(claim.subject == subject,"Unknown user or key");
        return claim.value;
    }

    function removeClaim(address issuer, address subject, string calldata key) public {
        require(msg.sender == issuer);
        delete registry[subject][key];
        emit ClaimRemoved(msg.sender, subject, key, block.timestamp);
    }


    function splitSignature(bytes memory sig) internal pure returns (uint8 v, bytes32 r, bytes32 s) {
        require(sig.length == 65);

        assembly {
        // first 32 bytes, after the length prefix.
            r := mload(add(sig, 32))
        // second 32 bytes.
            s := mload(add(sig, 64))
        // final byte (first byte of the next 32 bytes).
            v := byte(0, mload(add(sig, 96)))
        }

        return (v, r, s);
    }


    function recoverSigner(bytes32 message, bytes memory sig) internal pure returns (address) {
        (uint8 v, bytes32 r, bytes32 s) = splitSignature(sig);
        return ecrecover(message, v, r, s);
    }

    /// builds a prefixed hash to mimic the behavior of eth_sign.
    function prefixed(bytes32 hash) internal pure returns (bytes32) {
        return keccak256(abi.encodePacked("\x19Ethereum Signed Message:\n32", hash));
    }

    function checkSignature(bytes32 msgHash, uint8 v, bytes32 r, bytes32 s) public pure returns (address) {
        return ecrecover(msgHash, v, r, s);
    }

}