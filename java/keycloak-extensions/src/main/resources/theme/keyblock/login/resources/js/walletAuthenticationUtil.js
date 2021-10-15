document.addEventListener('DOMContentLoaded', () => {
	initWeb3Metamask();
	identification();
})

function displayErrorDivsWithMessages(show) {
	show === undefined ? show = true : show
	document.getElementById("walletError").innerHTML = 'Non-Ethereum browser detected. You should consider trying MetaMask!';
	document.getElementById("pluginOkDivs").hidden = show;
	document.getElementById("pluginKoDivs").hidden = !show;
}

function initWeb3Metamask() {
	// Modern dapp browsers...
	if (window.ethereum) {

		window.web3 = new Web3(ethereum);
		displayErrorDivsWithMessages(false)
		// refresh page on account or network change
		ethereum.on("accountsChanged", (accounts) => {
			window.location.reload(true);
		});
		ethereum.on("chainChanged", (accounts) => {
			window.location.reload(true);
		});

	}
	// Legacy dapp browsers...
	else if (window.web3) {
		window.web3 = new Web3(web3.currentProvider);
	}
	// Non-dapp browsers...
	else {
		console.log('Non-Ethereum browser detected. You should consider trying MetaMask!');
		displayErrorDivsWithMessages();
	}
}

function identification() {
	ethereum = window.ethereum;
	if (ethereum) {
		ethereum.request({method: 'eth_requestAccounts'})
			.then((account) => {
				console.log("Ethereum enabled with account : " + account);
				document.getElementById("blockchain-address").value = account[0];
				document.getElementById("address-label").innerHTML = account[0];
			})
			.catch((error) => {
				console.error(error);
			})
	} else {
		displayErrorDivsWithMessages();
	}
}

async function authentication() {
	var accounts = await ethereum.request({method: 'eth_requestAccounts'});
	var account = accounts[0];
	askForSignatureEthPersonal(account, "Veuillez signer pour vous authentifier");
}

function askForSignatureEthPersonal(account, message) {

	web3.eth.personal.sign(message, account)
		.then(
			(signature) => {
				doAuthentication(signature, message);
			})
		.catch((error) => {
			console.log("Sign fail: " + error.message);
		});
}


function doAuthentication(signature, message) {
	document.getElementById("blockchain-sig").value = signature;
	document.getElementById("blockchain-data").value = message;
	document.getElementById("kc-wallet-form").submit();
}