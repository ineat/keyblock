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

}