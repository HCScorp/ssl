
#!/usr/bin/env bash

mvn clean formatter:format package
docker build -t $1/$2 .