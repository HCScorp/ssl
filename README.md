# Sensor Simulation Language

* Author: HCS Team
* Version: 1.0

Sensor Simulation Language (SSL) is a domain specific language that allows one to define and run multiple sensor simulation.
It's goal is to feed an InfluxDB with simulated sensor's data. The execution supports two modes, replay and realtime.
The replay mode feeds the database as fast as possible whereas the realtime mode simulates sensors over time.

For complete demo with local InfluxDB and Grafana, take a look at the [demo directory](demo/).

## Quick start

Assuming the following example, located in '~/aScript.ssl' :
```
law random aLaw {
    values in interval [0.0, 1.0]
}

sensor aSensor {
    governed by aLaw
    period 1s
}

area anArea {
    has 1 aSensor
}

app anApp {
    anArea: A1
}

# no global definition, default: realtime
```

To generate Docker images that contains defined apps in the script, simply execute:

```
./generate.sh DOCKER_USER ~/aScript.ssl
```

Where DOCKER_USER will be the first part of the built image tag (of the form "DOCKER_USER/anApp").

Then run the simulation by executing (Docker need to be installed):
```
docker run DOCKER_USER/anApp
```

The default configuration pushes simulation data to local influxdb on port 8086 in the database "ssl".

For more example scripts, navigate to the [examples directory](src/main/resources/examples/)

## Old fashioned way

### Build the project

To build SSL, you need to perform a `mvn clean package` at the root of the project.

### Generate code from SSL script

To generate the simulation code, simply run this command after building SSL:

```
mvn exec:java -Dexec.args="PATH_TO_SSL_SCRIPT"
```

This will create a `generated` directory where each subfolder is an app that contains the proper simulation code, ready to be executed after a `mvn clean package`.

### Compile generated code and build Docker image

Go into the 'generated' directory, then go into the app you want to build, then execute:

```
./build.sh DOCKER_USER
```

This will compile the code and build a Docker image named "DOCKER_USER/anApp".

### Run Docker image

If you want to use the default InfluxDB server, run the following:
```
docker run DOCKER_USER/anApp
```

Otherwise, you can override default InfluxDB configuration by running the following:
```
docker run -e "SSL_INFLUXDB_ADDRESS=http://influxdb.address.com" \
           -e "SSL_INFLUXDB_USER=user" \
           -e "SSL_INFLUXDB_PWD=password" \
           -e "SSL_INFLUXDB_DB=ssl" \
           DOCKER_USER/anApp
```

## Import color scheme and partial completion in Intellij IDEA

This manipulation are for Intellij IDEA users only (tested on Linux). They will enable custom color scheme for .ssl scripts and add a partial code completion (dumb keyword completion)

To do so, first import the Intellij IDEA settings:
```
FIle -> Import Settings -> select 'intellij_settings/settings.jar' -> press OK
```

Then restart Intellij. You should now see every .ssl scripts with the right colors!

## Troubleshooting
TODO