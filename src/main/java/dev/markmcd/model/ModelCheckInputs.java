package dev.markmcd.model;

import dev.markmcd.model.kripke.Kripke;
import dev.markmcd.model.kripke.State;
import dev.markmcd.model.kripke.Transition;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * The ModelCheckInputs class has four private properties - the first three are inputs to this class: Kripke, the state to check and the model. The fourth, the modelStream, is generated within the constructor.
 * The {@link Kripke} must be fully populated with all necessary states, transitions and optionally (depending on the Kripke to model) labels. Null or empty states and transitions will throw a NullPointerException.
 * The stateToCheck is an optional {@link State} one wishes to see if it holds for specified properties. If omitted, all states are checked.
 * The model {@link String} is a well formed CTL model. Ie, "EXp", "AG(AF(p and q))", etc. Nested operators in nested operators are fine here, infinite up to the limits of hardware memory, probably.
 * The modelStream is an {@link InputStream} created from the model {@link String} in the class constructor. This is implementation specific input for the <a href="https://javacc.github.io/javacc/">JavaCC</a> compiler used.
 */
public class ModelCheckInputs {

    /**
     * The {@link Kripke} must be fully populated with {@link State}s and {@link Transition}s when passed as an argument. Any labels must be already attached to the states. Some error checking is done here to avoid null values, incorrect transitions (ie a state may only have transitions that start at that state), etc
     */
    private Kripke kripke;

    /**
     * Optional {@link State} to see if it holds on the model for specified proerties. If omitted, all states are checked. This will be null until a {@link State} is supplied
     */
    private State stateToCheck;

    /**
     * A well formed CTL model. Ie, "EXp", "AG(AF(p and q))", etc. Nested operators in nested operators are fine here, infinite up to the limits of hardware memory, probably. This is run through the ctlValidator to ensure its well formed and an IOException is thrown if it's not.
     */
    private String model;

    /**
     * {@link InputStream} created from the model {@link String} in the class constructor. This is implementation specific input for the <a href="https://javacc.github.io/javacc/">JavaCC</a> compiler used.
     */
    private InputStream modelStream;

    /**
     * Constructor specifying kripke, model and state to check. None can be null. If omitting state to check, use the constructor with just kripke and model. The model stream is created from the model here.
     * @param kripke must be fully populated with {@link State}s and {@link Transition}s when passed as an argument. Any labels must be already attached to the states. Some error checking is done here to avoid null values, incorrect transitions (ie a state may only have transitions that start at that state), etc
     * @param model well formed CTL model. Ie, "EXp", "AG(AF(p and q))", etc. Nested operators in nested operators are fine here, infinite up to the limits of hardware memory, probably. This is run through the ctlValidator to ensure its well formed and an IOException is thrown if it's not.
     * @param stateToCheck {@link State} to see if it holds on the model for specified proerties. If omitted, all states are checked. This will be null until a {@link State} is supplied
     * @throws UnsupportedEncodingException
     */
    public ModelCheckInputs(Kripke kripke, String model, State stateToCheck) throws IOException {

        if (kripke == null) { throw new NullPointerException("kripke is null in ModelCheckInputs call"); }
        kripke.checkKripkeForNulls(); // check kripke for null states, transitions or labels and throws exception if found
        if (model == null) { throw new NullPointerException("model is null in ModelCheckInputs call"); }
        if (stateToCheck == null) { throw new NullPointerException("stateToCheck is null in ModelCheckInputs call"); }

        // set properties
        this.kripke = kripke;
        this.model = model;
        this.stateToCheck = stateToCheck;
        this.modelStream = new ByteArrayInputStream(model.getBytes("UTF-8"));
    }

    /**
     * The constructor to use when omitting the optional state to check. Since state to check is omitted, all states will be checked. This constructor only specifies the Kripke and the model and state to check and neither can be null. The model stream is created from the model here.
     * @param kripke must be fully populated with {@link State}s and {@link Transition}s when passed as an argument. Any labels must be already attached to the states. Some error checking is done here to avoid null values, incorrect transitions (ie a state may only have transitions that start at that state), etc
     * @param model well formed CTL model. Ie, "EXp", "AG(AF(p and q))", etc. Nested operators in nested operators are fine here, infinite up to the limits of hardware memory, probably. This is run through the ctlValidator to ensure its well formed and an IOException is thrown if it's not.
     * @throws UnsupportedEncodingException
     */
    public ModelCheckInputs(Kripke kripke, String model) throws IOException {

        if (kripke == null) { throw new NullPointerException("kripke is null in ModelCheckInputs call"); }
        kripke.checkKripkeForNulls(); // check kripke for null states, transitions or labels and throws exception if found
        if (model == null) { throw new NullPointerException("model is null in ModelCheckInputs call"); }

        // set properties
        this.kripke = kripke;
        this.model = model;
        this.modelStream = new ByteArrayInputStream(model.getBytes("UTF-8"));
    }




}
