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

});

/**
* Create a Web3 object to connect to blockchain
*/
async function initWeb3() {
    // Modern dapp browsers...
    if (window.ethereum) {

        window.web3 = new Web3(ethereum);

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
}

function identification() {
    ethereum.request({ method: 'eth_requestAccounts' })
    .then( (account) => {
        console.log("Ethereum enabled with account : "+account);
        $('#userIdentity').text(account);
     })
    .catch( (error) => {console.error(error);} )
}

function authentication() {
    web3.eth.getAccounts()
    .then( async (accounts) => {
        let account = accounts[0];
        askForSignature(account);
    })
    .catch( (error) => {
        console.error("Error getting accounts : "+error);
    });
}

function checkClaims() {
    web3.eth.getAccounts()
    .then( async (accounts) => {
        let account = accounts[0];

        var result = contract.methods.getClaim(account, "isadmin").call({from:account})
        .then((isAdmin) => {
            console.log("Call isadmin : "+isAdmin);
            $('#userAdmin').text(isAdmin+" (using claim holder "+contractAddress+")");
        })
        .catch( (error) => {
              $('#userAdmin').text(error);
              console.log(error);
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
            ,Contract:[
                {name:"name", type:"string"}
                ,{name:"address", type:"address"}
            ]
            ,User:[
                {name:"account", type:"address"}
            ]
            ,MessageToSign:[
                  {name:"content",type:"string"}
                  ,{name:"contract", type:"Contract"}
                  ,{name:"forUser", type:"User"}
            ]

           }
        ,primaryType:"MessageToSign"
        ,domain:{name:"Keyblock",version:"1",chainId:3,verifyingContract:"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"}
        ,message:{
            content:"You are going to use your private key to sign this data and be authenticated for claim holder contract."
            ,contract:{name:"Claim holder", address: contractAddress}
            ,forUser:{account: account}
            }
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
            $('#userAuthent').text("Yes, with signature: "+result);
          })
          .catch((error) => {
            console.log("Sign fail: "+error.message);
            $('#userAuthent').text(error.message);
          });
}