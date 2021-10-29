package dev.markmcd.view;

import dev.markmcd.controller.types.kripke.Kripke;
import dev.markmcd.controller.types.kripke.State;
import dev.markmcd.controller.types.misc.Options;
import dev.markmcd.controller.types.modelCheckRelated.*;

import java.util.List;
import java.util.Set;

import static dev.markmcd.utils.Utils.containsStateName;
import static dev.markmcd.utils.Utils.handleError;

/**
 * Prints the output of the model checking to the terminal
 */
public class View {

    Options options;

    public View(Options options) {
        this.options = options;
    }

    public void updateView(ValidationResults validationResults, ModelCheckResults modelCheckResults) throws Exception {
        printValidationResults(validationResults);
        printModelCheckResults(modelCheckResults);
    }

    public void updateView(ValidationResults validationResults, ModelCheckResults modelCheckResults, AllEndToEndTestResults allEndToEndTestResults) throws Exception {
        printEndToEndTestResults(allEndToEndTestResults);
        printValidationResults(validationResults);
        printModelCheckResults(modelCheckResults);
    }

    public void printEndToEndTestResults(AllEndToEndTestResults allEndToEndTestResults) throws Exception {
        List validateModelResultsList = allEndToEndTestResults.getValidateModelResultsList();
        List validateFormulaResultsList = allEndToEndTestResults.getValidateFormulaResultList();
        List endToEndTestResultsList = allEndToEndTestResults.getEndToEndTestResultsList();

        for (Object validateModelResultObj : validateModelResultsList) {
            ValidateModelResults validateModelResults = (ValidateModelResults) validateModelResultObj;
            Boolean passValidation = validateModelResults.getPassValidation();
            String originalErrorMessage = validateModelResults.getOriginalErrorMessage();
            String testFile = validateModelResults.getKripkeFilepath();

            if (!passValidation) {
                originalErrorMessage = originalErrorMessage;
                String newErrorMessage = "❌ failed parsing - " + originalErrorMessage;
                handleError(newErrorMessage,options.getPrintExceptions());
            } else {
                System.out.println("✅ passed parsing - " + testFile);
            }
        }

        for (Object validateFormulaResultsObj : validateFormulaResultsList) {
            ValidateFormulaResults validateFormulaResults = (ValidateFormulaResults) validateFormulaResultsObj;
            String formula = validateFormulaResults.getFormula();
            String formulaFilename = validateFormulaResults.getFormulaFilename();
            System.out.println("✅ passed parsing - Formula \"" + formula + "\" is well formed (\"" + formulaFilename + "\")");
        }

        for (Object endToEndTestResultObj : endToEndTestResultsList) {
            EndToEndTestResult endToEndTestResult = (EndToEndTestResult) endToEndTestResultObj;
            Boolean actualResult = endToEndTestResult.getActualResult();
            Boolean expectedResult = endToEndTestResult.getExpectedResult();
            Boolean testPass = endToEndTestResult.getTestPass();
            String formula = endToEndTestResult.getFormula();
            String stateToTest = endToEndTestResult.getStateToTest();

            if (testPass) {
                if (actualResult) {
                    System.out.println("✅ passed model checking - " + formula + " holds for " + stateToTest);
                } else if (!actualResult) {
                    System.out.println("✅ passed model checking - " + formula + " does not hold for " + stateToTest);
                }
            } else {
                if (expectedResult) {
                    System.out.println("❌ failed model checking - " + formula + " should hold for " + stateToTest + " but did not.");
                } else {
                    System.out.println("❌ failed model checking - " + formula + " should not hold for " + stateToTest + " but did");
                }
            }

        }
        System.out.println("-- end to end tests done --\n");
    }

    public void printValidationResults(ValidationResults validationResults) throws Exception {
        ValidateModelResults validateModelResults = validationResults.getValidateModelResults();
        ValidateFormulaResults validateFormulaResults = validationResults.getValidateFormulaResults();
        ValidateStateToCheckResults validateStateToCheckResults = validationResults.getValidateStateToCheckResults();

        // model validation vars
        Boolean modelPassValidation = validateModelResults.getPassValidation();
        String modelFilepath = validateModelResults.getKripkeFilepath();
        String modelParsingErrorMessage = validateModelResults.getOriginalErrorMessage();

        // formula validation vars
        Boolean formulaPassValidation = validateFormulaResults.getPassValidation();
        String formula = validateFormulaResults.getFormula();

        // state to check validation vars
        Boolean stateToCheckPassValidation = validateStateToCheckResults.getStateToCheckPass();
        String stateToCheck = validateStateToCheckResults.getStateToCheck();

        // print model validation results
        if (modelPassValidation) {
            System.out.println("✅ model passed parsing - no syntax errors in " + modelFilepath);
        } else {
            String newErrorMessage = "❌ failed parsing - " + modelParsingErrorMessage;
            handleError(newErrorMessage,true);
        }

        // print formula validation results
        if (formulaPassValidation) {
            System.out.println("✅ formula passed parsing - \"" + formula + "\" is well formed");
        } else {
            // program halts with exception and detailed error message if formula isn't well formed
        }

        // print state to check validation results
        if (stateToCheckPassValidation) {
            System.out.println("✅ state to check " + stateToCheck + " is in the model");
        } else {
            System.out.println("❌ state to check " + stateToCheck + " is not in the model");
        }
    }

    public void printModelCheckResults(ModelCheckResults modelCheckResults) {
        Set statesThatHold = modelCheckResults.getStatesThatHold();
        Set allStates = modelCheckResults.getAllStates();
        String stateToCheck = modelCheckResults.getStateToCheck();
        String formula = modelCheckResults.getFormula();
        Boolean stateToCheckHold = modelCheckResults.getStateToCheckHold();
        if (stateToCheck != null) {
            if (stateToCheckHold) {
                System.out.println("✅ " + stateToCheck + " holds for " + formula);
            } else {
                System.out.println("❌ " + stateToCheck + " does not hold for " + formula);
            }
        } else {
            for (Object stateObj : allStates) {
                State state = (State) stateObj;
                String stateStr = state.toString();
                if (containsStateName(statesThatHold,stateStr)) {
                   System.out.println("✅ " + stateStr + " holds for " + formula);
                } else {
                    System.out.println("❌ " + stateStr + " does not hold for " + formula);
                }
            }
        }
    }

}
