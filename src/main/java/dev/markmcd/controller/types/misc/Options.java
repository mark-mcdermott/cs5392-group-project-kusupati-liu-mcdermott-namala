package dev.markmcd.controller.types.misc;

import dev.markmcd.controller.types.modelRelated.FormulaInputSource;

import java.io.IOException;

import static dev.markmcd.controller.types.modelRelated.FormulaInputSource.ARGUMENT;
import static dev.markmcd.controller.types.modelRelated.FormulaInputSource.FILE;

/**
 * Class with user set options. Some of these options get set at the top of Main. The args come into Main as command line arguments, which are parsed and processed here.
 */
public class Options {

    // options from command line arguments

    /**
     * {@link String} Filename of .txt file containing the kripke structure. Don't include the full path, just the filename. The file needs to be in the src/main/resources directory.
     */
    private String kripkeFilepath;

    /**
     * {@link String} name of the state to check, like "s0" or "s13". Constructor checks to ensure stateToCheckStr starts with lowercase "s" and next has an integer after that.
     */
    private String stateToCheckStr;

    /**
     * Simple {@link FormulaInputSource} enum value that's either FILE or ARGUMENT. Refers to whether user specified the -f or -a flag. FILE means the model is supplied in a textfile specified after the -f flag in the command line arguments. ARGUMENT means the model itself is hardcoded in the command line argument after the -a flag.
     */
    private FormulaInputSource formulaInputSource;
    private String formulaInputFilename;
    private String formula;

    /**
     * {@Boolean} specifying that the end to end tests should be run
     */
    Boolean runEndToEndTests;

    /**
     * {@Boolean} true for printing exceptions to console and not halting program, false for throwing exceptions which halt program
     */
    Boolean printExceptions;

    // user set options from the top of Main
    private String testFilesDir;

    /**
     * Empty constructor - for unit testing only
     */
    public Options() { }

    /**
     * Main constructor
     * @param args
     * @param testFilesDir
     * @throws IOException
     */
    public Options(String[] args, String testFilesDir) throws IOException {

        // set user set options
        this.testFilesDir = testFilesDir;

        // parse command line arguments and set the options found there
        Arguments arguments = parseArgs(args);
        String kripkeFileName = arguments.getKripkeFilename();
        // this.kripkeFilepath = (arguments.runEndToEndTests) ? testFilesDir + "/" + kripkeFileName : kripkeFileName;
        this.kripkeFilepath = kripkeFileName;
        this.stateToCheckStr = arguments.getStateToCheckStr();
        this.formulaInputSource = arguments.getFormulaInputSource();
        if (arguments.getModelFilename() != null) {
            this.formulaInputSource = arguments.getFormulaInputSource();
        }
        if (arguments.getModel() != null) {
            this.formula = arguments.getModel();
        }
        this.runEndToEndTests = arguments.runEndToEndTests;

    }

    /**
     *  Utility method which creates an Arguments object from the command line arguments and sets the Arguments object in the MVC model.
     *  For creating the Arguments object, this method just calls parseArgs and the heavy lifting involved is all in that method.
     *  This method was adapted with minor changes from http://journals.ecs.soton.ac.uk/java/tutorial/java/cmdLineArgs/parsing.html, accessed 10/1/21.
     *  The original code license for this method is included unaltered below.
     *
     *  Copyright (c) 1995-1997 Sun Microsystems, Inc. All Rights Reserved.
     *
     *  Permission to use, copy, modify, and distribute this software
     *  and its documentation for NON-COMMERCIAL purposes and without
     *  fee is hereby granted provided that this copyright notice
     *  appears in all copies. Please refer to the file "copyright.html"
     *  for further important copyright and licensing information.
     *
     *  SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
     *  THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
     *  TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
     *  PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
     *  ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
     *  DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
     *
     * @param args Two command line arguments are mandatory: -k <kripke file> specifying the kripke filename and then either -a <model> or -f <model filename>. There is an optional -s <state name> argument specifying a state to check.
     * @throws IOException
     */

    public static Arguments parseArgs(String[] args) throws IOException {

        int i = 0;
        String arg;

        String kripkeFilename = "";
        String stateToCheckStr = null;
        FormulaInputSource formulaInputSource = ARGUMENT;
        String formulaInputStr = "";
        Boolean runEndToEndTests = false;

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            // kripke file
            if (arg.equals("-k")) {
                if (i < args.length)
                    kripkeFilename = args[i++];
            }

            // state to check
            if (arg.equals("-s")) {
                if (i < args.length)
                    stateToCheckStr = args[i++];
            }

            // formula
            if (arg.equals("-a")) {
                if (i < args.length)
                    formulaInputSource = ARGUMENT;
                    formulaInputStr = args[i++];
            }

            // formula file
            if (arg.equals("-f")) {
                if (i < args.length)
                    formulaInputSource = FILE;
                    formulaInputStr = args[i++];
            }

            // end to end tests
            if (arg.equals("-e")) {
                runEndToEndTests = true;
            }

        }
        if (i != args.length)
            System.err.println("Usage: java -jar modelCheckingCTL -k <kripke file> [-s <state to check>] -af <formula> -e");
        else if (stateToCheckStr == null) {
            return new Arguments(kripkeFilename,formulaInputSource,formulaInputStr,runEndToEndTests);
        } else if (stateToCheckStr != null) {
            return new Arguments(kripkeFilename,stateToCheckStr,formulaInputSource,formulaInputStr,runEndToEndTests);
        }
        return null;
    }

    public String getStateToCheckStr() {
        return stateToCheckStr;
    }

    public String getKripkeFilepath() {
        return kripkeFilepath;
    }

    public FormulaInputSource getFormulaInputSource() {
        return formulaInputSource;
    }

    public String getFormula() {
        return formula;
    }

    public Boolean getRunEndToEndTests() {
        return runEndToEndTests;
    }

    public String getFormulaInputFilename() {
        return formulaInputFilename;
    }

    public String getTestFilesDir() {
        return testFilesDir;
    }
}
