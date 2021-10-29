package dev.markmcd.controller.types.modelCheckRelated;

import java.util.Set;

import static dev.markmcd.utils.Utils.containsStateName;

public class ModelCheckResults {

    Set statesThatHold;
    Set allStates;
    String stateToCheck;
    String formula;
    Boolean stateToCheckHold;

    public ModelCheckResults(Set statesThatHold, Set allStates, String stateToCheck, String formula) {
        this.statesThatHold = statesThatHold;
        this.allStates = allStates;
        this.stateToCheck = stateToCheck;
        this.formula = formula;

        if (statesThatHold != null && stateToCheck != null) {
            if (containsStateName(statesThatHold, stateToCheck)) {
                stateToCheckHold = true;
            } else {
                stateToCheckHold = false;
            }
        }

    }

    public String getStateToCheck() {
        return stateToCheck;
    }

    public void setStateToCheck(String stateToCheck) {
        this.stateToCheck = stateToCheck;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Set getAllStates() {
        return allStates;
    }

    public void setStatesThatHold(Set statesThatHold) {
        this.statesThatHold = statesThatHold;
    }

    public Set getStatesThatHold() {
        return statesThatHold;
    }

    public void setAllStates(Set allStates) {
        this.allStates = allStates;
    }

    public Boolean getStateToCheckHold() {
        return stateToCheckHold;
    }

    public void setStateToCheckHold(Boolean stateToCheckHold) {
        this.stateToCheckHold = stateToCheckHold;
    }

}
