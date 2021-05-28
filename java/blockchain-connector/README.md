# Blockchain connector

Client Java pour accéder au smart contract ClaimsRegistry sur Ethereum

## Smart contracts

- `ClaimsRegistry` : smart contract de gestion des claims.
- `SimpleClaimsRegistry` : version simplifiée de `ClaimsRegistry` sans gestion des signatures.

## Fichiers

- `resources/` : ABI et code compilé des smart contract, générés avec web3j depuis les fichiers .sol du projet racine
- `resources/properties` : différents jeux de properties définissant des contextes de connexions différents (ethereum provider, adresse du smart contract ...)

## Compte Ethereum pour l'IAM

Attention, comptes de test sur Ropsten uniquement :

**Seed :**

`suit blade rigid hat glue broccoli music blind scorpion column yard now`

**Compte 1 :**
- Adresse : 0x41f6B225846863E3C037e92F229cD40f5d575258
- Clé privée : 85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723
- https://ropsten.etherscan.io/address/0x41f6b225846863e3c037e92f229cd40f5d575258

**Compte 2 :**
- Adresse : 0x5Db6617D5A8BB274379cD815D765722aF5088F8a
- Clé privée : 6484e4896a53883b15451347df3bd63a8e9b935310e194cd162fa64159086b07
- https://ropsten.etherscan.io/address/0x5Db6617D5A8BB274379cD815D765722aF5088F8a

## Utilisation

- importer le jar `blockchain-connector-1.0-SNAPSHOT.jar`

#### Connexion

```
ClaimsRegistryConnector registry = new ClaimsRegistryConnector(new Context(Context.ContextFlavor.SIMPLECLAIMREGISTRY_INFURA_ROPSTEN));
```

#### Contextes disponibles

(Définis dans les fichiers properties, exploités par `Context.ContextFlavor`)

- `SIMPLECLAIMREGISTRY_INFURA_ROPSTEN` Contrat SimpleClaimRegistry sur Ropsten via Infura.
- `SIMPLECLAIMREGISTRY_GANACHE` Contrat SimpleClaimRegistry en local avec Ganache.
- `CLAIMREGISTRY_INFURA_ROPSTEN` Contrat ClaimRegistry sur Ropsten via Infura.
- `SIMPLECLAIMREGISTRY_GANACHE` Contrat ClaimRegistry en local avec Ganache.


#### Lecture
```
String subjectAddress = "0x...";
String claimId = "isadmin";

Claim claim = registry.getClaim(subjectAddress, claimId);
```

#### Ecriture synchrone

```
TransactionReceipt txReceipt  = registry.setClaimSync(subjectAddress, claimId, "true");
```
#### Ecriture asynchrone avec wait bloquant optionnel

```
String txHash = registry.setClaimAsync(subjectAddress, claimId, "true");
TransactionReceipt txReceipt = registry.waitForReceipt(txHash);
```

#### Ecriture asynchrone avec listener

```

```