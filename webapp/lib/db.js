// Fake database for Keyblock web app

const db = {

    issuers: [
        {id:1, name:"Keycloak", address:"0x8433e11FD3c2dc5F9E44558be8FccCe8dB0fCD1E", trusted:true}
        , {id:2, name:"BadKeycloak", address:"0x4373Da4760A550F8EBFbaE006A6F82ad2B2c25B1", trusted:false}
    ],
    claims:[
        {name:"isadmin", issuer:1}
        , {name:"issomethingelse", issuer:1}
        , {name:"anotherone", issuer:1}
        , {name:"wrongIssuerClaim", issuer:1} // to be tested emitted from another issuer than 1
        , {name:"unknownIssuerClaim", issuer:99} // unknown issuer
        , {name:"untrustedIssuerClaim", issuer:2} // untrusted issuer
    ]
};