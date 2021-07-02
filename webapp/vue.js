var simpleClaimsRegistry = {
    address: "0xad9388311e96031d9cF2D1370826D8940d057362"
    ,abi: [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"uint256","name":"removedAt","type":"uint256"}],"name":"ClaimRemoved","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"string","name":"value","type":"string"},{"indexed":false,"internalType":"uint256","name":"updatedAt","type":"uint256"}],"name":"ClaimSet","type":"event"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"getClaim","outputs":[{"internalType":"uint256","name":"","type":"uint256"},{"internalType":"string","name":"","type":"string"},{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"","type":"address"},{"internalType":"string","name":"","type":"string"}],"name":"registry","outputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"address","name":"issuer","type":"address"},{"internalType":"uint256","name":"issuedAt","type":"uint256"},{"internalType":"bytes","name":"issuerSignature","type":"bytes"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"issuer","type":"address"},{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"removeClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setSelfClaim","outputs":[],"stateMutability":"nonpayable","type":"function"}]
};

var claimsRegistry = {
    address: "0x864cf6e7547C37B06c9C49fa4B3D681B273E8E0b"
    , abi: [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"uint256","name":"removedAt","type":"uint256"}],"name":"ClaimRemoved","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"issuer","type":"address"},{"indexed":true,"internalType":"address","name":"subject","type":"address"},{"indexed":true,"internalType":"string","name":"key","type":"string"},{"indexed":false,"internalType":"string","name":"value","type":"string"},{"indexed":false,"internalType":"uint256","name":"updatedAt","type":"uint256"}],"name":"ClaimSet","type":"event"},{"inputs":[{"internalType":"bytes32","name":"msgHash","type":"bytes32"},{"internalType":"uint8","name":"v","type":"uint8"},{"internalType":"bytes32","name":"r","type":"bytes32"},{"internalType":"bytes32","name":"s","type":"bytes32"}],"name":"checkSignature","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"pure","type":"function"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"getClaim","outputs":[{"internalType":"uint256","name":"","type":"uint256"},{"internalType":"string","name":"","type":"string"},{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"","type":"address"},{"internalType":"string","name":"","type":"string"}],"name":"registry","outputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"address","name":"issuer","type":"address"},{"internalType":"uint256","name":"issuedAt","type":"uint256"},{"internalType":"bytes","name":"issuerSignature","type":"bytes"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"issuer","type":"address"},{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"}],"name":"removeClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"subject","type":"address"},{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setClaim","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"string","name":"key","type":"string"},{"internalType":"string","name":"value","type":"string"},{"internalType":"bytes","name":"signature","type":"bytes"}],"name":"setSelfClaim","outputs":[],"stateMutability":"nonpayable","type":"function"}]
}

var contractData = simpleClaimsRegistry;

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

   // console.log("web3 : "+web3.version);
}

/**
* Init the contract objects
*/
async function loadContract() {
    if(contractData.address) {
       // console.log("Load contract : "+JSON.stringify(contractData));
        try {
            contract =  new web3.eth.Contract(contractData.abi, contractData.address);
        }
        catch(error) {
            console.error("Error loading contract : "+error);
        }
    }
}

const app = Vue.createApp({
  data() {
        return {
            web3Version: "v",
            nodeInfo: "n",
            blockNumber: "b",
            userIdentity: "not identified",
            userAuthent:'',
            signature:'',
            recover:'',
            signatureCheck:'',
            contractAddress:contractData.address,
            claims: []
        }
  },
  async mounted() {
        initWeb3();
        this.web3Version=web3.version;
        this.nodeInfo=await web3.eth.getNodeInfo();
        this.blockNumber=await web3.eth.getBlockNumber();

        loadContract();
  },
  methods: {

    identification() {

        ethereum.request({ method: 'eth_requestAccounts' })
        .then( (account) => {
            console.log("Ethereum enabled with account : "+account);
            this.userIdentity=account[0];
         })
        .catch( (error) => {console.error(error);} )

    },
    authent(authentId) {

        authentication(authentId).then( (result) => {
            this.userAuthent = result.userAuthent;
            this.signature = result.signature;
            this.recover = result.recover;
            this.signatureCheck = result.signatureCheck;
        });
    },
    displayClaims() {
        checkClaims().then( (result) => {
            console.log("get claims: "+result);
            this.claims = result;
        });
    },
    callCreateClaim() {
        createClaim(db.claims[0], "true", "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14")
    }
  }
});

const vm = app.mount('#app');

app.component("claim", {
    props:["name", "value"],
    template:`
        <p><b>{{name}}: </b>{{value}}</p>
    `
});

