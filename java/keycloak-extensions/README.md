# Keyblock extensions

## Prerequisites

- A Keycloak (tested on v15) installation
- An Ethereum account on a test network
- A metamask browser plugin installed (if not, Keycloak will show you an error with a download link ;))

## How to build and deploy

Run `mvn clean package` and copy the generated jar into your Keycloak `deployment` directory

## Keyblock theme

- Provide an overridden registration page with a new form field :a `ethereum address id` field
- **For now, this theme also ship some web resources needed by the authenticator**

### Setup

- After the jar deployment, you should be able to set the keyblock theme in the Administration console
- Activate the `User Registration`

### Usage

- Go to the Keycloak login page and switch to the Registration page: 
![](docs/registration.png)

## Keycloak Wallet Authenticator

You must configure a forked `Browser Flow` in order to add the `Wallet Blockchain Authenticator` execution

- this execution can be unique in the flow to provide a single authentication way
- you can add this execution after the `Browser Forms` Flow to do 2FA with the `Wallet Blockchain Authenticator` 