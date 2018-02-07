package ssl.dsl

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.syntax.Types
class GroovySensorDSL {

    private GroovyShell shell
    private CompilerConfiguration configuration
    private GroovySSLBinding binding

    GroovySensorDSL() {
        println "GroovySensorDSL constructor"
        binding = new GroovySSLBinding()
        binding.setGrovySSLModel(new SSLModel(binding));
        configuration = getDSLConfiguration()
        configuration.setScriptBaseClass("ssl.dsl.GroovyBaseScript")
        shell = new GroovyShell(configuration)
    }

    private static CompilerConfiguration getDSLConfiguration() {
        def secure = new SecureASTCustomizer()
        secure.with {
            //disallow closure creation
            closuresAllowed = true

            //disallow method definitions
            methodDefinitionAllowed = true
            //empty white list => forbid imports
            importsWhitelist = [
                    'java.lang.*'
            ]
            staticImportsWhitelist = []
            staticStarImportsWhitelist= []
            //language tokens disallowed
            //tokensBlacklist= []
            //language tokens allowed
            tokensWhitelist= [Types.ARRAY_EXPRESSION, Types.PREFIX_MINUS, Types.INTEGER_NUMBER,
                              Types.NUMBER, Types.MINUS, java.sql.Types.DECIMAL]
            //types allowed to be used  (including primitive types)
            constantTypesClassesWhiteList= [
                    int, Integer, Number, Integer.TYPE, String, Object, BigDecimal
            ]
            //classes who are allowed to be receivers of method calls
            receiversClassesWhiteList= [
                    int, Number, Integer, String, Object, BigDecimal
            ]
        }

        def configuration = new CompilerConfiguration()

        configuration.addCompilationCustomizers(secure)

        return configuration
    }

    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)
        binding.setScript(script)
        script.setBinding(binding)

        script.run()
    }

}
