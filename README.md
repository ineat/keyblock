# Keyblock

## Résumé

L'utilisateur affiche une page web dans son navigateur. Elle interroge Metamask pour obtenir son identité Ethereum.

Puis elle interroge un smart contract ClaimHolder pour récupérer certains credentials pour lui donner ou non certains accès.

Premier exemple :

L'utilisateur connecté est-il admin ?

Keycloak connait l'utilisateur sous son login/pwd habituel et sait s'il st admin ou non.

L'utilisateur se connecte avec Metamask. L'appli cherche la claim dans le smart contract. S'il n'y en n'a pas, il faut interroger keycloak. Une fenêtre s'ouvre ou s'affiche, gérée par keycloak. L'utilisateur saisi son login/pwd. Keycloak l'identifie, a récupéré son adresse Ethereum depuis l'appli web et peut générer une claim.

L'appli connait l'adresse publique du provider (keyvloak). Elle peut donc vérifier la validité de la claim en vérifiant la signature.

## Todo

- [X] Page web minimaliste
- [X] Connexion à Metamask
- [X] Smart contract ClaimHolder
- [X] Lecture d'une claim dans le smart contract depuis la page web
- [ ] API d'écriture/modification/suppression d'une claim
- [ ] IAM (keycloak) 
- [ ] Identification keycloak depuis l'appli web
- [ ] Génération de la claim par keycloak
- [ ] Vérification de la signature de la claim



## Docs

https://ethereum.stackexchange.com/questions/90680/metamask-display-for-signing-data-with-eth-signtypeddata-v4

https://eth.wiki/json-rpc/API

https://docs.metamask.io/guide/signing-data.html#sign-typed-data-v4

https://medium.com/metamask/scaling-web3-with-signtypeddata-91d6efc8b290
