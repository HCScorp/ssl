package hcs.dsl.ssl.generator;

import hcs.dsl.ssl.backend.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import hcs.dsl.ssl.backend.area.Area;
import hcs.dsl.ssl.backend.area.SensorGroup;
import hcs.dsl.ssl.backend.exec.AreaGroup;
import hcs.dsl.ssl.backend.exec.Exec;
import hcs.dsl.ssl.backend.global.Global;
import hcs.dsl.ssl.backend.law.*;
import hcs.dsl.ssl.backend.misc.Interval;
import hcs.dsl.ssl.backend.misc.ListWrapper;
import hcs.dsl.ssl.backend.misc.Var;
import hcs.dsl.ssl.backend.sensor.Sensor;

import hcs.dsl.ssl.backend.sensor.SourceLaw;
import hcs.dsl.ssl.runtime.law.random.RandomLawIntervalInteger;
import okhttp3.internal.Internal;
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

        context.put("RandomLaw", RandomLaw.class);
        context.put("MarkovLaw", MarkovLaw.class);
        context.put("FunctionLaw", FunctionLaw.class);
        context.put("SourceLaw", SourceLaw.class);
    }

    public void createExecs(){
        model.execs.forEach((key, value) -> {

        });
    }

    public void createTree(File newExec){

        newExec.getParentFile().mkdirs();

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
        Map<String, Area> areas = new HashMap<>();
        Map<String, Exec> execs = new HashMap<>();


        // MARKOV
        MarkovLaw markovLaw = new MarkovLaw("MarkovString");
        List<Edge> edges = Arrays.asList(
                new Edge<>("lol", 0.1, "lolilol"),
                new Edge<>("lolilol", 0.2, "lol"));
        markovLaw.setList(edges);
        markovLaw.setValType(Var.Type.String);

        MarkovLaw markovLawInteger = new MarkovLaw("MarkovInteger");
        List<Edge> ed = Arrays.asList(
                new Edge<>(1, 0.1, 2),
                new Edge<>(2, 0.2, 1));
        markovLawInteger.setList(ed);
        markovLawInteger.setValType(Var.Type.Integer);

        laws.put("markoov", markovLaw);
        laws.put("markovInteger", markovLawInteger);

        // FUNCTION :: juste Double from now.
        FunctionLaw functionLaw = new FunctionLaw("myFunction");
        functionLaw.setCases(Arrays.asList(new CaseFunc("ici", "la"),
                new CaseFunc("labas", "where")));

        laws.put("myFunction", functionLaw);

        // RANDOM
        RandomLaw randomLaw = new RandomLaw("myRandom");
        randomLaw.setInterval(new Interval<>(Interval.Type.Integer, 1, 2));

        RandomLaw randomLawDouble = new RandomLaw("myRandomDouble");
        randomLawDouble.setInterval(new Interval<>(Interval.Type.Double, 1.0, 2.0));


        RandomLaw randomLawList = new RandomLaw("myRandomList");
        randomLawList.setList(new ListWrapper(Var.Type.Integer, Arrays.asList(1, 2, 3, 4)));

        RandomLaw randomLawListString = new RandomLaw("myRandomListString");
        randomLawListString.setList(new ListWrapper(Var.Type.String, Arrays.asList("a", "b", "c", "d")));


        laws.put("randomLawListString", randomLawListString);
        laws.put("myRandomList", randomLawList);
        laws.put("random", randomLaw);
        laws.put("myRandomDouble", randomLawDouble);

        // SENSOR
        Sensor sensor = new Sensor("lumi");
        SourceLaw sourceLaw = new SourceLaw("myRandomList");
        sensor.setSource(sourceLaw);
        sensor.setNoise(new Interval<>(Interval.Type.Integer, 1, 2));
        sensor.setOffset("10/10/1010 10:11");
        sensor.setPeriod("10ms");

        Sensor sensorOther = new Sensor("lumiOther");
        SourceLaw sourceLawOther = new SourceLaw("myRandomDouble");
        sensorOther.setSource(sourceLawOther);
        sensorOther.setPeriod("20ms");

        sensors.put("lumi", sensor);
        sensors.put("lumiOther", sensorOther);

        // AREAS
        SensorGroup sensorGroup = new SensorGroup("myRandomList", 3);
        SensorGroup sensorGroup1 = new SensorGroup("myRandomDouble", 4);
        Area area = new Area("parking",Arrays.asList(sensorGroup, sensorGroup1 ));
        areas.put("parking", area);

        // EXEC
        AreaGroup areaGroup = new AreaGroup("parking", Arrays.asList("parking1", "parking2" ));
        Exec exec = new Exec("execParking",Arrays.asList(areaGroup) );
        execs.put("execcces", exec);
        // GLOBAL
        Global global = new Global();
        global.setRealtime();
        global.setReaply("09/12/2018 00:00", "09/12/2018 12:00");

        Model model = new Model(laws, sensors, areas, execs, global);
        SSLAppGenerator sslAppGenerator = new SSLAppGenerator(model);
        sslAppGenerator.createContext();
        sslAppGenerator.writeTemplate();
    }

    @Override
    public void run() {
        // TODO
    }
}
