package dev.markmcd.controller.types.modelCheckRelated;

public class FormulaFileObj {
    String stateToTest;
    String formula;
    Boolean expected;

    public FormulaFileObj(String stateToTest, String formula, Boolean expected) {
        this.stateToTest = stateToTest;
        this.formula = formula;
        this.expected = expected;
    }

    public void setExpected(Boolean expected) {
        this.expected = expected;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setStateToTest(String stateToTest) {
        this.stateToTest = stateToTest;
    }

    public String getFormula() {
        return formula;
    }

    public Boolean getExpected() {
        return expected;
    }

    public String getStateToTest() {
        return stateToTest;
    }
}
