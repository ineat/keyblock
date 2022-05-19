help:
	echo "Usage: make [target]"

start: build-blockchain-connector build-extensions docker-compose-run

build-blockchain-connector:
	cd java/blockchain-connector; mvn clean install

build-extensions:
	cd java/keycloak-extensions; \
	mvn clean package

docker-compose-run:
	cd java/keycloak-extensions; \
	echo "Starting Keycloak witk Keyblock extensions..."; \
	docker compose up --force-recreate --build  --remove-orphans; \
