package dev.markmcd.controller.types.modelCheckRelated;

/**
 * Data structure holding the results of mode/formula/state validation. Each of those is a custom data structure in a property on this class. Validation here means checking if there are no syntax errors (in models/formulas) or for the state to check, checking that it is in the model
 */
public class ValidationResults {

    /**
     * results of validating a model (checking it has no syntax errors)
     */
    private ValidateModelResults validateModelResults;

    /**
     * results of validating a formula (making sure it's well formed / has no syntax errors)
     */
    private ValidateFormulaResults validateFormulaResults;

    /**
     * results of checking if a state to check is in a model or not
     */
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
