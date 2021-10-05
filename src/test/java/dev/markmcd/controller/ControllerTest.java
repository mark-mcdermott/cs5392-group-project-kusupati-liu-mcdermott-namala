package dev.markmcd.controller;

import dev.markmcd.controller.types.misc.TestFiles;
import dev.markmcd.model.Model;
import dev.markmcd.view.View;
import dev.markmcd.controller.Controller;
import dev.markmcd.controller.types.misc.Options;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ControllerTest {

    // test if Controller throws NullPointerException if model param is null
    @Test(expected = NullPointerException.class)
    public void testController1() throws IOException {
        Model model = null;
        View view = new View();
        Options options = new Options();
        new Controller(model,view,options);
    }

    // test if Controller throws NullPointerException if view param is null
    @Test(expected = NullPointerException.class)
    public void testController2() throws IOException {
        Model model = new Model();
        View view = null;
        Options options = new Options();
        new Controller(model,view,options);
    }

    // test if Controller throws NullPointerException if args param is null
    @Test(expected = NullPointerException.class)
    public void testController3() throws IOException {
        Model model = new Model();
        View view = new View();
        Options options = new Options();
        new Controller(model,view,options);
    }

    // test if runProgram throws NullException if args param is null
    @Test(expected = NullPointerException.class)
    public void testRunProgram1() throws IOException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        String[] args = null;
        Options options = new Options();
        controller.runProgram(args,options);
    }

    // test if runProgram throws NullException if options param is null
    @Test(expected = NullPointerException.class)
    public void testRunProgram2() throws IOException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        String[] args = {"test"};
        Options options = null;
        controller.runProgram(args,options);
    }

    // tests if getTestFiles throws NullPointerException if testFilesDir param is null
    @Test(expected = NullPointerException.class)
    public void testGetTestFiles1() throws IOException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        String testFilesDir = null;
        controller.getTestFiles(testFilesDir);
    }

    // tests if getTestFiles throws IllegalArgumentException if testFilesDir not the name of an actual directory
    @Test(expected = IllegalArgumentException.class)
    public void testGetTestFiles2() throws IOException {
        Controller controller = new Controller(new Model(), new View(), new Options());
        String testFilesDir = "test";
        controller.getTestFiles(testFilesDir);
    }

    // tests there are no png files in getTestFiles output
    @Test
    public void testGetTestFiles3() throws IOException {
        String testFilesDir = "end-to-end-tests";
        Boolean runEndToEndTests = true;
        String[] args = {"test"};
        Options options = new Options(args, testFilesDir, runEndToEndTests);
        Controller controller = new Controller(new Model(), new View(), new Options());
        TestFiles testFiles = controller.getTestFiles(options.getTestFilesDir());
        List kripkesInvalid = testFiles.getKripkesInvalid();
        List kripkesValid = testFiles.getKripkesValid();
        List models = testFiles.getModels();


    }


}
