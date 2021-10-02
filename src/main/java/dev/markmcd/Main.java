package dev.markmcd;

import dev.markmcd.controller.Controller;
import dev.markmcd.model.Model;
import dev.markmcd.view.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        // setup main program (MVC structure based off https://www.edureka.co/blog/mvc-architecture-in-java/)
        Model model = new Model();
        View view = new View();
        new Controller(model, view, args);

    }
}
