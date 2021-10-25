package dev.markmcd.controller.types.kripke;

import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class KripkeTest {

    // tests if toString throws NullPointerException if a states is null
    @Test(expected = NullPointerException.class)
    public void testToString1() throws NullPointerException, IOException {
        Set states = null;
        Set transitions = new HashSet();
        Kripke kripke = new Kripke(states, transitions);
        kripke.toString();
    }

    // tests if toString throws NullPointerException if a transitions is null
    @Test(expected = NullPointerException.class)
    public void testToString2() throws NullPointerException, IOException {
        Set states = new HashSet();
        Set transitions = null;
        Kripke kripke = new Kripke(states, transitions);
        kripke.setStates(states);
        kripke.setStates(transitions);
        kripke.toString();
    }

    // tests if toString output is correct for example Kripke
    @Test(expected = NullPointerException.class)
    public void testToString3() {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        State state4 = new State(4);
        Transition transition1 = new Transition(state1, state2);
        Transition transition2 = new Transition(state1, state3);
        Transition transition3 = new Transition(state3, state4);
        Transition transition4 = new Transition(state4, state2);
        Transition transition5 = new Transition(state2, state3);
        Set state1Labels = new HashSet();
        Set state2Labels = new HashSet();
        Set state3Labels = new HashSet();
        Set state4Labels = new HashSet();
        state1Labels.add("p");
        state1Labels.add("q");
        state2Labels.add("q");
        state2Labels.add("t");
        state2Labels.add("r");
        state4Labels.add("t");
        Set states = new HashSet();
        states.add(state1);
        states.add(state2);
        states.add(state3);
        states.add(state4);
        state1.setLabels(state1Labels);
        state1.setLabels(state2Labels);
        state1.setLabels(state4Labels);
        Set transitions = new HashSet();
        transitions.add(transition1);
        transitions.add(transition2);
        transitions.add(transition3);
        transitions.add(transition4);
        transitions.add(transition5);
        Kripke kripke = new Kripke(states,transitions);
        String actualToString = kripke.toString();
        String expectedToString = "s1, s2, s3, s4;\nt1 : s1 - s2,\nt2 : s1 - s3,\nt3 : s3 – s4,\nt4 : s4 – s2,\nt5 : s2 – s3;\ns1 : p q,\ns2 : q t r,\ns3 : ,\ns4 : t;";
        assertEquals(actualToString,expectedToString);
    }



}
