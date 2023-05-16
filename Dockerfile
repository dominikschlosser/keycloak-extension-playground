FROM registry.access.redhat.com/ubi8-minimal AS build-env

ENV KEYCLOAK_VERSION 19.0.2
ARG KEYCLOAK_DIST="keycloak-server/target/keycloak-$KEYCLOAK_VERSION"

RUN microdnf install -y tar gzip

ADD $KEYCLOAK_DIST /tmp/keycloak/

RUN mv /tmp/keycloak /opt/keycloak && mkdir -p /opt/keycloak/data

RUN chmod -R g+rwX /opt/keycloak

FROM registry.access.redhat.com/ubi8-minimal
ENV LANG en_US.UTF-8

COPY --from=build-env --chown=1000:0 /opt/keycloak /opt/keycloak

RUN microdnf update -y && \
    microdnf install -y --nodocs java-17-openjdk-headless glibc-langpack-en && microdnf clean all && rm -rf /var/cache/yum/* && \
    echo "keycloak:x:0:root" >> /etc/group && \
    echo "keycloak:x:1000:0:keycloak user:/opt/keycloak:/sbin/nologin" >> /etc/passwd

USER 1000

EXPOSE 8080
EXPOSE 8443

ENTRYPOINT [ "/opt/keycloak/bin/kc.sh" ]

HEALTHCHECK CMD curl -f http://localhost:8080$KC_HTTP_RELATIVE_PATH/health/live || exit 1;