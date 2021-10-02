package dev.markmcd.model.kripke;

import org.junit.Test;

import javax.print.attribute.standard.NumberUp;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StateTest {

    // tests if addTransition throws NullPointerException when param is null
    @Test(expected = NullPointerException.class)
    public void testaddTransition1() throws IOException {
        State state1 = new State(1);
        Transition transition = null;
        state1.addTransition(transition);
    }

    // tests if addTransition throws NullPointerException when from state of param is null
    @Test(expected = NullPointerException.class)
    public void testaddTransition2() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        Transition transition = new Transition(null, state2);
        state1.addTransition(transition);
    }

    // tests if addTransition throws NullPointerException when to state of param is null
    @Test(expected = NullPointerException.class)
    public void testaddTransition3() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        Transition transition = new Transition(state1, null);
        state1.addTransition(transition);
    }

    // tests if addTransition throws NullPointerException when from and to states of param is null
    @Test(expected = NullPointerException.class)
    public void testaddTransition4() throws IOException {
        State state1 = new State(1);
        Transition transition = new Transition(null, null);
        state1.addTransition(transition);
    }

    // tests if addTransition throws NullPointerException when to state of param is null and param from state is not the state adding the transition to
    @Test(expected = NullPointerException.class)
    public void testaddTransition6() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        Transition transition = new Transition(state2, null);
        state1.addTransition(transition);
    }

    // tests if addTransition throws IOException when param from state is not the state adding the transition to
    @Test(expected = IOException.class)
    public void testaddTransition7() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        Transition transition = new Transition(state2, state3);
        state1.addTransition(transition);
    }

    // tests if addTransition does not throws IOException when param from state is the state adding the transition to
    @Test
    public void testaddTransition8() throws IOException {
        try {
            State state1 = new State(1);
            State state2 = new State(2);
            Transition transition = new Transition(state1, state2);
            state1.addTransition(transition);
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }
    }

    // tests state's transition set has size 1 if state had no transitions and we add one
    @Test
    public void testaddTransition9() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        Transition transition = new Transition(state1, state2);
        state1.addTransition(transition);
        assertEquals(1, state1.getTransitions().size());
    }

    // tests state's transition set has size 2 if state had no transitions and we add two
    @Test
    public void testaddTransition10() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        Transition transition1 = new Transition(state1, state2);
        Transition transition2 = new Transition(state1, state3);
        state1.addTransition(transition1);
        state1.addTransition(transition2);
        assertEquals(2, state1.getTransitions().size());
    }

    // tests state's transition set has size 5 if state had no transitions and we add five
    @Test
    public void testaddTransition11() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        State state4 = new State(4);
        State state5 = new State(5);
        Transition transition1 = new Transition(state1, state1);
        Transition transition2 = new Transition(state1, state2);
        Transition transition3 = new Transition(state1, state3);
        Transition transition4 = new Transition(state1, state4);
        Transition transition5 = new Transition(state1, state5);
        state1.addTransition(transition1);
        state1.addTransition(transition2);
        state1.addTransition(transition3);
        state1.addTransition(transition4);
        state1.addTransition(transition5);
        assertEquals(5, state1.getTransitions().size());
    }

    // tests state's transition set has size 0 if we do not add any transitions
    @Test
    public void testaddTransition12() throws IOException {
        State state1 = new State(1);
        assertEquals(0, state1.getTransitions().size());
    }

    // tests if hasTransitionTo throws null exception when passed a null state
    @Test(expected = NullPointerException.class)
    public void testHasTransitionTo1() throws NullPointerException, IOException {
        State testStateTest = new State(1);
        State nullStateTest = null;
        testStateTest.hasTransitionTo(nullStateTest);
    }

    // tests if hasTransitionTo returns false when the state does not have any transitions
    @Test
    public void testHasTransitionTo2() {
        State state1 = new State(1);
        State state2 = new State(2);
        assertFalse(state1.hasTransitionTo(state2));
    }

    // tests if hasTransitionTo returns false when the state has transitions but not the specified one
    @Test
    public void testHasTransitionTo3() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        State state4 = new State(4);
        state1.addTransition(new Transition(1, state1, state2));
        state1.addTransition(new Transition(2, state1, state3));
        assertFalse(state1.hasTransitionTo(state4));
    }

    // tests if hasTransitionTo returns true when the state only has the specified transition
    @Test
    public void testHasTransitionTo4() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        state1.addTransition(new Transition(1, state1, state2));
        assertTrue(state1.hasTransitionTo(state2));
    }

    // tests if hasTransitionTo returns true when the state has several transitions including the specified one
    @Test
    public void testHasTransitionTo5() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        State state4 = new State(4);
        state1.addTransition(new Transition(1, state1, state2));
        state1.addTransition(new Transition(2, state1, state3));
        state1.addTransition(new Transition(3, state1, state4));
        assertTrue(state1.hasTransitionTo(state4));
    }

    // tests if hasTransitionTo returns false when the state only has a transition to itself but another is specified
    @Test
    public void testHasTransitionTo6() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        state1.addTransition(new Transition(1, state1, state1));
        assertFalse(state1.hasTransitionTo(state2));
    }

    // tests if hasTransitionTo returns true when the state only has a transition to itself and that is the specified transition
    @Test
    public void testHasTransitionTo7() throws IOException {
        State state1 = new State(1);
        state1.addTransition(new Transition(1, state1, state1));
        assertTrue(state1.hasTransitionTo(state1));
    }

    // tests if hasTransitionTo returns true when the state has several transitions and it has the specified transition, which is a transition to itself
    @Test
    public void testHasTransitionTo8() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        State state4 = new State(4);
        state1.addTransition(new Transition(1, state1, state2));
        state1.addTransition(new Transition(2, state1, state3));
        state1.addTransition(new Transition(3, state1, state4));
        assertTrue(state1.hasTransitionTo(state3));
    }

    // tests if hasTransitionTo returns true when the state has several transitions including one to itself and it has the specified transition, which is not the transition to itself
    @Test
    public void testHasTransitionTo9() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        State state4 = new State(4);
        state1.addTransition(new Transition(1, state1, state1));
        state1.addTransition(new Transition(2, state1, state3));
        state1.addTransition(new Transition(3, state1, state4));
        assertTrue(state1.hasTransitionTo(state3));
    }

    // tests if toStringDetailed returns "s1 : " when state num is 1 and state has no labels
    @Test
    public void testToStringDetailed1() throws IOException {
        State state1 = new State(1);
        Set labels = new HashSet();
        state1.setLabels(labels);
        String stringDetailed = state1.toStringDetailed();
        assertEquals("s1 : ",stringDetailed);
    }

    // tests if toStringDetailed returns "s0 : p" when state num is 0 and p is only label
    @Test
    public void testToStringDetailed2() throws IOException {
        State state1 = new State(0);
        Set labels = new HashSet();
        labels.add('p');
        state1.setLabels(labels);
        String stringDetailed = state1.toStringDetailed();
        assertEquals("s0 : p",stringDetailed);
    }

    // tests if toStringDetailed returns "s0 : p q" when state num is 0 and p and q are the only labels
    @Test
    public void testToStringDetailed3() throws IOException {
        State state1 = new State(0);
        Set labels = new HashSet();
        labels.add('p');
        labels.add('q');
        state1.setLabels(labels);
        String stringDetailed = state1.toStringDetailed();
        assertEquals("s0 : p q",stringDetailed);
    }

    // tests if toStringDetailed returns "s100 : a z" when state num is 100 and z and a are the labels
    @Test
    public void testToStringDetailed4() throws IOException {
        State state1 = new State(100);
        Set labels = new HashSet();
        labels.add('z');
        labels.add('a');
        state1.setLabels(labels);
        String stringDetailed = state1.toStringDetailed();
        assertEquals("s100 : a z",stringDetailed);
    }

    // tests if hasLabel throws NullPointerException if labelToCheck is null
    @Test(expected = NullPointerException.class)
    public void hasLabel1() throws NullPointerException, IOException {
        State state1 = new State(1);
        Character labelToCheck = null;
        state1.hasLabel(labelToCheck);
    }

    // tests if hasLabel throws IOException if labelToCheck is a capitol letter
    @Test(expected = IOException.class)
    public void hasLabel2() throws IOException {
        State state1 = new State(1);
        Character labelToCheck = 'A';
        state1.hasLabel(labelToCheck);
    }

    // tests if hasLabel throws IOError if labelToCheck is a number
    @Test(expected = IOException.class)
    public void hasLabel3() throws IOException {
        State state1 = new State(1);
        Character labelToCheck = '1';
        state1.hasLabel(labelToCheck);
    }

    // tests if hasLabel throws IOError if labelToCheck is a @ sign
    @Test(expected = IOException.class)
    public void hasLabel4() throws IOException {
        State state1 = new State(1);
        Character labelToCheck = '@';
        state1.hasLabel(labelToCheck);
    }

    // tests if hasLabel throws IOError if labelToCheck a '\n' char
    @Test(expected = IOException.class)
    public void hasLabel5() throws IOException {
        State state1 = new State(1);
        Character labelToCheck = '\n';
        state1.hasLabel(labelToCheck);
    }

    // tests if hasLabel throws NullPointerException if a label in label set is null
    @Test(expected = NullPointerException.class)
    public void hasLabel6() throws NullPointerException, IOException {
        State state1 = new State(1);
        Set labels = new HashSet();
        Character char1 = 'a';
        Character char2 = null;
        labels.add(char1);
        labels.add(char2);
        state1.setLabels(labels);
        state1.hasLabel('p');
    }

    // tests if hasLabel returns true if labelToCheck is 'a' and state has 'a'
    @Test
    public void hasLabel7() throws IOException {
        State state1 = new State(1);
        Set labels = new HashSet();
        Character char1 = 'a';
        labels.add(char1);
        state1.setLabels(labels);
        assertTrue(state1.hasLabel('a'));
    }

    // tests if hasLabel returns false if labelToCheck is 'a' and state has 'z'
    @Test
    public void hasLabel8() throws IOException {
        State state1 = new State(1);
        Set labels = new HashSet();
        Character char1 = 'z';
        labels.add(char1);
        state1.setLabels(labels);
        assertFalse(state1.hasLabel('a'));
    }

    // tests if hasLabel returns false if labelToCheck is 'a' and state has no labels
    @Test
    public void hasLabel9() throws IOException {
        State state1 = new State(1);
        Set labels = new HashSet();
        state1.setLabels(labels);
        assertFalse(state1.hasLabel('a'));
    }

    // tests if hasLabel returns fasle if labelToCheck is 'p' and state has 'a' and 'b'
    @Test
    public void hasLabel10() throws IOException {
        State state1 = new State(1);
        Set labels = new HashSet();
        Character char1 = 'a';
        Character char2 = 'b';
        labels.add(char1);
        labels.add(char2);
        state1.setLabels(labels);
        assertFalse(state1.hasLabel('p'));
    }

    // tests if hasLabel returns true if labelToCheck is 'p' and state has 'a' and 'p'
    @Test
    public void hasLabel11() throws IOException {
        State state1 = new State(1);
        Set labels = new HashSet();
        Character char1 = 'a';
        Character char2 = 'p';
        labels.add(char1);
        labels.add(char2);
        state1.setLabels(labels);
        assertTrue(state1.hasLabel('p'));
    }



}
