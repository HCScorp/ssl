package hcs.dsl.ssl.runtime.engine;


import hcs.dsl.ssl.runtime.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Engine implements Runnable {

//    private List<>

    @Override
    public void run() {
        // dans l'exec on définit potentiellement un offset a appliquer sur toutes les données
        // puis un ensemble d'area instancié par leur nom

        // TODO save le time de lancement de l'exec
        // TODO puis calculer l'offset des sensors : cad : foreach sensor, give the start time for the offset to be calculated
        // TODo by the (s)

        // TODO offset for each sensor = startTime - desiredOffset
        // 10

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        List<Sensor> sensors = new ArrayList<>();
        for (Sensor s : sensors) {
            // TODO Configure sensor output stream
            executor.scheduleAtFixedRate(s, 0, s.getPeriod(), TimeUnit.SECONDS);
        }
//
//        Exec e = new Exec();
//        e.setOffset(new Offset(LocalDateTime.now()))
    }

}
