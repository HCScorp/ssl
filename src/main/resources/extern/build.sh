#!/usr/bin/env bash

cd $(dirname $0)

LC=${2,,}
NAME=`echo ${LC//_/-}`
mvn clean formatter:format package
docker build -t $1/$NAME .