help:
	echo "Usage: make [target]"

start: build run

build: build-keyblock

build-keyblock:
	mvn clean package

run:
	cd java/keycloak-extensions; \
	echo "Starting Keycloak witk Keyblock extensions..."; \
	docker compose up --force-recreate --build  --remove-orphans; \
