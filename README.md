# hmpps-historical-prisoner-api

[![Ministry of Justice Repository Compliance Badge](https://github-community.service.justice.gov.uk/repository-standards/api/hmpps-historical-prisoner-api/badge?style=flat)](https://github-community.service.justice.gov.uk/repository-standards/hmpps-historical-prisoner-api)
[![Docker Repository on ghcr](https://img.shields.io/badge/ghcr.io-repository-2496ED.svg?logo=docker)](https://ghcr.io/ministryofjustice/hmpps-historical-prisoner-api)
[![API docs](https://img.shields.io/badge/API_docs_-view-85EA2D.svg?logo=swagger)](https://historical-prisoner-api-dev.prison.service.justice.gov.uk/swagger-ui/index.html?configUrl=/v3/api-docs)

Backend for historical prisoner application.

## Running the application locally

The application comes with a `dev` spring profile that includes default settings for running locally. This is not
necessary when deploying to kubernetes as these values are included in the helm configuration templates -
e.g. `values-dev.yaml`.

There is also a `docker-compose.yml` that can be used to run a local instance of the template in docker and also an
instance of HMPPS Auth (required if your service calls out to other services using a token).

```bash
docker compose pull && docker compose up
```

will build the application and run it and HMPPS Auth within a local docker instance.

### Running the application in Intellij

```bash
docker compose pull && docker compose up --scale hmpps-historical-prisoner-api=0
```

will just start a docker instance of HMPPS Auth. The application should then be started with a `dev` active profile
in Intellij.

