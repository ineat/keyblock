/**
* Init connections and display data when page is loaded.
*/
window.addEventListener('load', async() => {
    /* Create connection to blockchain */
    initWeb3();

    /* Load the contract */
    loadContract();

    /* Display data read from blockchain */
    displayBlockchainInfo();

    /* Display data read from blockchain */
    displayUserInfo();
});

/**
* Create a Web3 object to connect to blockchain
*/
async function initWeb3() {
    // Modern dapp browsers...
    if (window.ethereum) {
        ethereum.autoRefreshOnNetworkChange = true;
        window.web3 = new Web3(ethereum);
        try {
            // Request account access if needed
            await ethereum.enable();
            console.log("Ethereum enabled with account : "+ethereum.selectedAddress);
        } catch (error) {
            console.error("Access denied for metamask by user");
        }

        // refresh page on account change
        ethereum.on("accountsChanged", (accounts) => { document.location.reload(true); });

    }
    // Legacy dapp browsers...
    else if (window.web3) {
        window.web3 = new Web3(web3.currentProvider);
    }
    // Non-dapp browsers...
    else {
        console.log('Non-Ethereum browser detected. You should consider trying MetaMask!');
    }

    console.log("web3 : "+web3.version);
}

/**
* Retrieve and basic info from blockchain node
*/
async function displayBlockchainInfo() {
    $('#web3Version').text(web3.version);
    $('#nodeInfo').text(await web3.eth.getNodeInfo());
    $('#blockNumber').text(await web3.eth.getBlockNumber());

    web3.eth.getAccounts()
    .then( async (accounts) => {

        let account = accounts[0];
        console.log("Account : "+account);
        $('#account').text(account);

        let balance = await web3.eth.getBalance(account);
        let balanceInEth = web3.utils.fromWei(balance);
        console.log("Balance : "+balanceInEth);
        $('#balance').text(balanceInEth);
    })
    .catch( (error) => {
        console.error("Error getting accounts : "+error);
    });

}

async function displayUserInfo() {
    web3.eth.getAccounts()
    .then( async (accounts) => {
        let account = accounts[0];
        $('#userIdentity').text(account);

        var result = contract.methods.getClaim(account, "isadmin").call({from:account})
        .then((isAdmin) => {
            console.log("Call isadmin : "+isAdmin);
            $('#userAdmin').text((isAdmin)=="true");
        });

        console.log("Request user signature");
        ethereum
          .request({
            method: 'eth_sign',
            params: [
                account
                , web3.utils.toHex("message")
            ]
          })
          .then((result) => {
            console.log("Sign: "+result);
          })
          .catch((error) => {
            console.log("Sign fail: "+error);
          });



    })
    .catch( (error) => {
        console.error("Error getting accounts : "+error);
    });
}

/**
* Init the contract objects
*/
async function loadContract() {
    if(contractAddress) {
        console.log("Load contract at : "+contractAddress);
        try {
            contract =  new web3.eth.Contract(abi, contractAddress);
        }
        catch(error) {
            console.error("Error loading contract : "+error);
        }
    }
}