
var simpleClaimsRegistry = {
    address: "0xad9388311e96031d9cF2D1370826D8940d057362"
    ,abi: [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"uint256","name":"removedAt","type":"uint256"}],"name":"ClaimRemoved","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"string","name":"value","type":"string"},{"indexed":false,"internalType":"uint256","name":"updatedAt","type":"uint256"}],"name":"ClaimSet","type":"event"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"getClaim","outputs":[{"internalType":"uint256","name":"","type":"uint256"},{"internalType":"string","name":"","type":"string"},{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"","type":"address"},{"internalType":"string","name":"","type":"string"}],"name":"registry","outputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"address","name":"issuer","type":"address"},{"internalType":"uint256","name":"issuedAt","type":"uint256"},{"internalType":"bytes","name":"issuerSignature","type":"bytes"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"issuer","type":"address"},{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"removeClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setSelfClaim","outputs":[],"stateMutability":"nonpayable","type":"function"}]
};

var claimsRegistry = {
    address: "0x3827c01e72db65712bbc405cba29e3d21147a66b"
    , abi: [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"uint256","name":"removedAt","type":"uint256"}],"name":"ClaimRemoved","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"string","name":"value","type":"string"},{"indexed":false,"internalType":"uint256","name":"updatedAt","type":"uint256"}],"name":"ClaimSet","type":"event"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"getClaim","outputs":[{"internalType":"uint256","name":"","type":"uint256"},{"internalType":"string","name":"","type":"string"},{"internalType":"address","name":"","type":"address"},{"internalType":"address","name":"","type":"address"},{"internalType":"uint256","name":"","type":"uint256"},{"internalType":"bytes","name":"","type":"bytes"},{"internalType":"string","name":"","type":"string"},{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"","type":"address"},{"internalType":"string","name":"","type":"string"}],"name":"registry","outputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"address","name":"issuer","type":"address"},{"internalType":"uint256","name":"issuedAt","type":"uint256"},{"internalType":"bytes","name":"issuerSignature","type":"bytes"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"issuer","type":"address"},{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"removeClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setSelfClaim","outputs":[],"stateMutability":"nonpayable","type":"function"}]
 }

var contractData = claimsRegistry;


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
    if(contractData.address) {
        //console.log("Load contract : "+JSON.stringify(contractData));
        try {
            contract =  new web3.eth.Contract(contractData.abi, contractData.address);
        }
        catch(error) {
            console.error("Error loading contract : "+error);
        }
    }
}

function authentication(id) {

    var authentPromise;

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
             ,contract:{name:"Claim holder", address: contractData.address}
             ,forUser:{account: null}
             }
     };

    return new Promise ( async (resolve, reject) => {

        var accounts = await ethereum.request({ method: 'eth_requestAccounts' });
        var account = accounts[0];

        switch(id) {
            case 0:
                authentPromise = askForSignatureEthPersonal(account, shortMessage);
                break;
            case 1:
                EIP712Data.message.forUser.account = account;
                authentPromise = askForSignatureEIP712(account, EIP712Data);
                break;
            case 2:
                authentPromise = askForSignatureEthSign(account, shortMessage);
                break;
        }
        console.log("resolve: "+authentPromise);
        resolve(authentPromise);

    })
}

/**
* Sign using eth_personal
*/
function askForSignatureEthPersonal(account, message) {

    return new Promise( (resolve, reject) => {

        var authentResult = {
            userAuthent: undefined,
            signature: undefined,
            signatureCheck: undefined,
            recover: undefined
        }

        web3.eth.personal.sign(message, account)
        .then(
            (sig) => {
                console.log("Signature personal: "+sig);
                authentResult.userAuthent="Yes";
                authentResult.signature=sig;

                console.log(web3.eth.accounts.recover(message, sig));

                return web3.eth.personal.ecRecover(message, sig);
        })
        .then(
            (result) => {
                authentResult.recover = result;
                authentResult.signatureCheck = (result.toLowerCase() === account.toLowerCase())? "ok" : "ko";

                console.log(JSON.stringify(authentResult));
                resolve(authentResult);
        })
        .catch((error) => {
            console.log("Sign fail: "+error.message);
            authentResult.userAuthent=error.message;
            console.log(JSON.stringify(authentResult));
            reject(authentResult);
        });

    });
}

/**
* Sign using eth_signTypedData_v4
* EIP-712 compliant
*/
function askForSignatureEIP712(account, data) {

    return new Promise( (resolve, reject) => {

        var authentResult = {
            userAuthent: undefined,
            signature: undefined,
            signatureCheck: undefined,
            recover: undefined
        }

        ethereum.request({
            method: 'eth_signTypedData_v4',
            params: [
                account,
                JSON.stringify(data)
            ]
        })
        .then( (sig) => {
              console.log("Signature eip712: "+sig);

                const Http = new XMLHttpRequest();
                const url='http://localhost:3001/checkSig?data='+JSON.stringify(data)+'&address='+account+'&signature='+sig;
                Http.open("GET", url);
                Http.setRequestHeader('Access-Control-Allow-Origin','*');
                Http.send();

            const recovered = sigUtil.recoverTypedSignature_v4({
                data: data
                , sig: sig
            });

            authentResult.userAuthent="Yes";
            authentResult.signature = sig;
            authentResult.recover = recovered;
            authentResult.signatureCheck = (recovered.toLowerCase() === account.toLowerCase())? "ok" : "ko";

            resolve(authentResult);
        })
        .catch((error) => {
            console.log("Sign fail: "+error.message);
            authentResult.userAuthent=error.message;

            reject(authentResult);
        });

    });
}

/**
* Sign using eth_sign
* Not recommended because message is displayed in hex to user
*/
function askForSignatureEthSign(account, message) {

    console.log("eth.sign");

    return new Promise( (resolve, reject) => {

        var authentResult = {
            userAuthent: undefined,
            signature: undefined,
            signatureCheck: undefined,
            recover: undefined
        }

        web3.eth.sign(message, account)
        .then(
          (sig) => {
            console.log("signature sign: "+sig);
            authentResult.userAuthent = "Yes";
            authentResult.signature = sig;

            return web3.eth.personal.ecRecover(message, sig);
        })
        .then(
          (result) => {
            console.log("recover: "+result);
            authentResult.recover = result;
            authentResult.signatureCheck = (result.toLowerCase() === account.toLowerCase())? "ok" : "ko";

            console.log(JSON.stringify(authentResult));
            resolve(authentResult);
        })
        .catch((error) => {
            console.log("Sign fail: "+error.message);
            authentResult.userAuthent=error.message;

            console.log(JSON.stringify(authentResult));
            reject(authentResult);
        });
    });
}

/**
* Check all claims referenced in db.claims
*/
async function checkClaims() {

    var claims = [];

    // must have db and claims list
    if(!db) throw 'No DB found';
    if(!db.claims) throw 'No claim found';

    var accounts = await ethereum.request({ method: 'eth_requestAccounts' });
    var account = accounts[0];
    //console.log("acc: "+ account);

    await Promise.all(db.claims.map(
       async (claim) => {
            // contract call to get claim value and metadata
            var result = await contract.methods.getClaim(account, claim.name).call({from:account})

            // result[0] -> 0 = ok, 1 = ko
            // result[1] -> error message if ko
            // result[2] -> subject
            // result[3] -> issuer
            // result[4] -> issuedAt
            // result[5] -> signature
            // result[6] -> key
            // result[7] -> value

            // look for claim issuer in local db
            var trustedIssuer = db.issuers.find(issuer => issuer.id == claim.issuer);

            // if issuer is found, is trusted and is the same as claimed, then check signature
            if(result[0]==0 && trustedIssuer && result[3]==trustedIssuer.address && trustedIssuer.trusted) {
                // hash claim data
                const hashedClaim = web3.utils.soliditySha3(result[3], result[2], result[6], result[7]);
                try{
                    // compute signer address from signature
                    var recovered = await web3.eth.personal.ecRecover(hashedClaim, result[5]);

                    // set data and check status for claim
                    var checked=(recovered && recovered.toLowerCase() === result[3].toLowerCase());
                    claims.push({name:claim.name, value:result[7], checked:checked, msg:((!checked)?"Wrong signature":"")  });
                }
                catch(error) {
                    claims.push({name:claim.name, value:result[7], checked:false, msg:"Error while checking signature" });
                }
            }
            else {
                 if(result[0]==1) {
                    // no data or smart contract error
                    claims.push({name:claim.name, value:result[1], checked: false, msg:result[1] });
                 } else {
                    // issuer problem
                    var msg="";
                    if(!trustedIssuer) msg="Unknown issuer";
                    else if(!trustedIssuer.trusted) msg="Untrusted issuer";
                    else if(result[3]!=trustedIssuer.address) msg="Not issued by claimed issuer";
                    else msg="Unknown error"

                     // untrusted or unchecked
                     claims.push({name:claim.name, value:result[7], checked: false, msg:msg });
                 }
            }

           // console.log(claims);
        }
    ))

    console.log(claims);
    return claims;

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
        try{
            var errorObject = JSON.parse(textObject);
            return errorObject.originalError.message;
        }
        catch(error) {
            console.error(error);
            return errorMessage;
        }
    }
    else {
        return errorMessage;
    }
}


function createClaim(claimName, claimValue, subject) {

    return new Promise ( async (resolve, reject) => {
        /*console.log("claimName: "+claimName);
        console.log("claimValue: "+claimValue);
        console.log("subject: "+subject);*/

        // create claim with value
        var claim = new Object();
        claim.subject = subject;
        claim.key = claimName;
        claim.value = claimValue;
        claim.issuedAt = Math.trunc(Date.now() / 1000);

        var accounts = await ethereum.request({ method: 'eth_requestAccounts' });
        var account = accounts[0];
        //console.log("issuer: "+account);

        claim.issuer = account;

        /*
        * Claim signature :
        * 1) Concatenate issuer + subject + key + value
        * 2) Hash to sha3 (keccak256)
        * 3) compute signature
        */

        // hash serialized claim object
        const hashedClaim = web3.utils.soliditySha3(claim.issuer, claim.subject, claim.key, claim.value);
        //console.log("hashedClaim: "+hashedClaim);

        try {

            // sign hashed claim
            var signature = await web3.eth.personal.sign(hashedClaim, account);
            //console.log(signature);

            var gasAmount = await contract.methods.setClaim(subject, claimName, claimValue, signature).estimateGas({from: account});
            //console.log(gasAmount);

            var sendResult = contract.methods.setClaim(subject, claimName, claimValue, signature).send({from:account, gas:gasAmount});
            resolve(sendResult);

            sendResult.once("transactionHash", (hash) => {
                console.log("Hash: "+hash);
            })
            .once("receipt", (receipt) => {
                console.log(receipt);
            })
            .on("error", (error) => {
                console.error(error);
            });
        }
        catch(error) {
            reject(extractError(error));
        }
    });
}

/*
async function sign(message) {
    return new Promise ( async (resolve, reject) => {

        console.log("Sign: "+message);

        var accounts = await ethereum.request({ method: 'eth_requestAccounts' });
        var account = accounts[0];
        console.log("signer: "+account);

        web3.eth.personal.sign(message, account)
        .then(
            (signature) => {
            console.log("Claim signed: "+signature);
            resolve(signature);
        })
        .catch((error) => {
            console.log("Claim signature fail: "+error.message);
        });
    })

    reject();
}
*/

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

/**
* Check signature using smart contract call
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
         $('#recover').text(resultAddress);
         $('#signatureCheck').text( (resultAddress==account) );
    });
}