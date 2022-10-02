FROM quay.io/keycloak/keycloak:19.0.2

ARG KEYCLOAK_DIST="keycloak-server/target/keycloak-19.0.2"

HEALTHCHECK CMD curl -f http://localhost:8080$KC_HTTP_RELATIVE_PATH/health/live || exit 1;