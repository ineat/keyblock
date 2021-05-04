
var contractAddress = "0x85ff1399bc70b3e8116d970d209c17af84bfb0fa";
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
          				"internalType": "string",
          				"name": "",
          				"type": "string"
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
          				"internalType": "bytes32",
          				"name": "issuerSignature",
          				"type": "bytes32"
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
          			}
          		],
          		"name": "setSelfClaim",
          		"outputs": [],
          		"stateMutability": "nonpayable",
          		"type": "function"
          	}
          ];