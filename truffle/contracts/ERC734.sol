pragma solidity ^0.6.0;

abstract contract ERC734 {

    uint256 constant MANAGEMENT_KEY = 1;
    uint256 constant EXECUTION_KEY = 2;

    event KeyAdded(bytes32 indexed key, uint256 indexed purpose, uint256 indexed keyType);
    event KeyRemoved(bytes32 indexed key, uint256 indexed purpose, uint256 indexed keyType);
    event ExecutionRequested(uint256 indexed executionId, address indexed to, uint256 indexed value, bytes data);
    event Executed(uint256 indexed executionId, address indexed to, uint256 indexed value, bytes data);
    event Approved(uint256 indexed executionId, bool approved);
    event KeysRequiredChanged(uint256 purpose, uint256 number);

    struct Key {
        uint256 purpose; //e.g., MANAGEMENT_KEY = 1, EXECUTION_KEY = 2, etc.
        uint256 keyType; // e.g. 1 = ECDSA, 2 = RSA, etc.
        bytes32 key;
    }

    function getKey(bytes32 _key) virtual public view returns(uint256[] memory purposes, uint256 keyType, bytes32 key);
    function keyHasPurpose(bytes32 _key, uint256 _purpose) virtual public view returns (bool exists);
    function getKeysByPurpose(uint256 _purpose) virtual public view returns (bytes32[] memory keys);
    function addKey(bytes32 _key, uint256 _purpose, uint256 _keyType) virtual public returns (bool success);
    function removeKey(bytes32 _key, uint256 _purpose) virtual public returns (bool success);
    function changeKeysRequired(uint256 purpose, uint256 number) virtual public;
    function getKeysRequired(uint256 purpose) virtual public view returns(uint256);
    function execute(address _to, uint256 _value, bytes memory _data) virtual public returns (uint256 executionId);
    function approve(uint256 _id, bool _approve) virtual public returns (bool success);
}
