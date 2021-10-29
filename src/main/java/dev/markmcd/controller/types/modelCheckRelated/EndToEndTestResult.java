package dev.markmcd.controller.types.modelCheckRelated;

public class EndToEndTestResult {

    ModelCheckResults modelCheckResults;
    Boolean expectedResult;
    Boolean actualResult;
    Boolean testPass;
    String formula;
    String stateToTest;

    public EndToEndTestResult(ModelCheckResults modelCheckResults, Boolean expectedResult, Boolean actualResult, Boolean testPass, String formula, String stateToTest) {
        this.modelCheckResults = modelCheckResults;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.testPass = testPass;
        this.formula = formula;
        this.stateToTest = stateToTest;
    }

    public ModelCheckResults getModelCheckResults() {
        return modelCheckResults;
    }

    public void setModelCheckResults(ModelCheckResults modelCheckResults) {
        this.modelCheckResults = modelCheckResults;
    }

    public Boolean getActualResult() {
        return actualResult;
    }

    public void setActualResult(Boolean actualResult) {
        this.actualResult = actualResult;
    }

    public Boolean getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(Boolean expectedResult) {
        this.expectedResult = expectedResult;
    }

    public Boolean getTestPass() {
        return testPass;
    }

    public void setTestPass(Boolean testPass) {
        this.testPass = testPass;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getStateToTest() {
        return stateToTest;
    }

    public void setStateToTest(String stateToTest) {
        this.stateToTest = stateToTest;
    }

}
