package dev.markmcd;

import dev.markmcd.controller.Controller;
import dev.markmcd.controller.ctl.Parser.ParseException;
import dev.markmcd.controller.types.misc.Options;
import dev.markmcd.model.Model;
import dev.markmcd.view.View;

import java.io.IOException;

/**
 * Entry point for the whole program. The options are entered here as command line arguments.
 * The MVC structure of the program is based off https://www.edureka.co/blog/mvc-architecture-in-java/, accessed around 10/21
 */
public class Main {
    public static void main(String[] args) throws Exception {

        // options set here
        String testFilesDir = "end-to-end-tests";   // this is a subfolder under /resources
        Boolean printExceptions = true;             // this is whether to print error messages and continue the program (good for prod) or to throw an exception and halt the program there (good for debugging)

        // setup main program
        Options options = new Options(args, testFilesDir, printExceptions);
        Model model = new Model();
        View view = new View(options);
        new Controller(model, view, options);

    }
}
