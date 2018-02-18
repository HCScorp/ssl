import hcs.dsl.ssl.antlr.ModelBuilder;
import hcs.dsl.ssl.antlr.StopErrorListener;
import hcs.dsl.ssl.antlr.grammar.SSLLexer;
import hcs.dsl.ssl.antlr.grammar.SSLParser;
import hcs.dsl.ssl.model.Model;
import hcs.dsl.ssl.generator.SSLAppGenerator;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Running the ANTLR compiler for SSL");

        CharStream stream = getCharStream(args);
        Model model = buildModel(stream);
        exportToCode(model);
    }

    private static CharStream getCharStream(String[] args) throws IOException {
        if (args.length < 1)
            throw new RuntimeException("no input file");
        Path input = Paths.get(new File(args[0]).toURI());
        System.out.println("Using input file: " + input);
        return CharStreams.fromPath(input);
    }

    private static Model buildModel(CharStream stream) {
        SSLLexer lexer = new SSLLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new StopErrorListener());

        SSLParser parser = new SSLParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(new StopErrorListener());

        ParseTreeWalker walker = new ParseTreeWalker();
        ModelBuilder builder = new ModelBuilder();

        walker.walk(builder, parser.root()); // parser.root() is the entry point of the grammar

        return builder.retrieve();
    }

    private static void exportToCode(Model model) {
        SSLAppGenerator generator = new SSLAppGenerator(model);
        generator.run();
    }

}
