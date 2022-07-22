help:
	echo "Usage: make [target]"

start: build-blockchain-connector build-extensions run

build: build-blockchain-connector build-extensions

build-blockchain-connector:
	cd java/blockchain-connector; mvn clean install

build-extensions:
	cd java/keycloak-extensions; \
	mvn clean package

run:
	cd java/keycloak-extensions; \
	echo "Starting Keycloak witk Keyblock extensions..."; \
	docker compose up --force-recreate --build  --remove-orphans; \
