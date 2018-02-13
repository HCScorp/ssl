#!/usr/bin/env bash

cd $(dirname $0)

mvn clean formatter:format package
docker build -t $1/$2 .