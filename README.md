# Keyblock

Utilisation de Keycloak dans la gestion décentralisée des identités.

Objectif : les actions d'identification et d'authentification doivent rester entre les mains de l'utilisateur. 

Les ID providers **ne** doivent **pas** être au centre des opérations en 
- étant appelés à chaque accès à une ressource protégée
- détenant l'ensemble des informations de l'utilisateur dans un provider central à un système

Ainsi, la gestion de l'identité se retrouve décentralisée.

Pour cela, l'utilisateur gère un portefeuille de credentials. Il est alimenté par divers providers de confiance, chacun ne détenant qu'une partie de ses informations personnelles. Ils interviennent uniquement pour les opérations de création/modification/suppression de credential. 

Le reste du temps, ces credentials sont hébergés sur un registre de confiance et seul l'utilisateur a accès à ce qui le concerne.

2 concepts mis en jeu :
- **Verifiable Credentials** : l'émetteur d'un credential est connu et de confiance. Les credentials qu'il émet sont rigoureusement identifiés comme venant de lui et non altérés depuis leur création.
- **Preuve à divulgation nulle de connaissance** (ZKP) : les crédentials servent à prouver un fait (ex: l'utilisateur est admin) sans donner l'information à l'origine du fait (ex: l'utilisateur est admin parce qu'il appartient à tel ou tel groupe d'utilisateurs)

Nous allons mettre en place un exemple d'une telle organisation avec la blockchain Ethereum comme registre de confiance et Keycloak comme ID provider.

## Résumé

D'un côté, un IAM (Keycloak) connait un utilisateur sous son login/pwd habituel et sait s'il est paramétré admin ou non.

D'un autre côté, une application web qui identifie ses utilisateurs via leur identité Ethereum (adresse publique) contenue dans le wallet (Metamask) du navigateur.

Au milieu, un smart contract (ClaimHolder) de gestion des crédentials (Claims). L'application s'appuie sur ces crédentials pour savoir si un utilisateur est admin ou pas.

L'utilisateur dispose d'une interface web qui liste les claims lui appartenant et qui existent dans le smart contract. Cette interface connaît l'ensemble des claims qu'il est possible d'avoir ainsi que leurs trusted providers. 

L'utilisateur peut demander une nouvelle claim. Une requête est alors envoyée au provider, qui va effectuer une identification avec les credentials qu'il connait pour l'utilisateur puis va générer la claim correspondant et la signer avec sa clé privée Ethereum. Même scénario pour une modification ou suppression de claim.

De cette façon, l'utilisateur se constitue un portefeuille de claims. Quand il se connecte à l'application web, elle peut vérifier les claims et lui accorder les services correspondant à ses droits.

## Composants

- smart contract ClaimHolder
- application de gestion des claims
- application que l'utilisateur veut utiliser
- ID provider (Keycloak)
- API de connexion avec la blockchain pour l'ID provider

## Step 1 (initialisation)
- [X] Page web minimaliste avec web3
- [X] Connexion / identification avec Metamask
  
## Step 2 (smart contract)
- [X] Smart contract ClaimHolder (alimentation des claims manuellement pour le moment)
- [ ] Paramétrer dans l'appli les claims possibles et leurs fournisseurs de confiance
- [X] Lecture des claims dans le smart contract depuis l'interface de gestion
- [ ] L'application affiche les claims de l'utilisateur et indique celles qu'il n'a pas

## Step 3 (provider)
- [ ] API d'écriture/modification/suppression d'une claim
- [ ] IAM (keycloak)
- [ ] Identité Ethereum pour IAM (avec gestion de la clé privée)
- [ ] Identification keycloak depuis l'appli web
- [ ] Génération de la claim par keycloak
- [ ] Vérification de la signature de la claim

## Step 4 (amélioration)
- [ ] IHM plus belle pour présentation plus visuelle
- [ ] Rapprocher le smart contract des standards ERC-735 et ERC-780
- [ ] Plusieurs keycloak ou ID providers
- [ ] Vérifier la compliance zero knowledge proof et verifiable credential
- [ ] L'appli web demande l'accès à une claim, l'utilisateur l'accord ou non



## Docs

https://ethereum.stackexchange.com/questions/90680/metamask-display-for-signing-data-with-eth-signtypeddata-v4

https://eth.wiki/json-rpc/API

https://docs.metamask.io/guide/signing-data.html#sign-typed-data-v4

https://medium.com/metamask/scaling-web3-with-signtypeddata-91d6efc8b290
