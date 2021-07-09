
const app = Vue.createApp({
  data() {
        return {
            web3Version: '',
            nodeInfo: '',
            blockNumber: '',
            userIdentity: undefined,
            userAuthent:'',
            signature:'',
            recover:'',
            signatureCheck:'',
            contractAddress:contractData.address,
            claims: [],
            targetUser: "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14",
            setClaimMessage:'',
            spinner:''

        }
  },
  async mounted() {
        initWeb3();
        this.web3Version = web3.version;
        this.nodeInfo = await web3.eth.getNodeInfo();
        this.blockNumber = await web3.eth.getBlockNumber();

        loadContract();
  },
  methods: {

    clearAuthent() {
            this.userAuthent='';
            this.signature='';
            this.recover='';
            this.signatureCheck='';
    },
    identification() {

        ethereum.request({ method: 'eth_requestAccounts' })
        .then( (account) => {
            console.log("Ethereum enabled with account : "+account);
            this.userIdentity=account[0];
         })
        .catch( (error) => {console.error(error);} )

    },
    authent(authentId) {
        this.clearAuthent();
        authentication(authentId).then( (result) => {
            this.userAuthent = result.userAuthent;
            this.signature = result.signature;
            this.recover = result.recover;
            this.signatureCheck = result.signatureCheck;
        });
    },
    displayClaims() {
        checkClaims().then( (result) => {
         //   console.log("get claims: "+result);
            this.claims = result;
        });
    },
    submitClaim(claimName, claimValue) {
       // console.log("submitClaim: "+claimName+" = "+claimValue);
        this.spinner = '<div class="spinner-border" role="status"></div>';
        var createClaimPromise = createClaim(claimName, claimValue, "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14");

        console.log("createClaimPromise");
        console.log(createClaimPromise);

        createClaimPromise.then( (receipt) => {
            this.spinner = '';
            this.setClaimMessage = "Tx created in bloc "+receipt.blockNumber;
        })
        .catch( (error) => {
            this.spinner = '';
            this.setClaimMessage = error;
        });


    }
  }
});

app.component("claim", {
    props:["name", "value", "checked"],
    template:`
        <p>
            <b>{{name}}: </b>{{value}}
            <i class="bi bi-check-circle" v-if="checked" style="color:green" title="Issuer signature checked"></i>
            <i class="bi bi-x-circle" v-if="! checked && value!='Subject or key not found'" style="color:red" title="Signature check failed"></i>
            <i class="bi bi-question-circle" v-if="! checked && value=='Subject or key not found' " style="color:blue" title="Issuer signature NOT checked"></i>
        </p>
    `
});

app.component("claim-form", {
    props:["name", "address"],
    emits:["submit-claim-event"],
    data(){
        return {
            claimValue:'',
            claimName: this.name
        }
    },
    methods:{
        submitClaim() {
            this.$emit('submit-claim-event', this.claimName, this.claimValue);
        }
    },
    template:`
        <p>
            <form>
              <div class="form-group row">
                <label v-bind:for="name" class="col-sm-4 col-form-label">{{name}}</label>
                <div class="col-sm-6">
                  <input type="text" class="form-control" v-bind:id="name" v-model="claimValue">
                  <input type="hidden" v-model="claimName">
                </div>
                <button type="button" class="btn btn-primary mb-2" @click="submitClaim">OK</button>
              </div>
            </form>
        </p>
    `
});

const vm = app.mount('#app');
