module.exports = {
  networks: {
	ganache: {
	  host: "127.0.0.1",
	  port: 7545,
	  network_id: "5777"
	},
    development: {
      host: "127.0.0.1",
      port: 8545,
      network_id: "*" // Match any network id
    }
  },
  compilers: {
  	solc: {
  		version: "0.6.0"
  	}
  }
};
