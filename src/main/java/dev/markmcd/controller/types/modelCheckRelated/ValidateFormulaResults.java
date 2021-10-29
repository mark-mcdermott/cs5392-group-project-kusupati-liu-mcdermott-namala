package dev.markmcd.controller.types.modelCheckRelated;

/**
 * Data structure containing bookkeeping info on the results of validating a CTL formula (checking whether it is well formed. That is, checking whether it has any syntax errors)
 * This class is odd because as currently implemented, if the fomula validation fails, the program throws and exception and halts. So this class is only instantiated when validation succeeds, so the error property here will always be null. I  
 */
public class ValidateFormulaResults {

    Boolean passValidation;
    String formula;
    String error;
    String formulaFilename;

    public ValidateFormulaResults(Boolean passValidation, String formula, String error, String formulaFilename) {
        this.passValidation = passValidation;
        this.formula = formula;
        this.error = error;
        this.formulaFilename = formulaFilename;
    }

    public ValidateFormulaResults(Boolean passValidation, String formula, String error) {
        this.passValidation = passValidation;
        this.formula = formula;
        this.error = error;
    }

    public Boolean getPassValidation() {
        return passValidation;
    }

    public String getFormula() {
        return formula;
    }

    public String getError() {
        return error;
    }

    public void setPassValidation(Boolean passValidation) {
        this.passValidation = passValidation;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFormulaFilename() {
        return formulaFilename;
    }
}
