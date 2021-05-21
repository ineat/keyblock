#!/bin/bash

# Compile smart contract to generate abi anb binary file, and copy abi to java blockchain connector directory

## Install solc, solidity compiler
# sudo add-apt-repository ppa:ethereum/ethereum
# sudo apt-get update
# sudo apt-get install solc


echo "Compile SimpleClaimsRegistry..."
solc SimpleClaimsRegistry.sol --bin --abi -o ./build/ --overwrite

# copy abi and binary to java connector
echo "Copy file to java connector..."
cp ./build/SimpleClaimsRegistry.abi ../java/blockchain-connector/src/main/resources/SimpleClaimsRegistry.abi
cp ./build/SimpleClaimsRegistry.bin ../java/blockchain-connector/src/main/resources/SimpleClaimsRegistry.bin

# generate web3j classe for contract
echo "Generate java wrapper..."
web3j generate solidity -a=./build/SimpleClaimsRegistry.abi -b=./build/SimpleClaimsRegistry.bin -o=../java/blockchain-connector/src/main/java/ --package=com.keyblock.contract

echo "Done"


# compile contract
echo "Compile ClaimsRegistry..."
solc ClaimsRegistry.sol --bin --abi -o ./build/ --overwrite

# copy abi and binary to java connector
echo "Copy file to java connector..."
cp ./build/ClaimsRegistry.abi ../java/blockchain-connector/src/main/resources/ClaimsRegistry.abi
cp ./build/ClaimsRegistry.bin ../java/blockchain-connector/src/main/resources/ClaimsRegistry.bin

# generate web3j classe for contract
echo "Generate java wrapper..."
web3j generate solidity -a=./build/ClaimsRegistry.abi -b=./build/ClaimsRegistry.bin -o=../java/blockchain-connector/src/main/java/ --package=com.keyblock.contract

echo "Done"