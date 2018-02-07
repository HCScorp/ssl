package ssl.dsl;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.Map;

public class GroovySSLBinding extends Binding {

    private Script script;

    private SSLModel model;

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

    public void setGrovySSLModel(SSLModel model) {
        this.model = model;
    }

    public Object getVariable(String name) {
        System.out.println("get Variable == " + name);
        return super.getVariable(name);
    }

    public void setVariable(String name, Object value) {
        super.setVariable(name, value);
    }

    public SSLModel getGrovySSLModel() {
        return this.model;
    }


}
