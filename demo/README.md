# Sensor Simulation Language : Demo

## Setup local environment

In the [demo](.) directory, start InfluxDB and Grafana by running:
```
./up.sh
```

Then access Grafana at the address [http://localhost:8086](http://localhost:8086) and verify that there is no data displayed (dashboard `SSL demo`).

## Generate apps and Docker images

We are now going to generate simulation code and build docker image for `demo.ssl` script by executing the following:
```
./generate.sh
```
If you want to, you can edit the script but beware that any update on names or elements addition will no be reflected on the pre-configured Grafana, you will have to do it yourself.

Alternatively, you can run the `generate-realtime.sh` script that will do the same as above for the `demo_realtime.ssl` script, to demonstrate the realtime extension. A dashboard (named `SSL demo realtime`) is already configured for this simulation.

## Run the simulation
You can now run the simulation (in the demo, only the 'simulight' will be executed, so its name must not change nor be deleted) by executing:
```
./run.sh
```

This will populate the local InfluxDB. When the simulation is done, you can visualize generated data on the demo graph available on the local Grafana.

## Voila!

After contemplating for long hours the beautiful graphs on Grafana, you can shut down the demo environment by executing the script:
```
./down.sh
```