package hcs.dsl.ssl.antlr;

import hcs.dsl.ssl.antlr.grammar.SSLBaseListener;
import hcs.dsl.ssl.model.Model;
import hcs.dsl.ssl.model.Namable;
import hcs.dsl.ssl.model.area.Area;
import hcs.dsl.ssl.model.area.SensorGroup;
import hcs.dsl.ssl.model.app.App;
import hcs.dsl.ssl.model.app.AreaGroup;
import hcs.dsl.ssl.model.global.Global;
import hcs.dsl.ssl.model.law.*;
import hcs.dsl.ssl.model.law.file.*;
import hcs.dsl.ssl.model.law.file.header.CsvHeader;
import hcs.dsl.ssl.model.law.file.header.JsonHeader;
import hcs.dsl.ssl.model.law.function.CaseFunc;
import hcs.dsl.ssl.model.law.function.FunctionLaw;
import hcs.dsl.ssl.model.law.markov.Edge;
import hcs.dsl.ssl.model.law.markov.MarkovLaw;
import hcs.dsl.ssl.model.law.random.RandomLaw;
import hcs.dsl.ssl.model.misc.Interval;
import hcs.dsl.ssl.model.misc.ListWrapper;
import hcs.dsl.ssl.model.misc.ValType;
import hcs.dsl.ssl.model.sensor.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final Map<String, App> apps = new HashMap<>();
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
        checkMinimalConfig();

        this.model = new Model(laws, sensors, areas, apps, global);
        this.built = true;
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

    private void add(App app) {
        checkAlreadyDefined(app, apps, "app");
        apps.put(app.getName(), app);
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
            return new ListWrapper(ValType.Double, list.list_double().elem.stream()
                    .map(ModelBuilder::toDouble)
                    .collect(Collectors.toList()));
        } else if (list.list_integer() != null) {
            return new ListWrapper(ValType.Integer, list.list_integer().elem.stream()
                    .map(ModelBuilder::toInt)
                    .collect(Collectors.toList()));
        } else if (list.list_boolean() != null) {
            return new ListWrapper(ValType.Boolean, list.list_boolean().elem.stream()
                    .map(ModelBuilder::toBoolean)
                    .collect(Collectors.toList()));
        } else if (list.list_string() != null) {
            return new ListWrapper(ValType.String, list.list_string().elem.stream()
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

        setEdgesIfPresent(law, ValType.Double, def.edge_double(), ModelBuilder::toEdgeDouble);
        setEdgesIfPresent(law, ValType.Integer, def.edge_integer(), ModelBuilder::toEdgeInteger);
        setEdgesIfPresent(law, ValType.Boolean, def.edge_boolean(), ModelBuilder::toEdgeBoolean);
        setEdgesIfPresent(law, ValType.String, def.edge_string(), ModelBuilder::toEdgeString);

        if (law.getValType() == null) {
            throw new IllegalArgumentException("invalid markov law definition: " + def);
        }

        return law;
    }

    private static <T extends ParserRuleContext, V extends Serializable>
    void setEdgesIfPresent(MarkovLaw law,
                           ValType type,
                           List<T> elems,
                           Function<T, Edge<V>> mapper) {
        if (!elems.isEmpty()) {
            law.setValType(type);
            law.setList(elems.stream().map(mapper).collect(Collectors.toList()));
        }
    }

    private static Edge<Double> toEdgeDouble(Edge_doubleContext e) {
        return new Edge<>(toDouble(e.from), toDouble(e.proba), toDouble(e.to));
    }

    private static Edge<Integer> toEdgeInteger(Edge_integerContext e) {
        return new Edge<>(toInt(e.from), toDouble(e.proba), toInt(e.to));
    }

    private static Edge<Boolean> toEdgeBoolean(Edge_booleanContext e) {
        return new Edge<>(toBoolean(e.from), toDouble(e.proba), toBoolean(e.to));
    }

    private static Edge<String> toEdgeString(Edge_stringContext e) {
        return new Edge<>(toStringTrim(e.from), toDouble(e.proba), toStringTrim(e.to));
    }

    @Override
    public void enterFunction(FunctionContext ctx) {
        FunctionLaw law = buildFunctionLaw(ctx);
        add(law);
    }

    private static FunctionLaw buildFunctionLaw(FunctionContext ctx) {
        FunctionLaw law = new FunctionLaw(toString(ctx.name));
        Function_defContext def = ctx.function_def();

        setCasesIfPresent(law, ValType.Double, def.caseFcExpr(), ModelBuilder::toCaseFuncExpr);
        setCasesIfPresent(law, ValType.String, def.caseFcString(), ModelBuilder::toCaseFuncString);
        setCasesIfPresent(law, ValType.Integer, def.caseFcInteger(), ModelBuilder::toCaseFuncInteger);
        setCasesIfPresent(law, ValType.Double, def.caseFcDouble(), ModelBuilder::toCaseFuncDouble);
        setCasesIfPresent(law, ValType.Boolean, def.caseFcBoolean(), ModelBuilder::toCaseFuncBoolean);

        if (law.getValType() == null) {
            throw new IllegalArgumentException("invalid function law definition: " + def);
        }

        return law;
    }

    private static <T extends ParserRuleContext> void setCasesIfPresent(FunctionLaw law,
                                                                        ValType type,
                                                                        List<T> elems,
                                                                        Function<T, CaseFunc> mapper) {
        if (!elems.isEmpty()) {
            law.setValType(type);
            law.setCases(elems.stream().map(mapper).collect(Collectors.toList()));
        }
    }

    private static CaseFunc toCaseFuncExpr(CaseFcExprContext e) {
        CaseFunc cf = new CaseFunc(toStringTrim(e.cond));
        cf.setExpression(toStringTrim(e.expr));
        return cf;
    }

    private static CaseFunc toCaseFuncString(CaseFcStringContext e) {
        CaseFunc cf = new CaseFunc(toStringTrim(e.cond));
        cf.setStrVal(toStringTrim(e.expr));
        return cf;
    }

    private static CaseFunc toCaseFuncInteger(CaseFcIntegerContext e) {
        CaseFunc cf = new CaseFunc(toStringTrim(e.cond));
        cf.setIntVal(toInt(e.expr));
        return cf;
    }

    private static CaseFunc toCaseFuncDouble(CaseFcDoubleContext e) {
        CaseFunc cf = new CaseFunc(toStringTrim(e.cond));
        cf.setDoubleVal(toDouble(e.expr));
        return cf;
    }

    private static CaseFunc toCaseFuncBoolean(CaseFcBooleanContext e) {
        CaseFunc cf = new CaseFunc(toStringTrim(e.cond));
        cf.setBoolVal(toBoolean(e.expr));
        return cf;
    }


    @Override
    public void enterFile(FileContext ctx) {
        FileLaw law = buildFileLaw(ctx);
        add(law);
    }

    private FileLaw buildFileLaw(FileContext ctx) {
        File_defContext def = ctx.file_def();
        Location_defContext locDef = def.location_def();

        FileLaw law;
        String lawName = toString(ctx.name);
        String sensorName = toStringTrim(def.sensor_name().name);

        if (locDef.type_csv() != null) {
            FileLawCsv lawCsv = new FileLawCsv(lawName, toStringTrim(locDef.type_csv().uri), sensorName);
            Header_csvContext header = locDef.type_csv().header_csv();
            if (header != null) {
                lawCsv.setCsvHeader(buildCsvHeader(header));
            }
            law = lawCsv;
        } else if (locDef.type_json() != null) {
            FileLawJson lawJson = new FileLawJson(lawName, toStringTrim(locDef.type_json().uri), sensorName);
            Header_jsonContext header = locDef.type_json().header_json();
            if (header != null) {
                lawJson.setJsonHeader(buildJsonHeader(header));
            }
            law = lawJson;
        } else {
            throw new IllegalArgumentException("invalid file law definition: " + ctx);
        }

        law.setFileLocation(toString(locDef.location));

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
                header.setF1Index(toPositiveOrNullInt(ctx.f1_name));
            }

            header.setF1Type(toString(ctx.f1_type));

            if (ctx.f2_name != null && ctx.f2_type != null) {
                if (isFromString(ctx.f2_name)) {
                    header.setF2Name(toStringTrim(ctx.f2_name));
                } else {
                    header.setF2Index(toPositiveOrNullInt(ctx.f2_name));
                }

                header.setF2Type(toString(ctx.f2_type));

                if (ctx.f3_name != null && ctx.f3_type != null) {
                    if (isFromString(ctx.f3_name)) {
                        header.setF3Name(toStringTrim(ctx.f3_name));
                    } else {
                        header.setF3Index(toPositiveOrNullInt(ctx.f3_name));
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

            ValType tLaw = laws.get(sensor.getLawRef()).getValType();
            ValType tNoise = sensor.getNoise().getValType();
            if (tLaw != tNoise) {
                throw new IllegalArgumentException("noise of type " + tNoise + " from interval " + def.noise().getText() + " does not match law value type " + tLaw + " for sensor " + sensor.getName());
            }
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
        if (ctx.ref == null) {
            throw new IllegalArgumentException("law reference must be defined");
        }

        String lawRef = toString(ctx.ref);
        if (!laws.containsKey(lawRef)) {
            throw new IllegalArgumentException("law '" + lawRef + "' is referenced before definition");
        }

        return lawRef;
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

        SensorGroup sg = new SensorGroup(sensorRef, toPositiveInt(ctx.nb));

        if (ctx.noise_override() != null) {
            sg.setNoise(buildInterval(ctx.noise_override().interval()));

            ValType tLaw = laws.get(sensors.get(sensorRef).getLawRef()).getValType();
            ValType tNoise = sg.getNoise().getValType();
            if (tLaw != tNoise) {
                throw new IllegalArgumentException("noise of type " + tNoise + " from interval " + ctx.noise_override().getText() + " does not match law value type " + tLaw + " for sensor " + sensorRef);
            }
        }

        // TODO add possibility for additional noise in addition to override noise

        return sg;
    }

    @Override
    public void enterApp(AppContext ctx) {
        App app = new App(toString(ctx.name),
                ctx.app_def().area_group().stream()
                        .map(this::buildAreaGroup)
                        .collect(Collectors.toList()));

        add(app);
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

            if (def.realtime().g_offset() != null) {
                global.setOffset(
                        toString(def.realtime().g_offset().date));
            }
        }
    }

    ///////////////////
    ////  Checker  ////
    ///////////////////

    private void checkAlreadyDefined(Namable namable, Map map, String type) {
        if (map.containsKey(namable.getName())) {
            throw new IllegalArgumentException("trying to redefine already existing " + type + " " + namable.getName());
        }
    }

    private void checkMinimalConfig() {
        if (global == null) {
            System.out.println("Script has not global configuration, using default: realtime with no offset");
            global = new Global();
            global.setRealtime();
        }

        if (laws.isEmpty() || sensors.isEmpty() || areas.isEmpty() || apps.isEmpty()) {
            throw new IllegalArgumentException("you must define at least one law, one sensor, one area and one app");
        }
    }


    ///////////////////
    ////  Helper   ////
    ///////////////////

    private static Integer toInt(Token token) {
        return Integer.parseInt(token.getText());
    }

    private static Integer toPositiveOrNullInt(Token token) {
        Integer result = Integer.parseInt(token.getText());

        if (result <= 0) {
            throw new IllegalArgumentException("expected positive or null integer, got: " + result + " (are you trying to set a negative CSV column?)");
        }

        return result;
    }

    private static Integer toPositiveInt(Token token) {
        Integer result = Integer.parseInt(token.getText());

        if (result < 0) {
            throw new IllegalArgumentException("expected strictly positive integer, got: " + result + " (are you trying to have a negative number of sensors?)");
        }

        return result;
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

    private static boolean isFromString(Token token) {
        return token.getText().startsWith("\"") && token.getText().endsWith("\"");
    }

}

