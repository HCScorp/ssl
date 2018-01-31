package ssl.dsl;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.Map;

public class GroovySSLBinding extends Binding {

    private Script script;

    private GroovySSLModel model;

    public GroovySSLBinding() {
        super();
    }

    @SuppressWarnings("rawtypes")
    public GroovySSLBinding(Map variables) {
        super(variables);
    }

    public GroovySSLBinding(Script script) {
        super();
        this.script = script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public void setGrovySSLModel(GroovySSLModel model) {
        this.model = model;
    }

    public Object getVariable(String name) {
        return super.getVariable(name);
    }

    public void setVariable(String name, Object value) {
        super.setVariable(name, value);
    }

    public GroovySSLModel getGrovySSLModel() {
        return this.model;
    }


}
