package dev.markmcd.controller;

import dev.markmcd.controller.types.kripke.KripkeFileObj;
import dev.markmcd.controller.types.misc.TestFiles;
import dev.markmcd.model.Model;
import dev.markmcd.view.View;
import dev.markmcd.controller.types.misc.Options;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Controller.java
 */
public class ControllerTest {

    // test if Controller throws NullPointerException if model param is null
    @Test(expected = NullPointerException.class)
    public void testController1() throws Exception {
        Model model = null;
        Options options = new Options();
        View view = new View(options);
        new Controller(model,view,options);
    }

    // test if Controller throws NullPointerException if view param is null
    @Test(expected = NullPointerException.class)
    public void testController2() throws Exception {
        Model model = new Model();
        Options options = new Options();
        View view = null;
        new Controller(model,view,options);
    }

    // test if Controller throws NullPointerException if args param is null
    @Test(expected = NullPointerException.class)
    public void testController3() throws Exception {
        Model model = new Model();
        Options options = new Options();
        View view = new View(options);
        new Controller(model,view,options);
    }

    // test if runProgram throws NullException if args param is null
    @Test(expected = NullPointerException.class)
    public void testRunProgram1() throws Exception {
        Options options = new Options();
        Controller controller = new Controller(new Model(), new View(options), new Options());
        controller.runProgram(options);
    }

    // test if runProgram throws NullException if options param is null
    @Test(expected = NullPointerException.class)
    public void testRunProgram2() throws Exception {
        Options options = null;
        Controller controller = new Controller(new Model(), new View(options), new Options());
        controller.runProgram(options);
    }

    // TODO: test validateEndToEndTestModels here!!!

    // tests if getTestFiles throws NullPointerException if testFilesDir param is null
    @Test(expected = NullPointerException.class)
    public void testGetTestFiles1() throws Exception {
        Options options = new Options();
        Controller controller = new Controller(new Model(), new View(options), new Options());
        String testFilesDir = null;
        controller.getTestFiles(testFilesDir);
    }

    // tests if getTestFiles throws NullPointerException if testFilesDir not the name of an actual directory
    @Test(expected = NullPointerException.class)
    public void testGetTestFiles2() throws Exception {
        Options options = new Options();
        Controller controller = new Controller(new Model(), new View(options),options);
        String testFilesDir = "test";
        controller.getTestFiles(testFilesDir);
    }

    // tests getTestFiles is getting correct results
    @Test
    public void testGetTestFiles4() throws Exception {
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        String[] args = {"-k","kripke.txt","-f", "formula.txt"};
        Options options = new Options(args, testFilesDir,true);
        Model model = new Model();
        View view = new View(options);
        Controller controller = new Controller(model, view, options);
        TestFiles testFiles = controller.getTestFiles(options.getTestFilesDir());
        List kripkesInvalidActual = testFiles.getKripkesInvalid();
        List kripkesValidActual = testFiles.getKripkesValid();
        List modelsActual = testFiles.getFormulas();
        List kripkesInvalidExpected = Arrays.asList("Broken Model 1.txt", "Broken Model 2.txt", "Broken Model 3.txt", "Broken Model 4.txt", "Broken Model 5.txt", "Broken Model 6.txt", "Broken Model 7.txt", "Broken Model 8.txt");
        List kripkesValidExpected = Arrays.asList("Model 1.txt","Model 2.txt","Model 3.txt","Model 4.txt","Model 5.txt","Model 6.txt","Model 7.txt");
        List modelsExpected = Arrays.asList("Model 1 - Test Formulas.txt","Model 2 - Test Formulas.txt","Model 3 - Test Formulas.txt","Model 4 - Test Formulas.txt","Model 5 - Test Formulas.txt","Model 6 - Test Formulas.txt","Model 7 - Test Formulas.txt");
        assertEquals(kripkesInvalidActual, kripkesInvalidExpected);
        assertEquals(kripkesValidActual, kripkesValidExpected);
        assertEquals(modelsActual, modelsExpected);
    }

    // tests getKripkeFromFile throws NullPointerException when kripkeFile para is null
    @Test(expected = NullPointerException.class)
    public void testGetKripkeFromFile1() throws Exception {
        String[] args = {"-k","kripke.txt","-f", "formula.txt"};
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        Options options = new Options(args, testFilesDir,true);
        Controller controller = new Controller(new Model(), new View(options), options);
        String nullStr = null;
        try {
            KripkeFileObj kripkeFileObj = controller.getKripkeFileObj(nullStr);
        } catch (Error e) {
            System.err.println(e);
        }

    }

    // tests getKripkeFromFile gets correct Kripke info
    @Test
    public void testGetKripkeFromFile2() throws Exception {
        String[] args = {"-k","kripke.txt","-f", "formula.txt"};
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        Options options = new Options(args, testFilesDir,true);
        Controller controller = new Controller(new Model(), new View(options), options);
        String modelFilepath = testFilesDir + "/Model 1.txt";
        KripkeFileObj kripkeFileObj = controller.getKripkeFileObj(modelFilepath);
        String kripkeStr = kripkeFileObj.getKripke().toString();
    }

}
