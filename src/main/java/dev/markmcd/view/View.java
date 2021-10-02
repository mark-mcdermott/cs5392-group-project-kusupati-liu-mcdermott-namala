package dev.markmcd.view;

import dev.markmcd.model.kripke.Kripke;

/**
 * Prints the output of the model checking to the terminal
 */
public class View {

    public View() { }

    public void printKripke(Kripke kripke) {
        kripke.printKripke();
        // print model check results here
    }

}
