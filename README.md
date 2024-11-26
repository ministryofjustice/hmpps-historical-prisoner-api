# hmpps-historical-prisoner-api

[![repo standards badge](https://img.shields.io/badge/endpoint.svg?&style=flat&logo=github&url=https%3A%2F%2Foperations-engineering-reports.cloud-platform.service.justice.gov.uk%2Fapi%2Fv1%2Fcompliant_public_repositories%2Fhmpps-historical-prisoner-api)](https://operations-engineering-reports.cloud-platform.service.justice.gov.uk/public-report/hmpps-historical-prisoner-api "Link to report")
[![CircleCI](https://circleci.com/gh/ministryofjustice/hmpps-historical-prisoner-api/tree/main.svg?style=svg)](https://circleci.com/gh/ministryofjustice/hmpps-historical-prisoner-api)
[![Docker Repository on Quay](https://img.shields.io/badge/quay.io-repository-2496ED.svg?logo=docker)](https://quay.io/repository/hmpps/hmpps-historical-prisoner-api)
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

