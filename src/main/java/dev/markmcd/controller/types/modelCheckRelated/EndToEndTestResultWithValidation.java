package dev.markmcd.controller.types.modelCheckRelated;

import java.util.List;

public class EndToEndTestResultWithValidation {
    ValidateModelResults validateModelResults;
    ValidateFormulaResults validateFormulaResults;
    List endToEndTestResult;

    public EndToEndTestResultWithValidation(ValidateModelResults validateModelResults, ValidateFormulaResults validateFormulaResults, List endToEndTestResult) {
        this.validateModelResults = validateModelResults;
        this.validateFormulaResults = validateFormulaResults;
        this.endToEndTestResult = endToEndTestResult;
    }

    public ValidateModelResults getValidateModelResults() {
        return validateModelResults;
    }

    public ValidateFormulaResults getValidateFormulaResults() {
        return validateFormulaResults;
    }

    public List getEndToEndTestResult() {
        return endToEndTestResult;
    }

}
