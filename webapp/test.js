var data = 'Hello';

var address = '0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14'.toLowerCase();
var privKey  ='c9371054f1c33a550832d94798a58d9ada788c0684588af488c248fe2e650746';

function manualHash(message) {
    message = '\x19Ethereum Signed Message:\n' + message.length + message;
	return web3.utils.sha3(message);
}

// ----- HASH
console.log("----- PREFIX & HASH");

var web3HashedMessage = web3.eth.accounts.hashMessage(data);
var manualHashedMessage = manualHash(data);

console.log('web3.eth.accounts.hashMessage: '+web3HashedMessage);
console.log('manualHash: '+manualHashedMessage);
console.log('plain: '+data);

var plainAccountsSignature;
var plainPersonalSignature;
var web3AccountsSignature;
var web3PersonalSignature;
var manualAccountsSignature;
var manualPersonalSignature;


(async function ()  {


    // ----- SIGN
    console.log("----- SIGN");

    // ----- ----- plainData & web3.eth.accounts.sign
    var sigObject = web3.eth.accounts.sign(data, privKey);
    plainAccountsSignature = sigObject.signature;
    console.log("plainData, web3.eth.accounts.sign: "+plainAccountsSignature);

    // ----- ----- plainData & web3.eth.personam.sign
    plainPersonalSignature = await web3.eth.personal.sign(data, address);
    console.log("plainData, web3.eth.personal.sign: "+plainPersonalSignature);

    // ----- ----- web3HashedMessage & web3.eth.accounts.sign
    var sigObject = web3.eth.accounts.sign(web3HashedMessage, privKey);
    web3AccountsSignature = sigObject.signature;
    console.log("web3HashedMessage, web3.eth.accounts.sign: "+web3PersonalSignature);

    // ----- ----- web3HashedMessage & web3.eth.personam.sign
    web3PersonalSignature = await web3.eth.personal.sign(web3HashedMessage, address);
    console.log("web3HashedMessage, web3.eth.personal.sign: "+web3AccountsSignature);

    // ----- ----- manualHashedMessage & web3.eth.accounts.sign
    var sigObject2 = web3.eth.accounts.sign(manualHashedMessage,privKey);
    manualAccountsSignature = sigObject2.signature
    console.log("manualHashedMessage, web3.eth.accounts.sign: "+manualAccountsSignature);

    // ----- ----- manualHashedMessage & web3.eth.personal.sign
    manualPersonalSignature = await web3.eth.personal.sign(manualHashedMessage,address);
    console.log("manualHashedMessage, web3.eth.personal.sign: "+manualPersonalSignature);


    // ----- RECOVER

    var plainAccountsRecoverAccounts;
    var plainPersonalRecoverAccounts;
    var web3AccountsRecoverAccounts;
    var web3PersonalRecoverAccounts;
    var manualAccountsRecoverAccounts;
    var manualPersonalRecoverAccounts;

    var plainAccountsRecoverPersonal;
    var plainPersonalRecoverPersonal;
    var web3AccountsRecoverPersonal;
    var web3PersonalRecoverPersonal;
    var manualAccountsRecoverPersonal;
    var manualPersonalRecoverPersonal;

    // ----- ----- web3.eth.accounts.recover
    plainAccountsRecoverAccounts = web3.eth.accounts.recover(data, plainAccountsSignature, false);
    console.log(plainAccountsRecoverAccounts+" : "+(plainAccountsRecoverAccounts.toLowerCase()==address));

    plainPersonalRecoverAccounts = web3.eth.accounts.recover(data, plainPersonalSignature, false);
    console.log(plainPersonalRecoverAccounts+" : "+(plainPersonalRecoverAccounts.toLowerCase()==address));

    web3AccountsRecoverAccounts = web3.eth.accounts.recover(web3HashedMessage, web3AccountsSignature, false);
    console.log(web3AccountsRecoverAccounts+" : "+(web3AccountsRecoverAccounts.toLowerCase()==address));

    web3PersonalRecoverAccounts = web3.eth.accounts.recover(web3HashedMessage, web3PersonalSignature, false);
    console.log(web3PersonalRecoverAccounts+" : "+(web3PersonalRecoverAccounts.toLowerCase()==address));

    manualAccountsRecoverAccounts = web3.eth.accounts.recover(manualHashedMessage, manualAccountsSignature, false);
    console.log(manualAccountsRecoverAccounts+" : "+(manualAccountsRecoverAccounts.toLowerCase()==address));

    manualPersonalRecoverAccounts = web3.eth.accounts.recover(manualHashedMessage, manualPersonalSignature, false);
    console.log(manualPersonalRecoverAccounts+" : "+(manualPersonalRecoverAccounts.toLowerCase()==address));

    // ----- ----- eb3.eth.personal.ecRecover
    plainAccountsRecoverPersonal = await web3.eth.personal.ecRecover(data, plainAccountsSignature);
    console.log(plainAccountsRecoverPersonal+" : "+(plainAccountsRecoverPersonal.toLowerCase()==address));

    plainPersonalRecoverPersonal = await web3.eth.personal.ecRecover(data, plainPersonalSignature);
    console.log(plainPersonalRecoverPersonal+" : "+(plainPersonalRecoverPersonal.toLowerCase()==address));

    web3AccountsRecoverPersonal = await web3.eth.personal.ecRecover(web3HashedMessage, web3AccountsSignature);
    console.log(web3AccountsRecoverPersonal+" : "+(web3AccountsRecoverPersonal.toLowerCase()==address));

    web3PersonalRecoverPersonal = await web3.eth.personal.ecRecover(web3HashedMessage, web3PersonalSignature);
    console.log(web3PersonalRecoverPersonal+" : "+(web3PersonalRecoverPersonal.toLowerCase()==address));

    manualAccountsRecoverPersonal = await web3.eth.personal.ecRecover(manualHashedMessage, manualAccountsSignature);
    console.log(manualAccountsRecoverPersonal+" : "+(manualAccountsRecoverPersonal.toLowerCase()==address));

    manualPersonalRecoverPersonal = await web3.eth.personal.ecRecover(manualHashedMessage, manualPersonalSignature);
    console.log(manualPersonalRecoverPersonal+" : "+(manualPersonalRecoverPersonal.toLowerCase()==address));


    // ----- ----- should fail
    plainAccountsRecoverPersonal2 = await web3.eth.personal.ecRecover(manualHashedMessage, plainAccountsSignature);
    console.log(plainAccountsRecoverPersonal2+" : "+(plainAccountsRecoverPersonal2.toLowerCase()==address));

})();
