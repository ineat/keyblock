pragma solidity ^0.6.0;

import "./IERC780.sol";

contract ClaimsRegistry is IERC780 {

    mapping(address => mapping(address => mapping(bytes32 => bytes32))) public registry;

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
    function setClaim(address subject, bytes32 key, bytes32 value) public {
        registry[msg.sender][subject][key] = value;
        emit ClaimSet(msg.sender, subject, key, value, now);
    }

    function setSelfClaim(bytes32 key, bytes32 value) public {
        setClaim(msg.sender, key, value);
    }

    function getClaim(address issuer, address subject, bytes32 key) public view returns(bytes32) {
        return registry[issuer][subject][key];
    }

    function removeClaim(address issuer, address subject, bytes32 key) public {
        require(msg.sender == issuer);
        delete registry[issuer][subject][key];
        emit ClaimRemoved(msg.sender, subject, key, now);
    }
}

/*
contract ClaimRegistry is ERC780 {

    mapping(address => mapping(address => mapping(bytes32 => bytes32))) public registry;
    mapping(bytes32 => address) public claimHolders;

    // create or update clams
    function setClaim(address _subject, bytes32 _key, bytes32 _value) public {
        registry[msg.sender][_subject][_key] = _value;
        claimHolders[_value] = _subject;

        emit ClaimSet(
            msg.sender,
            _subject,
            _key,
            _value
        );
    }

    function setSelfClaim(bytes32 key, bytes32 value) public {
        setClaim(msg.sender, key, value);
    }

    function getClaim(address _issuer, address _subject, bytes32 _key) public view returns(bytes32 claimId) {
        return registry[_issuer][_subject][_key];
    }

    function removeClaim(address _issuer, address _subject, bytes32 _key) public {
        require(msg.sender == _issuer);

        delete claimHolders[registry[_issuer][_subject][_key]];
        delete registry[_issuer][_subject][_key];

        emit ClaimRemoved(
            msg.sender,
            _subject,
            _key
        );
    }
}*/
