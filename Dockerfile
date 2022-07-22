FROM  quay.io/keycloak/keycloak:legacy

ADD keycloak-extensions/target/keyblock-extensions*.jar /opt/jboss/keycloak/standalone/deployments