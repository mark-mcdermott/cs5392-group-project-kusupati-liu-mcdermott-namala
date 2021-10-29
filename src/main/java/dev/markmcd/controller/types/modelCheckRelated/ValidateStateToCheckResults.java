package dev.markmcd.controller.types.modelCheckRelated;

public class ValidateStateToCheckResults {

    Boolean stateToCheckPass;
    String stateToCheck;

    public ValidateStateToCheckResults(Boolean stateToCheckPass, String stateToCheck) {
        this.stateToCheckPass = stateToCheckPass;
        this.stateToCheck = stateToCheck;
    }

    public Boolean getStateToCheckPass() {
        return stateToCheckPass;
    }

    public void setStateToCheckPass(Boolean stateToCheckPass) {
        this.stateToCheckPass = stateToCheckPass;
    }

    public String getStateToCheck() {
        return stateToCheck;
    }

    public void setStateToCheck(String stateToCheck) {
        this.stateToCheck = stateToCheck;
    }

}
