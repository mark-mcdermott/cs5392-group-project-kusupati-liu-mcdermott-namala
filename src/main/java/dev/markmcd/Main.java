package dev.markmcd;

import dev.markmcd.controller.Controller;
import dev.markmcd.controller.ctl.Parser.ParseException;
import dev.markmcd.controller.types.misc.Options;
import dev.markmcd.model.Model;
import dev.markmcd.view.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        // options set here
        String testFilesDir = "end-to-end-tests";

        // setup main program (MVC structure based off https://www.edureka.co/blog/mvc-architecture-in-java/)
        Options options = new Options(args, testFilesDir);
        Model model = new Model();
        View view = new View();
        new Controller(model, view, options);

    }
}
