pragma solidity ^0.8.0;

interface IERC780 {
    event ClaimSet(
        address indexed issuer,
        address indexed subject,
        bytes32 indexed key,
        bytes32 value,
        uint updatedAt);

    event ClaimRemoved(
        address indexed issuer,
        address indexed subject,
        bytes32 indexed key,
        uint removedAt);

    // create or update clams
    function setClaim(address subject, bytes32 key, bytes32 value) public;

    function setSelfClaim(bytes32 key, bytes32 value) public;

    function getClaim(address issuer, address subject, bytes32 key) public view returns(bytes32);

    function removeClaim(address issuer, address subject, bytes32 key) public;
}
