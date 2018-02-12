package hcs.dsl.ssl.generator;

import hcs.dsl.ssl.backend.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import hcs.dsl.ssl.backend.law.Law;
import hcs.dsl.ssl.backend.law.MarkovLaw;
import hcs.dsl.ssl.backend.law.RandomLaw;
import hcs.dsl.ssl.backend.misc.Interval;
import hcs.dsl.ssl.backend.sensor.Sensor;

import hcs.dsl.ssl.runtime.law.random.RandomLawIntervalInteger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class SSLAppGenerator implements Runnable {

    private VelocityEngine engine;

    private VelocityContext context;

    private final Model model;


    private static final List<String> TEMPLATES = Arrays.asList(
            "src/main/resources/template/sensor.vm");

    public SSLAppGenerator(Model model) {
        engine = new VelocityEngine();
        context = new VelocityContext();
        this.model = model;
    }



    public void createContext(){
        context.put("areas", model.areas);
        context.put("execs", model.execs);
        context.put("laws", model.laws);
        context.put("sensors", model.sensors);
        context.put("global", model.global);

    }

    public void writeTemplate(){
        model.sensors.forEach((key, value) -> {
            File f = new File("sensor.java");
            Template template = engine.getTemplate(TEMPLATES.get(0));
            mergeTemplate(template, f);
        });

    }


    public void mergeTemplate(Template templateSensor, File templateFile){
        try (FileWriter fw = new FileWriter(templateFile)) {
            templateSensor.merge(context, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Map<String, Law> laws = new HashMap<>();
        Map<String, Sensor> sensors = new HashMap<>();

        RandomLaw randomLaw = new RandomLaw("markov");
        randomLaw.setInterval(new Interval<>(1, 2));

        laws.put("random", randomLaw);

        Sensor sensor = new Sensor("lumi");
        sensors.put("lumi", sensor);


        Model model = new Model(laws, sensors, null, null, null);
        SSLAppGenerator sslAppGenerator = new SSLAppGenerator(model);
        sslAppGenerator.createContext();
        sslAppGenerator.writeTemplate();
    }

    @Override
    public void run() {
        // TODO
    }
}
