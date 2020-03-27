pragma solidity ^0.6.0;

contract ERC735 {

    event ClaimRequested(uint256 indexed claimRequestId, uint256 indexed topic, uint256 scheme, address indexed issuer, bytes signature, bytes data, string uri);
    event ClaimAdded(bytes32 indexed claimId, uint256 indexed topic, uint256 scheme, address indexed issuer, bytes signature, bytes data, string uri);
    event ClaimRemoved(bytes32 indexed claimId, uint256 indexed topic, uint256 scheme, address indexed issuer, bytes signature, bytes data, string uri);
    event ClaimChanged(bytes32 indexed claimId, uint256 indexed topic, uint256 scheme, address indexed issuer, bytes signature, bytes data, string uri);

    struct Claim {
        uint256 topic;
        uint256 scheme;
        address issuer; // msg.sender
        bytes signature; // this.address + topic + data
        bytes data;
        string uri;
    }

    function getClaim(bytes32 _claimId) public constant returns(uint256 topic, uint256 scheme, address issuer, bytes signature, bytes data, string uri);
    function getClaimIdsByTopic(uint256 _ topic) public constant returns(bytes32[] claimIds);
    function addClaim(uint256 _topic, uint256 _scheme, address _issuer, bytes _signature, bytes _data, string _uri) public returns (uint256 claimRequestId);
    changeClaim(bytes32 _claimId, uint256 _topic, uint256 _scheme, address _issuer, bytes _signature, bytes _data, string _uri) returns (bool success);
    function removeClaim(bytes32 _claimId) public returns (bool success);
}
