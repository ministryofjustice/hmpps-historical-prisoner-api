#!/bin/bash
PEM_FILE=$1
PASSWORD=changeit
KEYSTORE=$2

CERTS=$(grep -c 'END CERTIFICATE' "$PEM_FILE")

for N in $(seq 0 $((CERTS - 1))); do
  ALIAS="${PEM_FILE%.*}-$N"
  awk "n==$N { print }; /END CERTIFICATE/ { n++ }" "$PEM_FILE" |
    keytool -noprompt -import -trustcacerts \
            -alias "$ALIAS" -keystore "$KEYSTORE" -storepass "$PASSWORD"
done
