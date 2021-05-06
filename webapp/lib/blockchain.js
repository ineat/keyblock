//const sigUtil = require("./bundles/ethSigUtil.js");

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

    // testSignatures();
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

/**
* Retrieve and basic info from blockchain node
*/
async function displayBlockchainInfo() {
    $('#web3Version').text(web3.version);
    $('#nodeInfo').text(await web3.eth.getNodeInfo());
    $('#blockNumber').text(await web3.eth.getBlockNumber());
    $('#contractAddress').text(contractAddress);
}

function identification() {
    ethereum.request({ method: 'eth_requestAccounts' })
    .then( (account) => {
        console.log("Ethereum enabled with account : "+account);
        $('#userIdentity').text(account);
     })
    .catch( (error) => {console.error(error);} )
}

function clearAuthent() {
    $('#recover').text("");
    $('#signatureCheck').text("");
    $('#userAuthent').text("");
    $('#signature').text("");
}

function authentication(id) {
    clearAuthent();
    ethereum.request({ method: 'eth_requestAccounts' })
    .then( async (accounts) => {
        let account = accounts[0];
        switch(id) {
            case 0:
                askForSignatureEthPersonal(account);
                break;
            case 1:
                askForSignatureEIP712(account);
                break;
            case 2:
                askForSignatureEthSign(account);
                break;
        }
    })
    .catch( (error) => {
        console.error("Error getting accounts : "+error);
    });
}

function checkClaims() {
    ethereum.request({ method: 'eth_requestAccounts' })
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

function prefixedMsg(msg) {
    return '\x19Ethereum Signed Message:\n' + msg.length + msg;
}

function messageHash(msg) {
	return web3.utils.sha3(prefixedMsg(msg));
}

async function askForSignatureEIP712(account) {
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

    ethereum.request({
        method: 'eth_signTypedData_v4',
        params: [
            account,
            JSON.stringify(data)
        ]
    })
    .then((signature) => {
        console.log("Signature: "+signature);
        $('#userAuthent').text("Yes");
        $('#signature').text(signature);

        //checkSignature(signature, account, JSON.stringify(data));²

        const recovered = sigUtil.recoverTypedSignature_v4({
            data: data
            , sig: signature
        });

        console.log("Rec: "+recovered);

        $('#userAuthent').text("Yes");
        $('#signature').text(signature);
        $('#recover').text( recovered);
        $('#signatureCheck').text( (account.toLowerCase() === recovered.toLowerCase()) );

    })
    .catch((error) => {
        console.log("Sign fail: "+error.message);
        $('#userAuthent').text(error.message);
    });
}


function checkSignature(signature, account, message) {

    var r = signature.slice(0, 66);
    var s = '0x' + signature.slice(66, 130);
    var v = '0x' + signature.slice(130, 132);

    console.log('r: '+r);
    console.log('s: '+ s);
    console.log('v: '+ v);
    console.log("msg: "+message);

    var result = contract.methods.checkSignature(web3.utils.sha3(message), web3.utils.hexToNumber(v), r, s).call({from:account})
    .then( (resultAddress) => {
         $('#recover').text( resultAddress);
         $('#signatureCheck').text( (resultAddress==account) );
    });
}

/**
* Sign using eth_sign
* Not recommended because message is displayed in hex to user
*/
async function askForSignatureEthSign(account) {

    var message = "You are going to use your private key to sign this data and be authenticated for claim holder contract.";
    console.log("eth.sign");

    web3.eth.sign(message, account)
    .then(
      (signature) => {
        console.log("Signature: "+signature);
        $('#userAuthent').text("Yes");
        $('#signature').text(signature);

        web3.eth.personal.ecRecover(message, signature)
        .then(
            (result) => {
                 $('#recover').text( result);
                 $('#signatureCheck').text( (result.toLowerCase() === account.toLowerCase())? "ok" : "ko" );
          });

    })
    .catch((error) => {
        console.log("Sign fail: "+error.message);
        $('#userAuthent').text(error.message);
    });
}

async function askForSignatureEthPersonal(account) {

    var message = "You are going to use your private key to sign this data and be authenticated for claim holder contract.";

    web3.eth.personal.sign(message, account)
    .then(
      (signature) => {
        console.log("Signature: "+signature);
        $('#userAuthent').text("Yes");
        $('#signature').text(signature);

        web3.eth.personal.ecRecover(message, signature)
        .then(
            (result) => {
                 $('#recover').text( result);
                 $('#signatureCheck').text( (result.toLowerCase() === account.toLowerCase())? "ok" : "ko" );
          });

    })
    .catch((error) => {
        console.log("Sign fail: "+error.message);
        $('#userAuthent').text(error.message);
    });
}

/*
async function testSignatures() {

    var data = {
            types:{
                "EIP712Domain":[
                    {name:"name",type:"string"}
                    ,{name:"version",type:"string"}
                    ,{name:"chainId",type:"uint256"}
                    ,{name:"verifyingContract",type:"address"}
                ]
                ,MessageToSign:[
                      {name:"content",type:"string"}
                ]
               }
            ,primaryType:"MessageToSign"
            ,domain:{name:"Keyblock",version:"1",chainId:3,verifyingContract:"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"}
            ,message:{
                content:"You are going to use your private key to sign this data and be authenticated for claim holder contract."
                }
        };

    var account = "";
    var pk = "";

    var message = JSON.stringify(data); //"Hello toto";
    var hashedMessage = web3.utils.sha3(message);
    var prefixedMessage = prefixedMsg(message);
    var hashedPrefixedMessage = messageHash(message);

    console.log("hashedMessage: "+hashedMessage);
    console.log("hashedPrefixedMessage: "+hashedPrefixedMessage);
    console.log("web3.eth.accounts.hashMessage(message):"+web3.eth.accounts.hashMessage(message));

    console.log("===== web3.eth.accounts.sign =====")
    var res1 = web3.eth.accounts.sign(message, pk);
    console.log("Signature web3.eth.accounts.sign: ");
    console.log(res1);

    var res2 = web3.eth.accounts.recover(res1);
    console.log(res2);
    console.log("==> Recover: "+(res2===account));

    console.log("==== web3.eth.accounts.sign check with contract =====")

    var result = contract.methods.checkSignature(res1.messageHash, res1.v, res1.r, res1.s).call({from:account})
    .then( (result) => {
                 console.log("recover with contract: "+result);
                 console.log("==> Recover with contract: "+(result.toLowerCase()===account.toLowerCase()));
    });

    console.log("===== web3.eth.personal.sign =====")

    web3.eth.personal.sign(message, account)
    .then(
      (signature) => {
        console.log("Signature web3.eth.personal.sign: "+signature);

        web3.eth.personal.ecRecover(message, signature)
        .then(
            (result) => {
                 console.log("recover web3.eth.personal.ecRecover: "+result);
                 console.log("==> Recover: "+(result.toLowerCase()===account.toLowerCase()));
          });

    })
    .catch((error) => {
        console.log("Sign fail: "+error.message);
        $('#userAuthent').text(error.message);
    });

 console.log("===== eth_signTypedData_v4 =====")

    ethereum.request({
            method: 'eth_signTypedData_v4',
            params: [
                account,
                message
            ]
        })
        .then((signature) => {
            console.log("Signature eth_signTypedData_v4: "+signature);
            console.log("Signature message hash eth_signTypedData_v4: "+messageHash(signature));
            checkSignature(signature, account, message);
    })
    .catch((error) => {
        console.log("Sign fail: "+error.message);
        $('#userAuthent').text(error.message);
    });

}*/

