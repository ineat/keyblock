#!/bin/bash

# Compile smart contract to generate abi anb binary file, and copy abi to java blockchain connector directory

## Install solc, solidity compiler
# sudo add-apt-repository ppa:ethereum/ethereum
# sudo apt-get update
# sudo apt-get install solc

# compile contract
echo "Compile..."
solc ClaimsRegistry.sol --bin --abi -o ./build/ --overwrite

# copy abi and binary to java connector
echo "Copy file to java connector..."
cp ./build/ClaimsRegistry.abi ../java/blockchain-connector/src/main/resources/ClaimsRegistry.abi
cp ./build/ClaimsRegistry.bin ../java/blockchain-connector/src/main/resources/ClaimsRegistry.bin

# generate web3j classe for contract
echo "Generate java wrapper..."
cd build
web3j generate solidity -a=ClaimsRegistry.abi -b=ClaimsRegistry.bin -o=../java/blockchain-connector/src/main/java/com/keyblock/contract --package=com.keyblock.contract

echo "Done"