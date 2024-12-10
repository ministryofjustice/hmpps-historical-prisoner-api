FROM --platform=$BUILDPLATFORM eclipse-temurin:21-jdk-jammy AS builder

ARG BUILD_NUMBER
ENV BUILD_NUMBER=${BUILD_NUMBER:-1_0_0}

WORKDIR /app
ADD . .

ADD --chown=appuser:appgroup https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem ./global-bundle.pem
COPY ./split-pem.bash split-pem.bash
RUN ./split-pem.bash global-bundle.pem aws-certs.jks

RUN ./gradlew --no-daemon assemble

FROM eclipse-temurin:21-jre-jammy
LABEL maintainer="HMPPS Digital Studio <info@digital.justice.gov.uk>"

ARG BUILD_NUMBER
ENV BUILD_NUMBER=${BUILD_NUMBER:-1_0_0}

RUN apt-get update && \
    apt-get -y upgrade && \
    rm -rf /var/lib/apt/lists/*

ENV TZ=Europe/London
RUN ln -snf "/usr/share/zoneinfo/$TZ" /etc/localtime && echo "$TZ" > /etc/timezone

RUN addgroup --gid 2000 --system appgroup && \
    adduser --uid 2000 --system appuser --gid 2000

WORKDIR /app
COPY --from=builder --chown=appuser:appgroup /app/build/libs/hmpps-historical-prisoner-api*.jar /app/app.jar
COPY --from=builder --chown=appuser:appgroup /app/build/libs/applicationinsights-agent*.jar /app/agent.jar
COPY --from=builder --chown=appuser:appgroup /app/applicationinsights.json /app
COPY --from=builder --chown=appuser:appgroup /app/applicationinsights.dev.json /app

# Add AWS RDS Root key store to image
COPY --from=builder --chown=appuser:appgroup /app/aws-certs.jks /app

USER 2000

ENTRYPOINT ["java", "-XX:+AlwaysActAsServerClassMachine", "-javaagent:/app/agent.jar", "-jar", "/app/app.jar"]
