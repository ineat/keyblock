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

    var shortMessage = "You are going to use your private key to sign this data and be authenticated for claim holder contract.";

    var EIP712Data = {
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
             ,forUser:{account: null}
             }
     };

    clearAuthent();

    ethereum.request({ method: 'eth_requestAccounts' })
    .then( async (accounts) => {
        let account = accounts[0];
        switch(id) {
            case 0:
                askForSignatureEthPersonal(account, shortMessage);
                break;
            case 1:
                EIP712Data.message.forUser.account = account;
                askForSignatureEIP712(account, EIP712Data);
                break;
            case 2:
                askForSignatureEthSign(account, shortMessage);
                break;
        }
    })
    .catch( (error) => {
        console.error("Error getting accounts : "+error);
    });
}

/**
* Add a prefix to message to sign, as used by some Ethereum client, like Geth
* @param message the original message
* @return prefixed message : '\x19Ethereum Signed Message:\n' + msg.length + msg;
*/
function prefixedMsg(message) {
    return '\x19Ethereum Signed Message:\n' + message.length + message;
}

/**
* Hash a message to sign using sha3 (keccak)
* @param message the message to hash
* @return the sha3 hashed message
*/
function messageHash(message) {
	return web3.utils.sha3(prefixedMsg(message));
}

async function askForSignatureEIP712(account, data) {

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

/**
* Chech signature using smart contract call
* @param signature the signature
* @account the account that is assumed to be the signer
* @message the signed message
*/

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
async function askForSignatureEthSign(account, message) {
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

async function askForSignatureEthPersonal(account, message) {

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

/**
* Check all claims referenced in db.claims
*/
function checkClaims() {

    // must have db and claims list
    if(!db) throw 'No DB found';
    if(!db.claims) throw 'No claim found';

    // request current account from browser wallet
    ethereum.request({ method: 'eth_requestAccounts' })
    .then( async (accounts) => {
        let account = accounts[0];

        // check each claim
        db.claims.forEach( (claimName) => {
            console.log("get claim: "+claimName+" for "+account);

            // contract call
            var result = contract.methods.getClaim(account, claimName).call({from:account})
            .then((result) => {
                console.log(result);
                // result[0] -> 0 = ok, 1 = ko
                // result[1] -> value or error message
                // TODO check result[2] that contains issuer address
                $('#'+claimName).text(result[1]);
            })
            .catch( (error) => {
                   console.error("call error");
                  var errorObject = extractError(error);
                  $('#'+claimName).text(errorObject.message);
                  console.log(errorObject.message);
            });
        })
    })
    .catch( (error) => {
        console.error("Error getting accounts : "+error);
    });
}

/**
* Extract an error object from a mixed string + json error returned when contract call fails
* @param errorMessage the error returned by call
* @return an error object
*/
function extractError(errorMessage) {
    // return is "Object" typed, must be casted to string
    errorMessage = errorMessage.toString();

    // error is composed of a string + a stringified json. Just extract json
    var textObject = errorMessage.substring(errorMessage.indexOf('{'));

    if(textObject) {
        // parse json to object then return
        var errorObject = JSON.parse(textObject);
        return errorObject.originalError;
    }
    else {
        return null;
    }
}

function callCreateClaim() {
    createClaim(db.claims[0], "true", "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14")
}

async function createClaim(claimName, claimValue, subject) {

    console.log("claimName: "+claimName);
    console.log("claimValue: "+claimValue);
    console.log("subject: "+subject);

    // create claim with value
    var claim = new Object();
    claim.subject = subject;
    claim.key = claimName;
    claim.value = claimValue;
    claim.issuedAt = Date.now();

    // request current account from browser wallet
    ethereum.request({ method: 'eth_requestAccounts' })
    .then( async (accounts) => {
        let account = accounts[0];
        console.log("issuer: "+account);

        claim.issuer = account;

        /*
        * Claim signature :
        * 1) Contatenate issuer + subject + key + value
        * 2) Hash to sha3 (keccak256)
        * 3) compute signature
        */

        // hash and prefix serialized claim object
        const hashedClaim = web3.utils.soliditySha3(claim.issuer, claim.subject, claim.key, claim.value);

        console.log("hashedClaim: "+hashedClaim);

        // sign hashed and prefixed claim

       web3.eth.sign(hashedClaim, account)
        .then(
          (signature) => {
            console.log("Claim signed: "+signature);

                contract.methods.setClaim(subject, claimName, claimValue, signature).estimateGas({from: account})
                .then(function(gasAmount){
                    console.log("gas: "+gasAmount);

                    // call contract to create
                    contract.methods.setClaim(subject, claimName, claimValue, signature).send({from:account, gas:gasAmount})
                    .then((result) => {
                        console.log(result);
                    })
                   .catch( (error) => {
                          console.log(error);
                    });

                })
                .catch(function(error){
                    console.log(error);
                });
        });

    })
    .catch( (error) => {
        console.error("createClaim : Error getting accounts : "+error);
    });

}

async function sign(message) {
    return new Promise ( (resolve, reject) => {

         console.log("Sign: "+message);

        ethereum.request({ method: 'eth_requestAccounts' })
        .then( async (accounts) => {
            let account = accounts[0];

            web3.eth.sign(message, account)
            .then(
              (signature) => {
                console.log("Claim signed: "+signature);
                resolve(signature);
            })
            .catch((error) => {
                console.log("Claim signature fail: "+error.message);
            });

        })
        .catch( (error) => {
            console.error("Sign : Error getting accounts : "+error);
        });

        reject();

    });
}