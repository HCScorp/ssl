#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true
mvn exec:java -Dexec.args="src/main/resources/$1.ssl"

