#!/usr/bin/env bash

cd $(dirname $0)

FILE=$1
DOCKER_USER=$2

if [ ! -f ./target/ssl-antlr-1.0-SNAPSHOT.jar ]; then
    mvn clean package
fi

if [ ! -f $FILE ]; then
    if [ ! -f "src/main/resources/$FILE.ssl" ]; then
        mvn exec:java -Dexec.args="src/main/resources/$FILE.ssl"
    fi

    echo "file '$FILE' not found"
    exit 1
fi

mvn exec:java -Dexec.args="$FILE"

# TODO build every docker images
