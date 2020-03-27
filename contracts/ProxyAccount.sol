pragma solidity ^0.6.0;

import "./ERC725.sol";

contract ProxyAccount is ERC725 {
  /*
   *  Storage
   */
  address public owner;
  mapping(bytes32 => bytes) public store;

  /*
   *  Modifiers
   */
  modifier onlyOwner() {
    require(owner == msg.sender, "only-owner-allowed");
    _;
  }

  /*
   * Public functions
   */
  /// @dev Contract constructor sets initial owner.
  constructor() public {
    owner = msg.sender;
  }

  /// @dev Get stored data by key.
  /// @param _key Mapping key.
  /// @return Returns the data stored for that key.
  function getData(bytes32 _key) external view returns (bytes memory value) {
    return store[_key];
  }

  /// @dev Allows the owner of the contract to set new data.
  /// @param _key Mapping key to use.
  /// @param _value Data to store.
  function setData(bytes32 _key, bytes calldata _value) external onlyOwner {
    store[_key] = _value;
  }
}
