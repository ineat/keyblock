# Keyblock Proof of Concept

The idea behind Keyblock is to married Keycloak product with the Blockchain world.

For further considerations, take a look at [our ideations document](docs/README.md)

## Keyblock extensions

We provide some Keycloak extensions to play with Keycloak and the blockchain world.

See the [related README to get started](java/keycloak-extensions/README.md)

## Getting started

### Prerequisites

- Maven
- Java 11
- Docker
- A Metamask wallet (browser extension) installed 
- A supported blockchain configured into the Wallet
  - [Avalanche](/docs/avalanche.md)
  - [Polygon](/docs/polygon.md)
  - [ropsten](/docs/ropsten.md)

### Start the Keyblock middleware

To start the keyblock middleware, just run `make start` (that build the jars and run keycloak in a container mode). Your local instance is accessible on `http://localhost:8580`

> you can individually build and run each project thanks to the make targets. For details, see the make file content.


### How to play with the POC

- Check the [blockchain-connect README](java/blockchain-connector/README.md) to discover Ropsten accounts details to use
- Connect to the [Keycloak admin console](`http://localhost:8580`) with `admin` / `password` credentials
- Go to the `Keyblock` realm
- Check the pre-configured flows in the `Authentication` tab
  - `Keyblock Authentication`: A simple authentication flow with the Wallet authentication. 
  - `Keyblock SSO`: An SSO flow that check if a session exist in the Keyblock SmartContract
    - go to the `Config` on each execution to set the correct Ethereum addresses (the default values should work) 
- Use the [Account console](http://localhost:8580/auth/realms/Keyblock/account/) to test the authentication
  - A user with your Ethereum address must exist in Keycloak. To easily create one, click the `Register` link on the Metamask login page
    - fill your ethereum address in the dedicated field (this should be automagic if your metamask extension is active)
- To test the SSO flow, switch the default authentication flow to `Keyblock SSO`
  - after you log in; an ethereum transaction is sent to the SmartContract; you can show his status by pickup the `tx id` in the keycloak log and search it in the [https://ropsten.etherscan.io/](https://ropsten.etherscan.io/) website
    - try to login again in a new browser windows, you should be automatically authenticated if your ethereum transaction is accepted (this can take several seconds depending on the network saturation)
  - if you log out, the keycloak event listener send logout data to the SmartContract (your next connection will require a Metamask authentication again


