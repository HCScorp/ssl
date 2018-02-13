package hcs.dsl.ssl.runtime.exec;

import hcs.dsl.ssl.runtime.area.AreaInstance;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Exec implements Runnable {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final AreaInstance[] areaInstances;
    private final Config conf;
    private final String name;
    private InfluxDB influxDB;

    public Exec(String name, Config conf, AreaInstance... areaInstances) {
        this.name = name;
        this.areaInstances = areaInstances;
        this.conf = conf;
    }

    @Override
    public void run() {
        influxDB = InfluxDBFactory.connect("http://62.210.181.35:5057", "hcs", "hcs");
        String dbName = "ssl";
        influxDB.createDatabase(dbName);
        influxDB.setDatabase(dbName);

        for (AreaInstance ai : areaInstances) {
            ai.configure(name, influxDB);
        }

        if (conf.isRealtime()) {
            runRealtime();
        } else {
            runReplay();
        }
    }

    private void runRealtime() {
        influxDB.disableBatch();

        applyOffset(conf.getOffset());
        for (AreaInstance ai : areaInstances) {
            new Thread(ai).start();
        }

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            // TODO log error?
        }
    }

    private void runReplay() {
        applyOffset(conf.getStart());

        influxDB.enableBatch(1024, 200, TimeUnit.MILLISECONDS);

        LocalDateTime start = LocalDateTime.parse(conf.getStart(), DTF);
        LocalDateTime end = LocalDateTime.parse(conf.getEnd(), DTF);

        long startTs = start.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endTs = end.atZone(ZoneId.systemDefault()).toEpochSecond();

        for (AreaInstance ai : areaInstances) {
            ai.process(startTs, endTs);
        }
    }

    private void applyOffset(String offsetStr) {
        if (offsetStr != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime offDate = LocalDateTime.parse(offsetStr, DTF);
            long offset = ChronoUnit.MILLIS.between(now, offDate);
            for (AreaInstance ai : areaInstances) {
                ai.applyOffset(offset);
            }
        }
    }
}
