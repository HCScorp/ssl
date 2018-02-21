#!/usr/bin/env bash

cd $(dirname $0)

DOCKER_USER=$1
FILE=$2

if [ ! -f ./target/ssl-antlr-1.0-SNAPSHOT.jar ]; then
    mvn clean package
fi

rm -rf generated

if [ ! -f $FILE ]; then
    echo "src/main/resources/examples/$FILE.ssl"
    if [ -f "src/main/resources/examples/$FILE.ssl" ]; then
        mvn exec:java -Dexec.args="src/main/resources/examples/$FILE.ssl"
    else
        echo "file '$FILE' not found"
        exit 1
    fi
else
    mvn exec:java -Dexec.args="$FILE"
fi

for i in $(find ./generated -mindepth 1 -maxdepth 1 -type d)
do
    chmod +x $i/build.sh
    $i/build.sh $DOCKER_USER $(basename $i)
done
