package dev.markmcd.controller.types.kripke;

import java.util.Set;

public class KripkeFileObj {

    String kripkeFilename;
    Kripke kripke;
    Set states;
    Set transitions;
    String errorMessage;
    int lineNum;

    public void setKripke(Kripke kripke) {
        this.kripke = kripke;
    }

    public void setTransitions(Set transitions) {
        this.transitions = transitions;
    }

    public void setStates(Set states) {
        this.states = states;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Kripke getKripke() {
        return kripke;
    }

    public Set getTransitions() {
        return transitions;
    }

    public Set getStates() {
        return states;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getKripkeFilename() {
        return kripkeFilename;
    }

    public void setKripkeFilename(String kripkeFilename) {
        this.kripkeFilename = kripkeFilename;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

}
