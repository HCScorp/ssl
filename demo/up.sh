#!/usr/bin/env bash

cd $(dirname $0)

docker-compose up -d

printf "Waiting for InfluxDB to be up..."
until $(curl --output /dev/null --silent --head --fail http://localhost:8086/ping); do
    printf '.'
    sleep 1
done
printf "done\n"

printf "Waiting for Grafana to be up..."
until $(curl --output /dev/null --silent --head --fail http://localhost:3000); do
    printf '.'
    sleep 1
done
printf "done\n"
