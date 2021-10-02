package dev.markmcd.controller;

import dev.markmcd.model.Model;
import dev.markmcd.view.View;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.markmcd.utils.Utils.parseArgs;

/**
 * Reads input files, validates the model, runs the tests, and then runs the model checker
 */
public class Controller {

    private Model model;
    private View view;
    private String[] args;
    private ModelInputSource modelInputSource;
    private Boolean runTests;

    private String modelTestFilesDir = "modelTestFiles/";
    private List modelTestFiles = new ArrayList<String>(Arrays.asList("Model 1.txt","Model 2.txt","Model 3.txt","Model 4.txt","Model 5.txt","Model 6.txt","Model 7.txt"));

    public Controller(Model model, View view, String[] args) throws IOException {
        this.model = model;
        this.view = view;
        runProgram(args);
    }

    private void runProgram(String[] args) throws IOException {

        getArguments(args);
        // runTests();
        // checkKripkeSyntax
        // checkModelSyntax
        // checkStateToCheck
        // checkModel(args, modelInputSource);
        // updateView();

    }

    private void getArguments(String[] args) throws IOException {
        Arguments arguments = parseArgs(args);
        model.setArguments(arguments);
    }

    private void runTests() {
           // run dr. p test files and check results are correct
    }

    private void checkKripkeSyntax() {

    }

    private void checkModelSyntax() {

    }

    // checks if the state specified to check exists
    private void checkStateToCheck() {

    }

    private void checkModel() {

    }
}
