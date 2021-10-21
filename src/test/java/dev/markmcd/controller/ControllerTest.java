package dev.markmcd.controller;

import dev.markmcd.controller.ctl.Parser.ParseException;
import dev.markmcd.controller.types.kripke.Kripke;
import dev.markmcd.controller.types.misc.TestFiles;
import dev.markmcd.model.Model;
import dev.markmcd.view.View;
import dev.markmcd.controller.Controller;
import dev.markmcd.controller.types.misc.Options;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;

public class ControllerTest {

    // test if Controller throws NullPointerException if model param is null
    @Test(expected = NullPointerException.class)
    public void testController1() throws IOException, ParseException {
        Model model = null;
        View view = new View();
        Options options = new Options();
        new Controller(model,view,options);
    }

    // test if Controller throws NullPointerException if view param is null
    @Test(expected = NullPointerException.class)
    public void testController2() throws IOException, ParseException {
        Model model = new Model();
        View view = null;
        Options options = new Options();
        new Controller(model,view,options);
    }

    // test if Controller throws NullPointerException if args param is null
    @Test(expected = NullPointerException.class)
    public void testController3() throws IOException, ParseException {
        Model model = new Model();
        View view = new View();
        Options options = new Options();
        new Controller(model,view,options);
    }

    // test if runProgram throws NullException if args param is null
    @Test(expected = NullPointerException.class)
    public void testRunProgram1() throws IOException, ParseException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        Options options = new Options();
        controller.runProgram(options);
    }

    // test if runProgram throws NullException if options param is null
    @Test(expected = NullPointerException.class)
    public void testRunProgram2() throws IOException, ParseException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        Options options = null;
        controller.runProgram(options);
    }

    // TODO: test validateEndToEndTestModels here!!!

    // tests if getTestFiles throws NullPointerException if testFilesDir param is null
    @Test(expected = NullPointerException.class)
    public void testGetTestFiles1() throws IOException, ParseException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        String testFilesDir = null;
        controller.getTestFiles(testFilesDir);
    }

    // tests if getTestFiles throws IllegalArgumentException if testFilesDir not the name of an actual directory
    @Test(expected = IllegalArgumentException.class)
    public void testGetTestFiles2() throws IOException, ParseException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        String testFilesDir = "test";
        controller.getTestFiles(testFilesDir);
    }

    // tests there are no png files in getTestFiles output
    @Test
    public void testGetTestFiles3() throws IOException, ParseException {
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        String[] args = {"test"};
        Options options = new Options(args, testFilesDir);
        Controller controller = new Controller(new Model(), new View(), new Options());
        TestFiles testFiles = controller.getTestFiles(options.getTestFilesDir());
        List kripkesInvalid = testFiles.getKripkesInvalid();
        List kripkesValid = testFiles.getKripkesValid();
        List models = testFiles.getModels();
        //
    }

    // tests getTestFiles is getting correct results
    @Test
    public void testGetTestFiles4() throws IOException, ParseException {
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        String[] args = {"-k","kripke.txt","-f","model.txt"};
        Options options = new Options(args, testFilesDir);
        Controller controller = new Controller(new Model(), new View(), options);
        TestFiles testFiles = controller.getTestFiles(options.getTestFilesDir());
        List kripkesInvalidActual = testFiles.getKripkesInvalid();
        List kripkesValidActual = testFiles.getKripkesValid();
        List modelsActual = testFiles.getModels();
        List kripkesInvalidExpected = Arrays.asList("Broken Model 1.txt", "Broken Model 2.txt", "Broken Model 3.txt", "Broken Model 4.txt", "Broken Model 5.txt", "Broken Model 6.txt", "Broken Model 7.txt", "Broken Model 8.txt");
        List kripkesValidExpected = Arrays.asList("Model 1.txt","Model 2.txt","Model 3.txt","Model 4.txt","Model 5.txt","Model 6.txt","Model 7.txt");
        List modelsExpected = Arrays.asList("Model 1 - Test Formulas.txt","Model 2 - Test Formulas.txt","Model 3 - Test Formulas.txt","Model 4 - Test Formulas.txt","Model 5 - Test Formulas.txt","Model 6 - Test Formulas.txt","Model 7 - Test Formulas.txt");
        assertEquals(kripkesInvalidActual, kripkesInvalidExpected);
        assertEquals(kripkesValidActual, kripkesValidExpected);
        assertEquals(modelsActual, modelsExpected);
    }

    // tests getKripkeFromFile throws NullPointerException when kripkeFile para is null
    @Test(expected = NullPointerException.class)
    public void testGetKripkeFromFile1() throws NullPointerException, IOException, ParseException {
        String[] args = {"-k","kripke.txt","-f","model.txt"};
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        Options options = new Options(args, testFilesDir);
        Controller controller = new Controller(new Model(), new View(), options);
        String nullStr = null;
        try {
            Kripke kripke = controller.getKripkeFromFile(nullStr, true);
        } catch (Error e) {
            System.err.println(e);
        }

    }

    // tests getKripkeFromFile gets correct Kripke info
    @Test
    public void testGetKripkeFromFile2() throws NullPointerException, IOException, ParseException {
        String[] args = {"-k","kripke.txt","-f","model.txt"};
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        Options options = new Options(args, testFilesDir);
        Controller controller = new Controller(new Model(), new View(), options);
        String modelFilename = "Model 1.txt";
        Kripke kripke = controller.getKripkeFromFile(modelFilename, true);
        String kripkeStr = kripke.toString();

    }

}
