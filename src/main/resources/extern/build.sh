#!/usr/bin/env bash

cd $(dirname $0)

mvn clean formatter:format package
if [ $? -eq 0 ]; then
    echo "Application build succeed!"

    docker build -t $1/$(echo -n "$2" | tr '[:upper:]' '[:lower:]') .
    if [ $? -eq 0 ]; then
        echo "Docker image build succeed!"
    else
        echo "Docker image build failed /!\\"
    fi
else
    echo "Application build failed, skipping Docker image build /!\\"
fi

