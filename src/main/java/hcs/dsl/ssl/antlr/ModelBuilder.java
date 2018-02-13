package hcs.dsl.ssl.antlr;

import hcs.dsl.ssl.antlr.grammar.SSLBaseListener;
import hcs.dsl.ssl.backend.Model;
import hcs.dsl.ssl.backend.area.Area;
import hcs.dsl.ssl.backend.area.SensorGroup;
import hcs.dsl.ssl.backend.exec.AreaGroup;
import hcs.dsl.ssl.backend.exec.Exec;
import hcs.dsl.ssl.backend.global.Global;
import hcs.dsl.ssl.backend.law.*;
import hcs.dsl.ssl.backend.misc.Interval;
import hcs.dsl.ssl.backend.misc.ListWrapper;
import hcs.dsl.ssl.backend.misc.Var.Type;
import hcs.dsl.ssl.backend.sensor.*;
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
        throw new RuntimeException("Cannot retrieve a model that was not created!");
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
    }

    @Override
    public void enterRandom(RandomContext ctx) {
        RandomLaw law = buildRandomLaw(ctx);
        laws.put(law.getName(), law);

        // TODO here or up, start the pre-code-generation phase (linear interpolation, type check ?)
        // TODO check math expression !!
        // TODO check already defined elements !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // TODO check noise type
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
        MarkovLaw l = buildMarkovLaw(ctx);
        laws.put(l.getName(), l);
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
            law.setList(def.edge_double().stream()
                    .map(e -> new Edge<>(toInt(e.from), toDouble(e.proba), toInt(e.to)))
                    .collect(Collectors.toList()));
        } else if (!def.edge_boolean().isEmpty()) {
            law.setValType(Type.Boolean);
            law.setList(def.edge_double().stream()
                    .map(e -> new Edge<>(toBoolean(e.from), toDouble(e.proba), toBoolean(e.to)))
                    .collect(Collectors.toList()));
        } else if (!def.edge_string().isEmpty()) {
            law.setValType(Type.String);
            law.setList(def.edge_double().stream()
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
        laws.put(law.getName(), law);
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
    public void enterSensor(SensorContext ctx) {
        Sensor sensor = new Sensor(toString(ctx.name));
        Sensor_defContext def = ctx.sensor_def();

        sensor.setSource(buildSource(def.source()));

        if (def.noise() != null) {
            sensor.setNoise(buildInterval(def.noise().interval()));
        }

//        if (def.offset() != null) {
//            sensor.setOffset(toString(def.offset().date));
//        }

        if (def.period() != null) {
            sensor.setPeriod(toString(def.period().period_value));
        }

        sensors.put(sensor.getName(), sensor);
    }

    private Source buildSource(SourceContext ctx) {
        Source src;

        if (ctx.law_ref() != null) {
            String lawRef = toString(ctx.law_ref().ref);
            if (!laws.containsKey(lawRef)) {
                throw new IllegalArgumentException("law '" + lawRef + "' is referenced before definition");
            }

            src = new SourceLaw(lawRef);

        } else if (ctx.file_input() != null) {
            File_inputContext fCtx = ctx.file_input();

            SourceFile srcFile;

            if (fCtx.type_csv() != null) {
                srcFile = buildCsvPartialSrc(fCtx.type_csv());
            } else if (fCtx.type_json() != null) {
                srcFile = buildJsonPartialSrc(fCtx.type_json());
            } else {
                throw new IllegalArgumentException("invalid source definition: " + ctx);
            }

            srcFile.setLocation(toString(fCtx.location));

            if (fCtx.interpolation() != null) {
                srcFile.setInterpolation(buildInterpolation(fCtx.interpolation()));
            }

            src = srcFile;
        } else {
            throw new IllegalArgumentException("invalid source definition: " + ctx);
        }

        return src;
    }

    private static Interpolation buildInterpolation(InterpolationContext ctx) {
        Interpolation interpolation = new Interpolation();
        if (ctx.restriction() != null) {
            interpolation.setRestriction(buildInterval(ctx.restriction().interval()));
        }
        return interpolation;
    }

    private static SourceJson buildJsonPartialSrc(Type_jsonContext ctx) {
        SourceJson src = new SourceJson(toStringTrim(ctx.name));
        if (ctx.header_json() != null) {
            src.setJsonHeader(buildJsonHeader(ctx.header_json()));
        }
        return src;
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

    private static SourceCsv buildCsvPartialSrc(Type_csvContext ctx) {
        SourceCsv src = new SourceCsv(toStringTrim(ctx.name));
        if (ctx.header_csv() != null) {
            src.setCsvHeader(buildCsvHeader(ctx.header_csv()));
        }
        return src;
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

    private static boolean isFromString(Token token) {
        return token.getText().startsWith("\"") && token.getText().endsWith("\"");
    }

    @Override
    public void enterArea(AreaContext ctx) {
        Area area = new Area(toString(ctx.name),
                ctx.area_def().sensor_group().stream()
                        .map(this::buildSensorGroup)
                        .collect(Collectors.toList()));

        areas.put(area.getName(), area);
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

        execs.put(exec.getName(), exec);
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
            global.setReaply(
                    toString(def.replay().start().date),
                    toString(def.replay().end().date));
        } else {
            global.setRealtime();
        }
    }

    public static Integer toInt(Token token) {
        return Integer.parseInt(token.getText());
    }

    public static Double toDouble(Token token) {
        return Double.parseDouble(token.getText());
    }

    public static Boolean toBoolean(Token token) {
        return Boolean.parseBoolean(token.getText());
    }


    public static String toString(Token token) {
        return token.getText();
    }

    public static String toStringTrim(Token token) {
        return token.getText().substring(1, token.getText().length() - 1);
    }
}

