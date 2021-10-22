package dev.markmcd.controller;

import dev.markmcd.controller.ctl.Validator.ParseException;
import dev.markmcd.controller.ctl.Validator.Validator;
import dev.markmcd.controller.types.kripke.KripkeFileObj;
import dev.markmcd.controller.types.misc.Options;
import dev.markmcd.controller.types.modelRelated.FormulaFileObj;
import dev.markmcd.controller.types.modelRelated.FormulaInputSource;
import dev.markmcd.controller.types.misc.TestFiles;
import dev.markmcd.controller.types.modelRelated.ModelCheckInputs;
import dev.markmcd.model.Model;
import dev.markmcd.controller.types.kripke.Kripke;
import dev.markmcd.controller.types.kripke.State;
import dev.markmcd.controller.types.kripke.Transition;
import dev.markmcd.view.View;
import dev.markmcd.controller.ctl.Parser.Parser;


import java.io.*;
import java.util.*;

import static dev.markmcd.controller.types.modelRelated.FormulaInputSource.ARGUMENT;
import static dev.markmcd.controller.types.modelRelated.FormulaInputSource.FILE;
import static dev.markmcd.utils.Utils.*;
import static dev.markmcd.utils.Utils.contains;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

/**
 * Reads input files, validates the model, runs the tests, and then runs the model checker
 */
public class Controller {

    /**
     * The model (MVC "model", not CTL "model" in this case) stores the kripke filename, the ctl model to test, the state to check, the command line arguments and the Kripke data
     */
    private Model model;

    /**
     * The view in this version is just a rudimentary command line output that says if the formula holds for the model and tries to give meaningful error messages if anything failed along the way.
     * Hoping to add Java Swing and Jung to this in a future version for a GUI and directed graph visualization.
     */
    private View view;

    /**
     * An {@link Options} object which has all options hard coded in Main as well as the user's command line arguments that were passed into Main
     */
    private Options options;

    /**
     * Two command line arguments are mandatory: -k <kripke file> specifying the kripke filename and then either -a <formula> or -f <formula filename>. There is an optional -s <state name> argument specifying a state to check.
     */
    private String[] args;

    /**
     * Simple {@link FormulaInputSource} enum value that's either FILE or ARGUMENT. Refers to whether user specified the -f or -a flag. FILE means the formula is supplied in a textfile specified after the -f flag in the command line arguments. ARGUMENT means the formula itself is hardcoded in the command line argument after the -a flag.
     */
    private FormulaInputSource formulaInputSource;

    /**
     * whether or not one wants to run the model checking tests in the resources/test-files/ directory
     */
    private Boolean runTests;

    /**
     * name of test file directory inside resources/
     */
    private String testFilesDir = "end-to-end-tests";


    /**
     * Kicks off the program after being called from Main
     * @param model The model (MVC "model", not CTL "model" in this case) stores the Kripke filename, the ctl model to test, the state to check, the command line arguments and the Kripke data
     * @param view The view in this version is just a rudimentary command line output that says if the state(s) hold for the model and tries to give meaningful error messages if anything failed along the way.
     * @param options Two command line arguments are mandatory: -k <kripke file> specifying the Kripke filename and then either -a <model> or -f <model filename>. There is an optional -s <state name> argument specifying a state to check.
     * @throws IOException
     */
    public Controller(Model model, View view, Options options) throws Exception {
        if (model == null || view == null || options == null) { throw new NullPointerException("A param to Controller constructor is null"); }
        this.model = model;
        this.view = view;
        runProgram(options);
    }

    /**
     * This is the meat and potatoes of the program - all the major function calls are here. Proecesses the arguments, runs tests, runs the model checking
     * @param options Two command line arguments are mandatory: -k <kripke file> specifying the Kripke filename and then either -a <formula> or -f <formula filename>. There is an optional -s <state name> argument specifying a state to check.
     * @throws IOException
     */
    public void runProgram(Options options) throws Exception {
        if (options == null) {
            throw new NullPointerException("runProgram options param is null");
        }

        if (options.getRunEndToEndTests()) {
            runEndToEndTests(options);
        }
        // runTests();
        // checkKripkeSyntax
        // checkModelSyntax
        // checkStateToCheck
        // checkModel(args, modelInputSource);
        // updateView();

    }


    /**
     * Goes through the files in the testFileDir inside the resources folder and returns a {@link TestFiles} object of the end to end tests, which is just an object with three array lists - kripkesValid, kripkesInvalid and models.
     * @param testFilesDir A {@link String} of the folder inside the resources folder that has all the end to end test files
     * @return a {@link TestFiles} object of the end to end tests, which is just an object with three array lists - kripkesValid, kripkesInvalid and models.
     * @throws IOException
     */
    public TestFiles getTestFiles(String testFilesDir) throws IOException {
        if (testFilesDir == null) { throw new NullPointerException("testFilesDir param is null in getTestFiles"); }
        List kripkesValid = new ArrayList();
        List kripkesInvalid = new ArrayList();
        List models = new ArrayList();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(testFilesDir);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            // read input stream line by line approach from https://stackoverflow.com/a/55420102, accessed 9/18/21
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String filename;
                while ((filename = reader.readLine()) != null) {
                    if (!filename.matches("(.*).png(.*)")) {
                        if (filename.matches("(.*)Broken(.*)")) {
                            kripkesInvalid.add(filename);
                        } else if (filename.matches("(.*)Formulas(.*)")) {
                            models.add(filename);
                        } else if (filename.matches("(.*)Model(.*)")) {
                            kripkesValid.add(filename);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        inputStream.close();
        return new TestFiles(kripkesValid, kripkesInvalid, models);
    }

    /**
     * Checks the CTL models in the end to end tests to see if they're well formed.
     * @param testFilesDir A {@link String} of the folder inside the resources folder that has all the end to end test files
     * @throws IOException
     */
    public void validateEndToEndTestModels(String testFilesDir, Boolean printExceptions) throws Exception {
        TestFiles testFiles = getTestFiles(testFilesDir);
        String testFormula = "p";

        for (Object testFilesObj : testFiles.getKripkesValid()) {
            String testFile = (String) testFilesObj;
            KripkeFileObj kripkeFileObj = getKripkeFileObj(testFile);
            if (kripkeFileObj.getErrorMessage() != null) {
                String origErrorMessage = kripkeFileObj.getErrorMessage();
                String newErrorMessage = "❌ failed parsing - " + origErrorMessage;
                handleError(newErrorMessage,printExceptions);
            } else {
                System.out.println("✅ passed parsing - " + testFile);
            }
            ModelCheckInputs modelCheckInputs = new ModelCheckInputs(kripkeFileObj.getKripke(), testFormula);
            Parser parser = new Parser(modelCheckInputs);
            parser.Parse();
        }

        for (Object testFilesObj : testFiles.getKripkesInvalid()) {
            String testFile = (String) testFilesObj;
            // System.out.println(testFile);
            KripkeFileObj kripkeFileObj = getKripkeFileObj(testFile);
            if (kripkeFileObj.getErrorMessage() != null) {
                String origErrorMessage = kripkeFileObj.getErrorMessage();
                String newErrorMessage = "❌ failed parsing - " + origErrorMessage;
                handleError(newErrorMessage,printExceptions);
            } else {
                System.out.println("✅ passed parsing - " + testFile);
            }
            ModelCheckInputs modelCheckInputs = new ModelCheckInputs(kripkeFileObj.getKripke(), testFormula);
            Parser parser = new Parser(modelCheckInputs);
            parser.Parse();
        }

    }

    /**
     * Parses a text file containing information for a specific Kripke structure and returns a {@link Kripke} with all that info.
     * Kripke text file must be in a format like this:
     * s1, s2, s3, s4;
     * t1 : s1 - s2, (transition t1 is from state s1 to state s2)
     * t2 : s1 - s3,
     * t3 : s3 – s4,
     * t4 : s4 – s2,
     * t5 : s2 – s3;
     * s1 : p q, (propositional atom names are separated by a space; a name consists of letters, it is casesensitive)
     * s2 : q t r,
     * s3 : , (i.e. set of propositional atoms for state s3 is empty)
     * s4 : t;
     * @param kripkeFile {@link String} filename of a kripke text file
     * @return A {@link Kripke} object
     * @throws IOException
     */
    public KripkeFileObj getKripkeFileObj(String kripkeFile) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(testFilesDir + "/" + kripkeFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        Set states = new HashSet<State>();
        Set transitions  = new HashSet<Transition>();
        Boolean parsedKripkeLabelsLine = false;
        KripkeFileObj kripkeFileObj = new KripkeFileObj();
        kripkeFileObj.setKripkeFilename(kripkeFile);
        kripkeFileObj.setStates(states);
        kripkeFileObj.setTransitions(transitions);
        kripkeFileObj.setLineNum(1);
        while (reader.ready()) {
            String line = reader.readLine();
            line = line.trim();
            char firstChar = line.charAt(0);

            // first line should always be state line
            if (kripkeFileObj.getLineNum() == 1) {
                line = removeByteOrderMark(line);
                kripkeFileObj = parseKripkeStates(kripkeFileObj, line); }
            // after first line, if it starts with a "t", it's a transition line
            else if (firstChar == 't') {
                // kripkeFileObj.setLineNum(krip);
                if (kripkeFileObj.getKripkeFilename().equals("Broken Model 4.txt")) {
                    int i=0;
                }
                if (kripkeFileObj.getErrorMessage() == null) {
                    kripkeFileObj = parseKripkeTransitionLine(kripkeFileObj, line);
                }
            }
            // and if it starts with an s it's a labels line
            else if (firstChar == 's') {
                kripkeFileObj = parseKripkeLabelsLine(kripkeFileObj, line);
                parsedKripkeLabelsLine = true;
            }
            int curLineNum = kripkeFileObj.getLineNum();
            curLineNum++;
            kripkeFileObj.setLineNum(curLineNum);
        }
        Kripke kripke = new Kripke(kripkeFileObj.getStates(), kripkeFileObj.getTransitions());
        if (parsedKripkeLabelsLine == false) {
            kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": no labels line found (a label line example could be: \"s1 : p;\").");
        }
        kripkeFileObj.setKripke(kripke);
        return kripkeFileObj;
    }

    /**
     * Gets a {@link Set} of labels from a line in the Kripke text file. Return is void void because it's modifying an existing state (adding labels to it), which is why all the states are passed as a param. The line must be in one the following type of formats:
     * s1 : p q, (propositional atom names are separated by a space; a name consists of letters, it is casesensitive)
     * s2 : q t r,
     * s3 : , (i.e. set of propositional atoms for state s3 is empty)
     * s4 : t; (will end in a comma if not the last line, or a semicolon if it is the last line)
     * @param line {@link String} a labels line from a Kripke text file. Must be in a format like "s2 : q t r,". Case sensitive and spaces matter. Can end in a comma or semicolon.
     * @param kripkeFileObj
     * @throws IOException
     */
    private static KripkeFileObj parseKripkeLabelsLine(KripkeFileObj kripkeFileObj, String line) throws IOException {
        String[] lineArr = line.split(" ",0);
        String stateName = lineArr[0];
        stateName = stateName.replace(",","");
        Integer stateNum = parseInt(stateName.replace("s",""));
        if (!contains(kripkeFileObj.getStates(),new State(stateNum))) { kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": state \"" + stateName + "\" not found in kripke states."); }
        // char secondChar = line.charAt(1);
        // Integer stateNum = Character.getNumericValue(secondChar);
        Set labels = new HashSet<Character>();
        // line = line.substring(5); // remove everything in the front (ie, "s1 : ") so only the labels are left (ie, "q t r,")
        // line = line.substring(0, line.length() - 1); // remove trailing comma or semicolon
        lineArr[lineArr.length - 1].replace(",","");
        lineArr[lineArr.length - 1].replace(";","");
        int lineArrElemNum = 0;
        for (Object lineArrElemObj : lineArr) {
            String lineArrElem = (String) lineArrElemObj;
            if (lineArrElemNum != 0 && lineArrElemNum != 1) { // skip state name and colon and start at labels
                // Character label = lineArrElem.charAt(0);
                String label = lineArrElem;
                label = label.replace(",","");
                label = label.replace(";","");
                if (labels.contains(label)) { kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": label \"" + label + "\" already exists in state \"" + stateName + "\"."); }
                labels.add(label);
//                if (line.length() > 2) {
//                    line = line.substring(2); // remove first char and the space after it
//                } else {
//                    line = "";
//                }
            }
            lineArrElemNum++;
        }
        if (contains(kripkeFileObj.getStates(),new State(stateNum))) {
            getState(stateNum, kripkeFileObj.getStates()).setLabels(labels);
        }
        return kripkeFileObj;
    }

    /**
     * Gets all the states in a Kripke from the text file of the Kripke. The line must be in a format like this: "s1, s2, s3, s4;" where the states are separated by a comma and a space and the last state is followed by a semicolon.
     * @param line {@link String} line from a Kripke text file (the first line). The line must be in a format like this: "s1, s2, s3, s4;" where the states are separated by a comma and a space and the last state is followed by a semicolon.
     * @return A {@link Set} representing all the {@link State}s specified in the line.
     */
    private static KripkeFileObj parseKripkeStates(KripkeFileObj kripkeFileObj, String line) throws IOException {
        Set states = new HashSet<State>();
        line = line.trim();
        String[] stateStrings = line.split(",",0);
        for (Object stateObj : stateStrings) {
            String stateStr = (String) stateObj;
            stateStr = stateStr.trim();
            stateStr = stateStr.replace(",","");
            stateStr = stateStr.replace(";","");
            stateStr = stateStr.replace("s","");
            Integer stateInt = parseInt(stateStr);
            State newState = new State(stateInt);
            if (contains(states, newState)) {
                kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line 1: duplicate state \"" + newState.toString() + "\" found.");
            }
            states.add(newState);
        }
        kripkeFileObj.setStates(states);
       return kripkeFileObj;
    }

    /**
     * Gets a single transition from one line of the Kripke text file. The line must be in the format like "t4 : s4 – s2,", which is the transition name and the from and to states of the transition respectively. The spaces matter and the transition line must end in a comma or semicolon.
     * The from and to states are actual references to the states in the Kripe. Ie, they have the same memory address and are not a copy. For a text output that shouldn't matter, but for a graphical output rendering the directed graph, that will be necessary.
     * @param line One {@link String} line of the Kripke text file. Any line that starts with "t" is a transition. The line must be in the format like "t4 : s4 – s2,", which is the transition name and the from and to states of the transition respectively. The spaces matter and the transition line must end in a comma or semicolon.
     * @return A {@link Transition} of the transition name and its from and to states.
     */
    private static KripkeFileObj parseKripkeTransitionLine(KripkeFileObj kripkeFileObj, String line) throws IOException {
        String[] transitionLineArr = line.split(" ",0); // ie, ["t1",":","s1","-","s2,"]
        String transitionName = transitionLineArr[0];
        if (transitionLineArr.length > 1) {
            if (!transitionLineArr[1].equals(":")) {
                kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": no colon found in transition line (a correct transition line example could be: \"t1 : s1 – s2;\").");
            }
        }
        if (kripkeFileObj.getErrorMessage() == null) {
            String fromName = transitionLineArr[2];
            if (transitionLineArr.length < 4) {
                kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": no destination state found in transition line (a correct transition line example could be: \"t1 : s1 – s2;\").");
            }
            if (kripkeFileObj.getErrorMessage() == null) {
                String toName = transitionLineArr[4];
                toName = toName.replace(",", "");
                toName = toName.replace(";", "");
                Integer transitionNum = parseInt(transitionName.replace("t", ""));
                Integer fromNum = parseInt(fromName.replace("s", ""));
                Integer toNum = parseInt(toName.replace("s", ""));
                State fromState = new State(fromNum);
                if (!contains(kripkeFileObj.getStates(),fromState)) { kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": transition from state (\"" + fromName + "\") not found in kripke states."); }
                State toState = new State(toNum);
                if (!contains(kripkeFileObj.getStates(),toState)) { kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": transition to state (\"" + toName + "\") not found in kripke states."); }
                Transition newTransition = new Transition(transitionNum, fromState, toState);
                for (int i = 1; i <= kripkeFileObj.getTransitions().size(); i++) {
                    Transition thisTransition = getTransition(i, kripkeFileObj.getTransitions());
                    if (thisTransition.getFrom().getNumber() == newTransition.getFrom().getNumber()) {
                        if (thisTransition.getTo().getNumber() == newTransition.getTo().getNumber()) {
                            kripkeFileObj.setErrorMessage(kripkeFileObj.getKripkeFilename() + ": Syntax error on line " + kripkeFileObj.getLineNum() + ": duplicate transition found (\"" + thisTransition.toStringDetailed() + "\" and \"" + newTransition.toStringDetailed() + "\").");
                        }
                    }
                }
                fromState = getState(fromNum,kripkeFileObj.getStates());
                fromState.addTransition(newTransition);
                kripkeFileObj.getTransitions().add(newTransition);
            }
        }
        return kripkeFileObj;
    }

    private void runEndToEndTests(Options options) throws Exception {
        validateEndToEndTestModels(options.getTestFilesDir(), true);
        validateEndToEndFormulas(options);
        modelCheckEndToEndTests(options);
           // run dr. p test files and check results are correct
    }

    public List getFormulaFileObjList(String formulasFilename, Options options) throws IOException {
        List formulaFileObjs = new ArrayList();
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(options.getTestFilesDir() + "/" + formulasFilename)) {
            String formula = "";
            String stateToCheck = "";
            Boolean expected = null;
            if (inputStream == null) {
                throw new IllegalArgumentException("file not found! " + formulasFilename);
            } else {
                // read input stream line by line approach from https://stackoverflow.com/a/55420102, accessed 9/18/21
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    // String rawLine = reader.readLine();
                    String rawLine = "";
                    while ((rawLine = reader.readLine()) != null) {
                        String rawLineForStateToCheck = rawLine;
                        String[] lineArr = rawLineForStateToCheck.split(";",0);
                        stateToCheck = lineArr[0].trim();
                        stateToCheck = stateToCheck.replaceAll("\uFEFF", "");
                        formula = lineArr[1];
                        expected = parseBoolean(lineArr[2]);
                        FormulaFileObj formulaFileObj = new FormulaFileObj(stateToCheck, formula, expected);
                        formulaFileObjs.add(formulaFileObj);
                    }
                }
            }
            return formulaFileObjs;
        }
    }

    private void modelCheckEndToEndTests(Options options) throws IOException, dev.markmcd.controller.ctl.Parser.ParseException {
        TestFiles testFilesObj = getTestFiles(testFilesDir);
        List kripkeFiles = testFilesObj.getKripkesValid();
        List formulaFiles = testFilesObj.getFormulas();

        // for (int i=0; i<kripkeFiles.size(); i++) {
            Object kripkeFilenameObj = kripkeFiles.get(0);
            // Object kripkeFilenameObj = kripkeFiles.get(i);
            String kripkeFilename = (String) kripkeFilenameObj;
            // Object formulaFilenameObj = formulaFiles.get(i);
            Object formulaFilenameObj = formulaFiles.get(0);
            String formulaFilename = (String) formulaFilenameObj;
            List formulaFileObjList = getFormulaFileObjList(formulaFilename, options);
            int numToTest = 1;
            int numTested = 0;
            // for (Object formulaFileObjObj : formulaFileObjList) {
            while (numTested < numToTest) {
                KripkeFileObj kripkeFileObj = getKripkeFileObj(kripkeFilename);
                // FormulaFileObj formulaFileObj = (FormulaFileObj) formulaFileObjObj;
                FormulaFileObj formulaFileObj = (FormulaFileObj) formulaFileObjList.get(numTested);
                ModelCheckInputs modelCheckInputs = new ModelCheckInputs(kripkeFileObj.getKripke(), formulaFileObj.getFormula(), formulaFileObj.getStateToTest());
                Parser parser = new Parser(modelCheckInputs);
                Set statesThatHold = parser.Parse();
                Boolean actual = null;
                if (containsStateName(statesThatHold,formulaFileObj.getStateToTest())) {
                    actual = true;
                } else {
                    actual = false;
                }
                if (actual == formulaFileObj.getExpected()) {
                    if (actual) {
                        System.out.println("✅ passed model checking - " + formulaFileObj.getFormula() + " holds for " + formulaFileObj.getStateToTest());
                    } else if (!actual) {
                        System.out.println("✅ passed model checking - " + formulaFileObj.getFormula() + " does not hold for " + formulaFileObj.getStateToTest());
                    }
                } else {
                    if (formulaFileObj.getExpected()) {
                        System.out.println("❌ failed model checking - " + formulaFileObj.getFormula() + " should hold hold for " + formulaFileObj.getStateToTest() + " but did not.");
                    } else {
                        System.out.println("❌ failed model checking - " + formulaFileObj.getFormula() + " should hold not hold for " + formulaFileObj.getStateToTest() + " but did not.");
                    }
                }
                numTested++;
            }
        // }





    }

    private void validateEndToEndFormulas(Options options) throws IOException, ParseException {
        TestFiles testFilesObj = getTestFiles(testFilesDir);
        for (Object formulasFileObj : testFilesObj.getFormulas()) {
            String formulasFilename = (String) formulasFileObj;
            ClassLoader classLoader = getClass().getClassLoader();
            List passedFormulas = new ArrayList();

            try (InputStream inputStream = classLoader.getResourceAsStream(options.getTestFilesDir() + "/" + formulasFilename)) {
                String ctlFormula = "";
                if (inputStream == null) {
                    throw new IllegalArgumentException("file not found! " + formulasFilename);
                } else {
                    // read input stream line by line approach from https://stackoverflow.com/a/55420102, accessed 9/18/21
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        String rawLine = reader.readLine();
                        while ((rawLine = reader.readLine()) != null) {
                            // numTests++;
                            rawLine = rawLine.replaceAll("s\\d+;", "");
                            rawLine = rawLine.replaceAll(";True", "");
                            ctlFormula = rawLine.replaceAll(";False", "");
                            ctlFormula = ctlFormula.replaceAll("\\ufeff", "");
                            if (!passedFormulas.contains(ctlFormula)) {
                                InputStream stringStream = new ByteArrayInputStream(ctlFormula.getBytes("UTF-8"));
                                Validator validator = new Validator(stringStream);
                                System.out.println("✅ passed parsing - Formula \"" + ctlFormula + "\" is well formed (\"" + formulasFilename + "\")");
                                validator.Validate();
                                passedFormulas.add(ctlFormula);
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void validateFormula(String formula, FormulaInputSource formulaInputSource) throws IOException, ParseException {

        // use this to use 2nd argument as filename where formula to test is located
        if (formulaInputSource == FILE) {
            String ctlFormula = parseCtlFormulaFile(formula);
            InputStream inputStream = new ByteArrayInputStream(ctlFormula.getBytes("UTF-8"));
            Validator validator = new Validator(inputStream);
            validator.Validate();
            System.out.println("✅ Formula " + ctlFormula + " is well formed (" + formula + ")");

        // use this to use 2nd argument as formula string to test
        } else if (formulaInputSource == ARGUMENT) {
            String ctlFormula = formula;
            InputStream stringStream = new ByteArrayInputStream(ctlFormula.getBytes("UTF-8"));
            Validator validator = new Validator(stringStream);
            validator.Validate();
            System.out.println("✅ Formula " + ctlFormula + " is well formed (command line argument)");
        }

    }

    public String parseCtlFormulaFile(String filename) throws IOException {

           // read resource file from class loader approach from https://mkyong.com/java/java-read-a-file-from-resources-folder/, accessed 9/18/21
           ClassLoader classLoader = getClass().getClassLoader();
           InputStream inputStream = classLoader.getResourceAsStream(filename);
           String ctlFormula = "";
           if (inputStream == null) {
               throw new IllegalArgumentException("file not found! " + filename);
           } else {
               // read input stream line by line approach from https://stackoverflow.com/a/55420102, accessed 9/18/21
               try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                   ctlFormula = reader.readLine();
               }
           }
           return ctlFormula;
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
