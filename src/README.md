# Web3 browser

index.html page that provides web3.js framework.

# Files
- index.html : main file, html page to be displayed in browser to provide web3 browser
- index.js : used to serve index.html as a Node.js app
- contract.js : to bi filled with some smart contract info to interact with it
- web3.min.js : web3.js framework. **Current version : 1.2.4**

# How to use ?

## Apache

`cd www`

`git clone https://github.com/avysel/web3-browser.git`

Then start your browser on http://localhost/web3-browser

## Node.js

`git clone https://github.com/avysel/web3-browser.git`

`node index.js`

Then start your browser on http://localhost:3000

## Interact with a smart contract

In `contract.js` :
- set `contractAddress` with address of the contract on the appropriate network
- set `abi` with the ABI object of the contract

Il will provides `var contract` var to deal with it.