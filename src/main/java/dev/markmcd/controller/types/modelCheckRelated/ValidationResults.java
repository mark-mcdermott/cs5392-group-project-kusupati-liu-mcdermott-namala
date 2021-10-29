package dev.markmcd.controller.types.modelCheckRelated;

public class ValidationResults {

    private ValidateModelResults validateModelResults;
    private ValidateFormulaResults validateFormulaResults;
    private ValidateStateToCheckResults validateStateToCheckResults;

    public ValidationResults(ValidateModelResults validateModelResults, ValidateFormulaResults validateFormulaResults, ValidateStateToCheckResults validateStateToCheckResults) {
        this.validateFormulaResults = validateFormulaResults;
        this.validateModelResults = validateModelResults;
        this.validateStateToCheckResults = validateStateToCheckResults;
    }

    public ValidateFormulaResults getValidateFormulaResults() {
        return validateFormulaResults;
    }

    public ValidateModelResults getValidateModelResults() {
        return validateModelResults;
    }

    public ValidateStateToCheckResults getValidateStateToCheckResults() {
        return validateStateToCheckResults;
    }

    public void setValidateFormulaResults(ValidateFormulaResults validateFormulaResults) {
        this.validateFormulaResults = validateFormulaResults;
    }

    public void setValidateModelResults(ValidateModelResults validateModelResults) {
        this.validateModelResults = validateModelResults;
    }

    public void setValidateStateToCheckResults(ValidateStateToCheckResults validateStateToCheckResults) {
        this.validateStateToCheckResults = validateStateToCheckResults;
    }
}
