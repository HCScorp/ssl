#!/usr/bin/env bash

cd $(dirname $0)

docker run --network=host ssl/simulight
