package dev.markmcd.model;

import dev.markmcd.controller.Arguments;
import dev.markmcd.model.kripke.Kripke;
import dev.markmcd.model.kripke.State;
import dev.markmcd.model.kripke.Transition;

import java.io.IOException;

/**
 * Holds model checking info - the kripke, the kripke file, the model and the state to check.
 * The setters here all check for null parameters and throw a NullPointerException if found
 */
public class Model {

    /**
     * {@link String} of the name of the file (ie, "kripke-file.txt") providing the Kripke content details.
     * Ends in ".txt" in this implementation.
     */
    private String kripkeFile;

    /**
     * Well formed CTL model. Ie, "EXp", "AG(AF(p and q))", etc. Nested operators in nested operators are fine here, infinite up to the limits of hardware memory, probably. This is run through the ctlValidator to ensure its well formed and an IOException is thrown if it's not.
     * Be mindful of the ambiguity between the "model" in model-view-controller and "model" in CTL model checking - here "model" means the CTL model in model checking.
     */
    private String model;

    /**
     * {@link State} to see if it holds on the model for specified proerties. If omitted, all states are checked. This will be null until a {@link State} is supplied
     */
    private State stateToCheck;

    /**
     * arguments
     */
    private Arguments arguments;


    private Kripke kripke;

    public String getKripkeFile() {
        return kripkeFile;
    }

    public void setKripkeFile(String kripkeFile) {
        if (kripkeFile == null) { throw new NullPointerException("kripeFile param is null in setKripkeFile in Model"); }
        this.kripkeFile = kripkeFile;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model == null) { throw new NullPointerException("model param is null in setModel in Model"); }
        this.model = model;
    }

    public State getStateToCheck() {
        return stateToCheck;
    }

    public void setStateToCheck(State stateToCheck) {
        if (stateToCheck == null) { throw new NullPointerException("stateToCheck param is null in setStateToCheck in Model"); }
        this.stateToCheck = stateToCheck;
    }

    public Kripke getKripke() {
        return kripke;
    }

    public void setKripke(Kripke kripke) throws IOException {
        if (kripke == null) { throw new NullPointerException("kripke param is null in setKripke in Model"); }
        kripke.checkKripkeForNulls(); // check kripke for null states, transitions or labels and throws exception if found
        this.kripke = kripke;
    }

    public Arguments getArguments() {
        return arguments;
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }

}
