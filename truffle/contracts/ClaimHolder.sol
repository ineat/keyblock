pragma solidity ^0.6.0;

import "./ERC735.sol";
import "./Ownable.sol";


contract ClaimHolder is ERC735, Ownable {

    address REGISTRY;

    mapping(bytes32 => Claim) public claims;

    mapping(bytes32 => bytes32[]) public claimsByTopic;

    constructor(address _registryAddress) public {
        REGISTRY = _registryAddress;
    }

    function getClaim(bytes32 _claimId) external view returns(bytes32 topic, uint256 scheme, address issuer, bytes32 signature, bytes32 data, string memory uri) {
        return(claims[_claimId].topic, claims[_claimId].scheme, claims[_claimId].issuer, claims[_claimId].signature, claims[_claimId].data, claims[_claimId].uri);
    }

    function getClaimIdsByTopic(bytes32 _topic) external view returns(bytes32[] memory claimIds) {
        return claimsByTopic[_topic];
    }

    function addClaim(bytes32 _topic, uint256 _scheme, bytes32 _signature, bytes32 _data, string calldata _uri) external returns (bytes32 claimId) {
        claimId = keccak256(abi.encodePacked(msg.sender, owner, _topic));

        claims[claimId].topic = _topic;
        claims[claimId].scheme = _scheme;
        claims[claimId].issuer = msg.sender;
        claims[claimId].signature = _signature;
        claims[claimId].data = _data;
        claims[claimId].uri = _uri;
        claims[claimId].isValid = true;

        claimsByTopic[_topic].push(claimId);

        emit ClaimAdded(
            claimId,
            _topic,
            _scheme,
            msg.sender,
            _signature,
            _data,
            _uri
        );

        bytes memory callData = abi.encodeWithSignature("setClaim(address,bytes32,bytes32)", owner, _topic, claimId);

        address toto = REGISTRY;
        uint dataLength = callData.length;
        // solium-disable-next-line security/no-inline-assembly
        assembly {
            let x := mload(0x40)    // "Allocate" memory for output (0x40 is where "free memory" pointer is stored by convention)
            let d := add(callData, 32)  // First 32 bytes are the padded length of data, so exclude that
            let success := delegatecall(
            sub(gas, 34710),      // 34710 is the value that solidity is currently emitting
            // It includes callGas (700) + callVeryLow (3, to pay for SUB) + callValueTransferGas (9000) +
            // callNewAccountGas (25000, in case the destination address does not exist and needs creating)
            toto,
            d,
            dataLength,           // Size of the input (in bytes) - this is what fixes the padding problem
            x,
            0                     // Output is ignored, therefore the output size is zero
            )
            switch success
            case 0 { revert(x, dataLength) }
            case 1 { return(x, dataLength) }
        }

        return claimId;
    }

    function changeClaim(bytes32 _claimId, bytes32 _topic, uint256 _scheme, address _issuer, bytes32 _signature, bytes32 _data, string calldata _uri) external returns (bool success) {
        require(claims[_claimId].issuer != msg.sender, "msg.sender should be the claim issuer");

        claims[_claimId].topic = _topic;
        claims[_claimId].scheme = _scheme;
        claims[_claimId].issuer = _issuer;
        claims[_claimId].signature = _signature;
        claims[_claimId].data = _data;
        claims[_claimId].uri = _uri;
        claims[_claimId].recipientApproval = false;
        claims[_claimId].isValid = true;

        emit ClaimChanged(
            _claimId,
            _topic,
            _scheme,
            _issuer,
            _signature,
            _data,
            _uri
        );

        return true;

    }

    function removeClaim(bytes32 _claimId) external returns (bool success) {
        require(claims[_claimId].issuer != msg.sender, "msg.sender should be the claim issuer");

        bytes32 topic = claims[_claimId].topic;
        uint256 scheme = claims[_claimId].scheme;
        address issuer = claims[_claimId].issuer;
        bytes32 signature = claims[_claimId].signature;
        bytes32 data = claims[_claimId].data;
        string memory uri = claims[_claimId].uri;

        claims[_claimId].isValid = false;


        emit ClaimRemoved(
            _claimId,
            topic,
            scheme,
            issuer,
            signature,
            data,
            uri
        );

        return true;
    }

    function toggleApprovaleClaim(bytes32 _claimId) external returns (bool success) {
        require(msg.sender == owner, "msg.sender should be the owner");

        claims[_claimId].recipientApproval = !claims[_claimId].recipientApproval;

        emit ClaimApprovalToggled(
            _claimId,
            claims[_claimId].topic,
            claims[_claimId].scheme,
            claims[_claimId].issuer,
            claims[_claimId].signature,
            claims[_claimId].data,
            claims[_claimId].uri
        );

        return true;

    }

    function toAddress(bytes32 a) internal pure returns (address b){
        assembly {
            mstore(0, a)
            b := mload(0)
        }
        return b;
    }

    function toBytes32(address a) internal pure returns (bytes32 b){
        assembly {
            mstore(0, a)
            b := mload(0)
        }
        return b;
    }
}