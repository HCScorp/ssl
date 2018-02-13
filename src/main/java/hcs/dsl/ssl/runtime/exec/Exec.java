package hcs.dsl.ssl.runtime.exec;

import hcs.dsl.ssl.runtime.area.AreaInstance;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Exec implements Runnable {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final AreaInstance[] areaInstances;
    private final Config conf;

    public Exec(Config conf, AreaInstance... areaInstances) {
        this.areaInstances = areaInstances;
        this.conf = conf;
    }

    @Override
    public void run() {
        InfluxDB influxDB = InfluxDBFactory.connect("http://62.210.181.35:5057", "hcs", "hcs");
        String dbName = "SSL";
        influxDB.createDatabase(dbName);
        influxDB.setDatabase(dbName);

        // TODO ret policy ?
//        String rpName = "defaultRet";
//        influxDB.createRetentionPolicy(rpName, dbName, "9999w", "9999w", 1, true);
//        influxDB.setRetentionPolicy(rpName);

        for (AreaInstance ai : areaInstances) {
            ai.configure(influxDB);
        }

        if (conf.isRealtime()) {
            applyOffset(conf.getOffset());
            for (AreaInstance ai : areaInstances) {
                new Thread(ai).start();
            }
        } else {
            applyOffset(conf.getStart());

            // TODO from START to END
            for (AreaInstance ai : areaInstances) {
                ai.process(1518480932L); // TODO
            }
            // TODO NOT REALTIME
        }
    }

    private void applyOffset(String offsetStr) {
        if (offsetStr != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime offDate = LocalDateTime.parse(offsetStr, dtf);
            long offset = ChronoUnit.MILLIS.between(now, offDate);
            for (AreaInstance ai : areaInstances) {
                ai.applyOffset(offset);
            }
        }
    }
}
