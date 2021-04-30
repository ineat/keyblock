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

        window.web3 = new Web3(ethereum);

        ethereum.request({ method: 'eth_requestAccounts' })
        .then( (account) => { console.log("Ethereum enabled with account : "+account);} )
        .catch( (error) => {console.error(error);} )

        // refresh page on account or network change
        ethereum.on("accountsChanged", (accounts) => { window.location.reload(true); });
        ethereum.on("chainChanged", (accounts) => { window.location.reload(true); });

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

        console.log("Request user signature for "+account);
        askForSignature(account);

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

async function askForSignature(account) {

 /*   var data = {
        types:{
            "EIP712Domain":[
                {name:"name",type:"string"}
                ,{name:"version",type:"string"}
                ,{name:"chainId",type:"uint256"}
                ,{name:"verifyingContract",type:"address"}
            ]
            ,Person:[
                {name:"name",type:"string"}
                ,{name:"wallet",type:"address"}
            ]
            ,Mail:[
                {name:"from",type:"Person"}
                ,{name:"to",type:"Person"}
                ,{name:"contents",type:"string"}
            ]
           }
        ,primaryType:"Mail"
        ,domain:{name:"Keyblock",version:"1",chainId:3,verifyingContract:"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"}
        ,message:{
            from:{name:"Cow",wallet:"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826"}
            ,to:{name:"Bob",wallet:"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB"}
            ,contents:"Hello, Bob!"
        }
    };
*/

var data = {
        types:{
            "EIP712Domain":[
                {name:"name",type:"string"}
                ,{name:"version",type:"string"}
                ,{name:"chainId",type:"uint256"}
                ,{name:"verifyingContract",type:"address"}
            ]
            ,Message:[
                  {name:"content",type:"string"}
            ]

           }
        ,primaryType:"Message"
        ,domain:{name:"Keyblock",version:"1",chainId:3,verifyingContract:"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"}
        ,message:{content:"coucou"}
    };

        ethereum
          .request({
            method: 'eth_signTypedData_v4',
            params: [
                account,
                JSON.stringify(data)
            ]
          })
          .then((result) => {
            console.log("Sign: "+result);
          })
          .catch((error) => {
            console.log("Sign fail: "+JSON.parse(error));
          });


}