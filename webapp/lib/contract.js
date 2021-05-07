
var contractAddress = "0x864cf6e7547C37B06c9C49fa4B3D681B273E8E0b";
var contract;

var abi = [
          	{
          		"inputs": [],
          		"stateMutability": "nonpayable",
          		"type": "constructor"
          	},
          	{
          		"anonymous": false,
          		"inputs": [
          			{
          				"indexed": true,
          				"internalType": "address",
          				"name": "issuer",
          				"type": "address"
          			},
          			{
          				"indexed": true,
          				"internalType": "address",
          				"name": "subject",
          				"type": "address"
          			},
          			{
          				"indexed": true,
          				"internalType": "string",
          				"name": "key",
          				"type": "string"
          			},
          			{
          				"indexed": false,
          				"internalType": "uint256",
          				"name": "removedAt",
          				"type": "uint256"
          			}
          		],
          		"name": "ClaimRemoved",
          		"type": "event"
          	},
          	{
          		"anonymous": false,
          		"inputs": [
          			{
          				"indexed": true,
          				"internalType": "address",
          				"name": "issuer",
          				"type": "address"
          			},
          			{
          				"indexed": true,
          				"internalType": "address",
          				"name": "subject",
          				"type": "address"
          			},
          			{
          				"indexed": true,
          				"internalType": "string",
          				"name": "key",
          				"type": "string"
          			},
          			{
          				"indexed": false,
          				"internalType": "string",
          				"name": "value",
          				"type": "string"
          			},
          			{
          				"indexed": false,
          				"internalType": "uint256",
          				"name": "updatedAt",
          				"type": "uint256"
          			}
          		],
          		"name": "ClaimSet",
          		"type": "event"
          	},
          	{
          		"inputs": [
          			{
          				"internalType": "address",
          				"name": "issuer",
          				"type": "address"
          			},
          			{
          				"internalType": "address",
          				"name": "subject",
          				"type": "address"
          			},
          			{
          				"internalType": "string",
          				"name": "key",
          				"type": "string"
          			}
          		],
          		"name": "removeClaim",
          		"outputs": [],
          		"stateMutability": "nonpayable",
          		"type": "function"
          	},
          	{
          		"inputs": [
          			{
          				"internalType": "address",
          				"name": "subject",
          				"type": "address"
          			},
          			{
          				"internalType": "string",
          				"name": "key",
          				"type": "string"
          			},
          			{
          				"internalType": "string",
          				"name": "value",
          				"type": "string"
          			},
          			{
          				"internalType": "bytes",
          				"name": "signature",
          				"type": "bytes"
          			}
          		],
          		"name": "setClaim",
          		"outputs": [],
          		"stateMutability": "nonpayable",
          		"type": "function"
          	},
          	{
          		"inputs": [
          			{
          				"internalType": "string",
          				"name": "key",
          				"type": "string"
          			},
          			{
          				"internalType": "string",
          				"name": "value",
          				"type": "string"
          			},
          			{
          				"internalType": "bytes",
          				"name": "signature",
          				"type": "bytes"
          			}
          		],
          		"name": "setSelfClaim",
          		"outputs": [],
          		"stateMutability": "nonpayable",
          		"type": "function"
          	},
          	{
          		"inputs": [
          			{
          				"internalType": "bytes32",
          				"name": "msgHash",
          				"type": "bytes32"
          			},
          			{
          				"internalType": "uint8",
          				"name": "v",
          				"type": "uint8"
          			},
          			{
          				"internalType": "bytes32",
          				"name": "r",
          				"type": "bytes32"
          			},
          			{
          				"internalType": "bytes32",
          				"name": "s",
          				"type": "bytes32"
          			}
          		],
          		"name": "checkSignature",
          		"outputs": [
          			{
          				"internalType": "address",
          				"name": "",
          				"type": "address"
          			}
          		],
          		"stateMutability": "pure",
          		"type": "function"
          	},
          	{
          		"inputs": [
          			{
          				"internalType": "address",
          				"name": "subject",
          				"type": "address"
          			},
          			{
          				"internalType": "string",
          				"name": "key",
          				"type": "string"
          			}
          		],
          		"name": "getClaim",
          		"outputs": [
          			{
          				"internalType": "uint256",
          				"name": "",
          				"type": "uint256"
          			},
          			{
          				"internalType": "string",
          				"name": "",
          				"type": "string"
          			},
          			{
          				"internalType": "address",
          				"name": "",
          				"type": "address"
          			}
          		],
          		"stateMutability": "view",
          		"type": "function"
          	},
          	{
          		"inputs": [
          			{
          				"internalType": "address",
          				"name": "",
          				"type": "address"
          			},
          			{
          				"internalType": "string",
          				"name": "",
          				"type": "string"
          			}
          		],
          		"name": "registry",
          		"outputs": [
          			{
          				"internalType": "address",
          				"name": "subject",
          				"type": "address"
          			},
          			{
          				"internalType": "address",
          				"name": "issuer",
          				"type": "address"
          			},
          			{
          				"internalType": "uint256",
          				"name": "issuedAt",
          				"type": "uint256"
          			},
          			{
          				"internalType": "bytes",
          				"name": "issuerSignature",
          				"type": "bytes"
          			},
          			{
          				"internalType": "string",
          				"name": "key",
          				"type": "string"
          			},
          			{
          				"internalType": "string",
          				"name": "value",
          				"type": "string"
          			}
          		],
          		"stateMutability": "view",
          		"type": "function"
          	}
          ];