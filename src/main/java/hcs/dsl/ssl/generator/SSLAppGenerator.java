package hcs.dsl.ssl.generator;

import hcs.dsl.ssl.backend.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import hcs.dsl.ssl.backend.law.FunctionLaw;
import hcs.dsl.ssl.backend.law.MarkovLaw;
import hcs.dsl.ssl.backend.law.RandomLaw;
import hcs.dsl.ssl.backend.sensor.SourceLaw;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SSLAppGenerator implements Runnable {

    private VelocityEngine engine;

    private VelocityContext context;

    private final Model model;


    private static final String TEMPLATE_SENSOR = "src/main/resources/template/sensor.vm";
    private static final String TEMPLATE_MAIN = "src/main/resources/template/main.vm";

    private static final String OUT_NAME_FILE = "src/main/java/Simulation.java";
    private static final String OUT_MAIN_FILE = "src/main/java/Main.java";

    File dockerfile = new File("src/main/resources/extern/Dockerfile");
    File pom = new File("src/main/resources/extern/pom.xml");

    public SSLAppGenerator(Model model) {
        engine = new VelocityEngine();
        context = new VelocityContext();
        this.model = model;
    }



    public void createContext(){
        context.put("areas", model.areas);
        context.put("laws", model.laws);
        context.put("sensors", model.sensors);
        context.put("execs", model.execs);
        context.put("global", model.global);

        context.put("RandomLaw", RandomLaw.class);
        context.put("MarkovLaw", MarkovLaw.class);
        context.put("FunctionLaw", FunctionLaw.class);
        context.put("SourceLaw", SourceLaw.class);
    }


    public void createTree(File newExec){
        newExec.getParentFile().mkdirs();
    }

    public void writeTemplate(){
        model.execs.forEach((key, value) -> {

            String outPutPath = "generated/" +  key + "/";
            File simulation = new File(outPutPath + OUT_NAME_FILE);
            File main = new File(outPutPath + OUT_MAIN_FILE);
            context.put("executor", key);
            createTree(simulation);
            File dockerOut = new File(outPutPath + "Dockerfile");
            File pomOut = new File(outPutPath + "pom.xml");


            try {
                Files.copy(dockerfile.toPath(), dockerOut.toPath(),REPLACE_EXISTING );
                Files.copy(pom.toPath(), pomOut.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Template templateSimulation = engine.getTemplate(TEMPLATE_SENSOR);
            Template templateMain = engine.getTemplate(TEMPLATE_MAIN);

            mergeTemplate(templateMain, main);
            mergeTemplate(templateSimulation, simulation);
        });

    }


    public void mergeTemplate(Template templateSensor, File templateFile){
        try (FileWriter fw = new FileWriter(templateFile)) {
            templateSensor.merge(context, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        this.createContext();
        this.writeTemplate();

    }
}
