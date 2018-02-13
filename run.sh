#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true
mvn exec:java -Dexec.args="src/main/resources/$1.ssl"

for i in $(find ./generated -mindepth 1 -maxdepth 1 -type d)
do
chmod +x $i/build.sh
$i/build.sh $1 $(basename $i)
done