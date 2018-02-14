package hcs.dsl.ssl.generator;

import hcs.dsl.ssl.model.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import hcs.dsl.ssl.model.law.FunctionLaw;
import hcs.dsl.ssl.model.law.MarkovLaw;
import hcs.dsl.ssl.model.law.RandomLaw;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SSLAppGenerator implements Runnable {

    private static final String ROOT = "src/main/";

    private static final String TEMPLATE_SIMU = ROOT + "resources/template/simulation.vm";
    private static final String TEMPLATE_MAIN = ROOT + "resources/template/main.vm";

    private static final String OUT_NAME_FILE = ROOT + "java/Simulation.java";
    private static final String OUT_MAIN_FILE = ROOT + "java/Main.java";

    private final VelocityEngine engine;
    private final VelocityContext context;
    private final Model model;

    private final File dockerfile;
    private final File pom;
    private final File build;

    public SSLAppGenerator(Model model) {
        this.engine = new VelocityEngine();
        this.context = new VelocityContext();
        this.model = model;

        this.dockerfile = new File("src/main/resources/extern/Dockerfile");
        this.pom = new File("src/main/resources/extern/pom.xml");
        this.build = new File("src/main/resources/extern/build.sh");
    }

    private void createContext(){
        context.put("areas", model.areas);
        context.put("laws", model.laws);
        context.put("sensors", model.sensors);
        context.put("execs", model.execs);
        context.put("global", model.global);

        context.put("RandomLaw", RandomLaw.class);
        context.put("MarkovLaw", MarkovLaw.class);
        context.put("FunctionLaw", FunctionLaw.class);
    }


    private void createTree(File newExec){
        newExec.getParentFile().mkdirs();
    }

    private void writeTemplate(){
        model.execs.forEach((key, value) -> {
            String outputpath = "generated/" +  key + "/";
            File simulation = new File(outputpath + OUT_NAME_FILE);
            File main = new File(outputpath + OUT_MAIN_FILE);

            context.put("executor", key);
            createTree(simulation);

            File dockerOut = new File(outputpath + "Dockerfile");
            File pomOut = new File(outputpath + "pom.xml");
            File buildOut = new File(outputpath + "build.sh");

            try {
                Files.copy(dockerfile.toPath(), dockerOut.toPath(),REPLACE_EXISTING );
                Files.copy(pom.toPath(), pomOut.toPath(), REPLACE_EXISTING);
                Files.copy(build.toPath(), buildOut.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            Template templateSimulation = engine.getTemplate(TEMPLATE_SIMU);
            Template templateMain = engine.getTemplate(TEMPLATE_MAIN);

            mergeTemplate(templateMain, main);
            mergeTemplate(templateSimulation, simulation);
        });

    }


    private void mergeTemplate(Template templateSensor, File templateFile){
        try (FileWriter fw = new FileWriter(templateFile)) {
            templateSensor.merge(context, fw);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void run() {
        this.createContext();
        this.writeTemplate();
    }
}
