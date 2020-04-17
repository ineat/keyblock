pragma solidity ^0.6.0;

contract Ownable {
    address payable owner;

    // Contract constructor: set owner
    constructor() public {
        owner = msg.sender;
    }

    // Access control modifier
    modifier onlyOwner {
        require(msg.sender == owner, "Only the contract owner can call this function");
        _;
    }

    // Contract destructor
    function destroy() public onlyOwner {
        selfdestruct(owner);
    }

}