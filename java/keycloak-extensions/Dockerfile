ARG KEYCLOAK_VERSION=18.0.2

# See https://www.keycloak.org/server/containers
FROM quay.io/keycloak/keycloak:${KEYCLOAK_VERSION} as builder

# Install custom providers
ADD target/keyblock-extensions*.jar /opt/keycloak/providers

# quarkus needs build with custom extensions
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:${KEYCLOAK_VERSION}
COPY --from=builder /opt/keycloak/ /opt/keycloak/
WORKDIR /opt/keycloak