package hcs.dsl.ssl.antlr;

import hcs.dsl.ssl.antlr.grammar.SSLBaseListener;
import hcs.dsl.ssl.model.Model;
import hcs.dsl.ssl.model.Namable;
import hcs.dsl.ssl.model.area.Area;
import hcs.dsl.ssl.model.area.SensorGroup;
import hcs.dsl.ssl.model.exec.AreaGroup;
import hcs.dsl.ssl.model.exec.Exec;
import hcs.dsl.ssl.model.global.Global;
import hcs.dsl.ssl.model.law.*;
import hcs.dsl.ssl.model.misc.Interval;
import hcs.dsl.ssl.model.misc.ListWrapper;
import hcs.dsl.ssl.model.misc.Var.Type;
import hcs.dsl.ssl.model.sensor.*;
import org.antlr.v4.runtime.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static hcs.dsl.ssl.antlr.grammar.SSLParser.*;

public class ModelBuilder extends SSLBaseListener {

    /********************
     ** Business Logic **
     ********************/

    private Model model = null;
    private boolean built = false;

    public Model retrieve() {
        if (built) {
            return model;
        }
        throw new RuntimeException("cannot retrieve a model that was not created!");
    }

    private final Map<String, Law> laws = new HashMap<>();
    private final Map<String, Sensor> sensors = new HashMap<>();
    private final Map<String, Area> areas = new HashMap<>();
    private final Map<String, Exec> execs = new HashMap<>();
    private Global global;

    /**************************
     ** Listening mechanisms **
     **************************/

    @Override
    public void enterRoot(RootContext ctx) {
        this.built = false;
    }

    @Override
    public void exitRoot(RootContext ctx) {
        this.model = new Model(laws, sensors, areas, execs, global);
        this.built = true;

        // check noise override type --> already done at runtime (init)
        // TODO add possibility for additional noise in addition to override noise
    }

    private void add(Law law) {
        checkAlreadyDefined(law, laws, "law");
        laws.put(law.getName(), law);
    }

    private void add(Sensor sensor) {
        checkAlreadyDefined(sensor, sensors, "sensor");
        sensors.put(sensor.getName(), sensor);
    }

    private void add(Area area) {
        checkAlreadyDefined(area, areas, "area");
        areas.put(area.getName(), area);
    }

    private void add(Exec exec) {
        checkAlreadyDefined(exec, execs, "exec");
        execs.put(exec.getName(), exec);
    }

    private void checkAlreadyDefined(Namable namable, Map map, String type) {
        if (map.containsKey(namable.getName())) {
            throw new IllegalArgumentException("trying to redefine already existing " + type + " " + namable.getName());
        }
    }

    @Override
    public void enterRandom(RandomContext ctx) {
        RandomLaw law = buildRandomLaw(ctx);
        add(law);
    }

    private static RandomLaw buildRandomLaw(RandomContext ctx) {
        RandomLaw law = new RandomLaw(toString(ctx.name));
        Random_defContext def = ctx.random_def();
        if (def.random_list() != null) {
            law.setList(buildList(def.random_list().list()));
        } else if (def.random_interval() != null) {
            law.setInterval(buildInterval(def.random_interval().interval()));
        } else {
            throw new IllegalArgumentException("invalid random law definition: " + def);
        }

        return law;
    }

    private static Interval buildInterval(IntervalContext ctx) {

        if (ctx.interval_double() != null) {
            Interval_doubleContext dCtx = ctx.interval_double();
            return new Interval<>(Interval.Type.Double, toDouble(dCtx.min), toDouble(dCtx.max));
        } else if (ctx.interval_integer() != null) {
            Interval_integerContext iCtx = ctx.interval_integer();
            return new Interval<>(Interval.Type.Integer, toInt(iCtx.min), toInt(iCtx.max));
        }

        throw new IllegalArgumentException("invalid interval: " + ctx);
    }

    private static ListWrapper buildList(ListContext list) {
        if (list.list_double() != null) {
            return new ListWrapper(Type.Double, list.list_double().elem.stream()
                    .map(ModelBuilder::toDouble)
                    .collect(Collectors.toList()));
        } else if (list.list_integer() != null) {
            return new ListWrapper(Type.Integer, list.list_integer().elem.stream()
                    .map(ModelBuilder::toInt)
                    .collect(Collectors.toList()));
        } else if (list.list_boolean() != null) {
            return new ListWrapper(Type.Boolean, list.list_boolean().elem.stream()
                    .map(ModelBuilder::toBoolean)
                    .collect(Collectors.toList()));
        } else if (list.list_string() != null) {
            return new ListWrapper(Type.String, list.list_string().elem.stream()
                    .map(ModelBuilder::toStringTrim)
                    .collect(Collectors.toList()));
        }

        throw new IllegalArgumentException("invalid list: " + list);
    }

    @Override
    public void enterMarkov(MarkovContext ctx) {
        MarkovLaw law = buildMarkovLaw(ctx);
        add(law);
    }

    private static MarkovLaw buildMarkovLaw(MarkovContext ctx) {
        MarkovLaw law = new MarkovLaw(toString(ctx.name));
        Markov_defContext def = ctx.markov_def();

        if (!def.edge_double().isEmpty()) {
            law.setValType(Type.Double);
            law.setList(def.edge_double().stream()
                    .map(e -> new Edge<>(toDouble(e.from), toDouble(e.proba), toDouble(e.to)))
                    .collect(Collectors.toList()));
        } else if (!def.edge_integer().isEmpty()) {
            law.setValType(Type.Integer);
            law.setList(def.edge_integer().stream()
                    .map(e -> new Edge<>(toInt(e.from), toDouble(e.proba), toInt(e.to)))
                    .collect(Collectors.toList()));
        } else if (!def.edge_boolean().isEmpty()) {
            law.setValType(Type.Boolean);
            law.setList(def.edge_boolean().stream()
                    .map(e -> new Edge<>(toBoolean(e.from), toDouble(e.proba), toBoolean(e.to)))
                    .collect(Collectors.toList()));
        } else if (!def.edge_string().isEmpty()) {
            law.setValType(Type.String);
            law.setList(def.edge_string().stream()
                    .map(e -> new Edge<>(toStringTrim(e.from), toDouble(e.proba), toStringTrim(e.to)))
                    .collect(Collectors.toList()));
        } else {
            throw new IllegalArgumentException("invalid markov law definition: " + def);
        }

        return law;
    }

    @Override
    public void enterFunction(FunctionContext ctx) {
        FunctionLaw law = buildFunctionLaw(ctx);
        add(law);
    }

    private static FunctionLaw buildFunctionLaw(FunctionContext ctx) {
        FunctionLaw law = new FunctionLaw(toString(ctx.name));
        Function_defContext def = ctx.function_def();

        law.setCases(def.caseFunc().stream()
                .map(ModelBuilder::toCaseFunc)
                .collect(Collectors.toList()));

        return law;
    }

    private static CaseFunc toCaseFunc(CaseFuncContext e) {
        return new CaseFunc(toStringTrim(e.cond), toStringTrim(e.expr));
    }


    @Override
    public void enterFile(FileContext ctx) {
        FileLaw law = buildFileLaw(ctx);
        add(law);
    }

    private FileLaw buildFileLaw(FileContext ctx) {
        FileLaw law;
        String name = toString(ctx.name);
        File_defContext def = ctx.file_def();
        if (def.type_csv() != null) {
            FileLawCsv src = new FileLawCsv(name, toStringTrim(ctx.name));
            Header_csvContext header = def.type_csv().header_csv();
            if (header != null) {
                src.setCsvHeader(buildCsvHeader(header));
            }
            law = src;
        } else if (def.type_json() != null) {
            FileLawJson src = new FileLawJson(name, toStringTrim(ctx.name));
            Header_jsonContext header = def.type_json().header_json();
            if (header != null) {
                src.setJsonHeader(buildJsonHeader(header));
            }
            law = src;
        } else {
            throw new IllegalArgumentException("invalid file law definition: " + ctx);
        }

        law.setFileLocation(toString(def.location));

        if (def.interpolation() != null) {
            law.setInterpolation(buildInterpolation(def.interpolation()));
        }


        return law;
    }


    private static Interpolation buildInterpolation(InterpolationContext ctx) {
        Interpolation interpolation = new Interpolation();
        if (ctx.restriction() != null) {
            interpolation.setRestriction(buildInterval(ctx.restriction().interval()));
        }
        return interpolation;
    }

    private static JsonHeader buildJsonHeader(Header_jsonContext ctx) {
        JsonHeader header = new JsonHeader();

        if (ctx.f1_name != null && ctx.f1_type != null) {

            header.setF1Name(toStringTrim(ctx.f1_name));
            header.setF1Type(toString(ctx.f1_type));

            if (ctx.f2_name != null && ctx.f2_type != null) {
                header.setF2Name(toStringTrim(ctx.f2_name));
                header.setF2Type(toString(ctx.f2_type));

                if (ctx.f3_name != null && ctx.f3_type != null) {
                    header.setF3Name(toStringTrim(ctx.f3_name));
                    header.setF3Type(toString(ctx.f3_type));
                }
            }
        } else {
            throw new IllegalArgumentException("json headers are not well defined: " + ctx);
        }

        return header;
    }

    private static CsvHeader buildCsvHeader(Header_csvContext ctx) {
        CsvHeader header = new CsvHeader();

        if (ctx.f1_name != null && ctx.f1_type != null) {
            if (isFromString(ctx.f1_name)) {
                header.setF1Name(toStringTrim(ctx.f1_name));
            } else {
                header.setF1Index(toInt(ctx.f1_name));
            }

            header.setF1Type(toString(ctx.f1_type));

            if (ctx.f2_name != null && ctx.f2_type != null) {
                if (isFromString(ctx.f2_name)) {
                    header.setF2Name(toStringTrim(ctx.f2_name));
                } else {
                    header.setF2Index(toInt(ctx.f2_name));
                }

                header.setF2Type(toString(ctx.f2_type));

                if (ctx.f3_name != null && ctx.f3_type != null) {
                    if (isFromString(ctx.f3_name)) {
                        header.setF3Name(toStringTrim(ctx.f3_name));
                    } else {
                        header.setF3Index(toInt(ctx.f3_name));
                    }

                    header.setF3Type(toString(ctx.f3_type));
                }
            }
        } else {
            throw new IllegalArgumentException("csv headers are not well defined: " + ctx);
        }

        return header;
    }

    @Override
    public void enterSensor(SensorContext ctx) {
        Sensor sensor = new Sensor(toString(ctx.name));
        Sensor_defContext def = ctx.sensor_def();

        sensor.setLawRef(buildLawRef(def.source()));

        if (def.noise() != null) {
            sensor.setNoise(buildInterval(def.noise().interval()));
        }

//        if (def.offset() != null) {
//            sensor.setOffset(toString(def.offset().date));
//        }

        if (def.period() != null) {
            sensor.setPeriod(toString(def.period().period_value));
        } else {
            sensor.setPeriod("5m");
        }

        add(sensor);
    }

    private String buildLawRef(SourceContext ctx) {
        if (ctx.law_ref() == null) {
            throw new IllegalArgumentException("law reference must be defined");
        }

        String lawRef = toString(ctx.law_ref().ref);
        if (!laws.containsKey(lawRef)) {
            throw new IllegalArgumentException("law '" + lawRef + "' is referenced before definition");
        }

        return lawRef;
    }

    private static boolean isFromString(Token token) {
        return token.getText().startsWith("\"") && token.getText().endsWith("\"");
    }

    @Override
    public void enterArea(AreaContext ctx) {
        Area area = new Area(toString(ctx.name),
                ctx.area_def().sensor_group().stream()
                        .map(this::buildSensorGroup)
                        .collect(Collectors.toList()));

        add(area);
    }

    private SensorGroup buildSensorGroup(Sensor_groupContext ctx) {
        String sensorRef = toString(ctx.sensor_ref);
        if (!sensors.containsKey(sensorRef)) {
            throw new IllegalArgumentException("sensor '" + sensorRef + "' is referenced before definition");
        }

        SensorGroup sg = new SensorGroup(sensorRef, toInt(ctx.nb));

        if (ctx.noise_override() != null) {
            sg.setNoise(buildInterval(ctx.noise_override().interval()));
        }

        return sg;
    }

    @Override
    public void enterExec(ExecContext ctx) {
        Exec exec = new Exec(toString(ctx.name),
                ctx.exec_def().area_group().stream()
                        .map(this::buildAreaGroup)
                        .collect(Collectors.toList()));

        add(exec);
    }

    private AreaGroup buildAreaGroup(Area_groupContext ctx) {
        String areaRef = toString(ctx.area_ref);
        if (!areas.containsKey(areaRef)) {
            throw new IllegalArgumentException("area '" + areaRef + "' is referenced before definition");
        }

        return new AreaGroup(areaRef,
                ctx.list_basic_string().elem.stream()
                        .map(ModelBuilder::toString)
                        .collect(Collectors.toList()));
    }

    @Override
    public void enterGlobal(GlobalContext ctx) {
        global = new Global();
        Global_defContext def = ctx.global_def();

        if (def.replay() != null) {
            global.setReplay(
                    toString(def.replay().start().date),
                    toString(def.replay().end().date));
        } else {
            global.setRealtime();

            if (def.realtime().globalOffset() != null) {
                global.setOffset(
                        toString(def.realtime().globalOffset().date));
            }
        }
    }

    private static Integer toInt(Token token) {
        return Integer.parseInt(token.getText());
    }

    private static Double toDouble(Token token) {
        return Double.parseDouble(token.getText());
    }

    private static Boolean toBoolean(Token token) {
        return Boolean.parseBoolean(token.getText());
    }

    private static String toString(Token token) {
        return token.getText();
    }

    private static String toStringTrim(Token token) {
        return token.getText().substring(1, token.getText().length() - 1);
    }
}

