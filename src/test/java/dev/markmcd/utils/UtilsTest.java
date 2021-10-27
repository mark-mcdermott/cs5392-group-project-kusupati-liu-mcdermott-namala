package dev.markmcd.utils;

import dev.markmcd.controller.types.kripke.State;
import dev.markmcd.controller.types.kripke.Transition;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static dev.markmcd.utils.Utils.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UtilsTest {


    // tests if isStateName throws NullPointerException if param is null
    @Test(expected = NullPointerException.class)
    public void testIsStateName1() throws IOException {
        String str = null;
        isStateName(str);
    }

    // tests if isStateName returns false if param is empty string
    @Test
    public void testIsStateName2() {
        String str = "";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if param is a string of length 1
    @Test
    public void testIsStateName3() {
        String str = "z";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if passed a string of integers
    @Test
    public void testIsStateName4() {
        String str = "1";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if passed a string that's just "0"
    @Test
    public void testIsStateName5() {
        String str = "0";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if passed a string that's just "a"
    @Test
    public void testIsStateName6() {
        String str = "a";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if passed a string that's just "@"
    @Test
    public void testIsStateName7() {
        String str = "@";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if passed a string that's just "s"
    @Test
    public void testIsStateName8() {
        String str = "s";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if passed a string that's just "ss"
    @Test
    public void testIsStateName9() {
        String str = "ss";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns false if passed a string that's just "s@"
    @Test
    public void testIsStateName10() {
        String str = "s@";
        assertFalse(isStateName(str));
    }

    // tests if isStateName returns true if passed a string that's "s0"
    @Test
    public void testIsStateName11() {
        String str = "s0";
        assertTrue(isStateName(str));
    }

    // tests if isStateName returns true if passed a string that's "s1"
    @Test
    public void testIsStateName12() {
        String str = "s1";
        assertTrue(isStateName(str));
    }

    // tests if isStateName returns true if passed a string that's "s10"
    @Test
    public void testIsStateName13() {
        String str = "s10";
        assertTrue(isStateName(str));
    }

    // tests if isStateName returns true if passed a string that's "s11"
    @Test
    public void testIsStateName14() {
        String str = "s11";
        assertTrue(isStateName(str));
    }

    // tests if isTxtFile throws NullPointerException if param is null
    @Test(expected = NullPointerException.class)
    public void testIsTxtFile1() throws IOException {
        String str = null;
        isTxtFile(str);
    }

    // tests if isTxtFile returns false if string has no "."
    @Test
    public void testIsTxtFile2() throws IOException {
        String str = "test";
        assertFalse(isTxtFile(str));
    }

    // tests if isTxtFile returns false if param is ".txt"
    @Test
    public void testIsTxtFile3() throws IOException {
        String str = ".txt";
        assertFalse(isTxtFile(str));
    }

    // tests if isTxtFile returns false if param is ".tx"
    @Test
    public void testIsTxtFile4() throws IOException {
        String str = ".tx";
        assertFalse(isTxtFile(str));
    }

    // tests if isTxtFile returns false if param is ".krp"
    @Test
    public void testIsTxtFile5() throws IOException {
        String str = ".krp";
        assertFalse(isTxtFile(str));
    }

    // tests if isTxtFile returns true if param is "1.txt"
    @Test
    public void testIsTxtFile6() throws IOException {
        String str = "1.txt";
        assertTrue(isTxtFile(str));
    }

    // tests if isTxtFile returns true if param is "formula.txt"
    @Test
    public void testIsTxtFile7() throws IOException {
        String str = "formula.txt";
        assertTrue(isTxtFile(str));
    }

    // tests if isTxtFile returns true if param is "kripke.txt"
    @Test
    public void testIsTxtFile8() throws IOException {
        String str = "kripke.txt";
        assertTrue(isTxtFile(str));
    }

    // tests if getStatesStr returns null if a null set is passed
    @Test(expected = NullPointerException.class)
    public void testGetStatesStr1() throws NullPointerException, IOException {
        Set set = null;
        getStatesStr(set);
    }

    // tests if getStatesStr returns null if the second state in a set is null
    @Test(expected = NullPointerException.class)
    public void testGetStatesStr2() throws NullPointerException, IOException {
        State state1 = new State(1);
        State state2 = null;
        Set set = new HashSet();
        set.add(state1);
        set.add(state2);
        getStatesStr(set);
    }

    // tests if getStatesStr's output has sorted two states by ascending state number, when the states were added to the set in descending order
    @Test
    public void testGetStatesStr3() throws IOException {
        State state1 = new State(1);
        State state2 = new State(2);
        Set set = new HashSet();
        set.add(state2);
        set.add(state1);
        assertEquals("s1, s2;", getStatesStr(set));
    }

    // tests if getStatesStr's output is correct with just one state, s0
    @Test
    public void testGetStatesStr4() throws IOException {
        State state1 = new State(0);
        Set set = new HashSet();
        set.add(state1);
        assertEquals("s0;", getStatesStr(set));
    }

    // tests if getStatesStr's output is correct with just one state, s1
    @Test
    public void testGetStatesStr5() throws IOException {
        State state1 = new State(01);
        Set set = new HashSet();
        set.add(state1);
        assertEquals("s1;", getStatesStr(set));
    }

    // tests if getStatesStr's output is correct with two states, s0 and s1
    @Test
    public void testGetStatesStr6() throws IOException {
        State state1 = new State(0);
        State state2 = new State(1);
        Set set = new HashSet();
        set.add(state1);
        set.add(state2);
        assertEquals("s0, s1;", getStatesStr(set));
    }

    // tests if getStatesStr's output is correct with just two states, s0 and s100
    @Test
    public void testGetStatesStr7() throws IOException {
        State state1 = new State(0);
        State state2 = new State(100);
        Set set = new HashSet();
        set.add(state1);
        set.add(state2);
        assertEquals("s0, s100;", getStatesStr(set));
    }

    // tests is getStatesStr's output is correct with just two states, s100 and s0
    @Test
    public void testGetStatesStr8() throws IOException {
        State state1 = new State(100);
        State state2 = new State(0);
        Set set = new HashSet();
        set.add(state1);
        set.add(state2);
        assertEquals("s0, s100;", getStatesStr(set));
    }

    // tests is getStatesStr's output is correct with just four states: s0, s1, s2 and s3
    @Test
    public void testGetStatesStr9() throws IOException {
        State state1 = new State(0);
        State state2 = new State(1);
        State state3 = new State(2);
        State state4 = new State(3);
        Set set = new HashSet();
        set.add(state1);
        set.add(state2);
        set.add(state3);
        set.add(state4);
        assertEquals("s0, s1, s2, s3;", getStatesStr(set));
    }

    // test getStatesStr throws IOException if state set is empty
    @Test(expected = IOException.class)
    public void testGetStatesStr10() throws NullPointerException, IOException {
        Set set = new HashSet();
        getStatesStr(set);
    }

    // no need to test printStatesStr (it's already tested with the testGetStatesStr tests above

    // test getState throws NullPointerException if stateNum param is null
    @Test(expected = NullPointerException.class)
    public void testGetState1() throws IOException {
        Integer stateNum = null;
        State state1 = new State(1);
        Set stateSet = new HashSet();
        stateSet.add(state1);
        getState(stateNum,stateSet);
    }

    // test getState throws NullPointerException if states param is null
    @Test(expected = NullPointerException.class)
    public void testGetState2() throws IOException {
        Integer stateNum = 1;
        Set stateSet = null;
        getState(stateNum,stateSet);
    }

    // test getState throws NullPointerException if a state in states param is null
    @Test(expected = NullPointerException.class)
    public void testGetState3() throws IOException {
        Integer stateNum = null;
        State state1 = new State(1);
        State state2 = null;
        Set stateSet = new HashSet();
        stateSet.add(state1);
        stateSet.add(state2);
        getState(stateNum,stateSet);
    }

    // test getState throws IOException if a state to find is not in state set
    @Test(expected = IOException.class)
    public void testGetState4() throws IOException {
        Integer stateNum = 4;
        State state1 = new State(1);
        State state2 = new State(2);
        Set stateSet = new HashSet();
        stateSet.add(state1);
        stateSet.add(state2);
        getState(stateNum,stateSet);
    }

    // test getState returns the state if state is in state set and state is s0 and is only state
    @Test
    public void testGetState5() throws IOException {
        Integer stateNum = 0;
        State state1 = new State(0);
        Set stateSet = new HashSet();
        stateSet.add(state1);
        State state = getState(stateNum,stateSet);
        assertEquals((Object) 0, (Object) state.getNumber());
    }

    // test getState returns the state if state is in state set and state set has 10 states
    @Test
    public void testGetState6() throws IOException {
        State state1 = new State(0);
        State state2 = new State(1);
        State state3 = new State(2);
        State state4 = new State(3);
        State state5 = new State(4);
        State state6 = new State(5);
        State state7 = new State(6);
        State state8 = new State(7);
        State state9 = new State(8);
        State state10 = new State(9);
        Set set = new HashSet();
        set.add(state1);
        set.add(state2);
        set.add(state3);
        set.add(state4);
        set.add(state5);
        set.add(state6);
        set.add(state7);
        set.add(state8);
        set.add(state9);
        set.add(state10);
        State state = getState(3, set);
        assertEquals((Object) 3, (Object) state.getNumber());
    }

    // tests if getTransitionsStr throws NullPointerException if param is null
    @Test(expected = NullPointerException.class)
    public void testGetTransitionsStr1() throws IOException {
        Set transitions = null;
        getTransitionsStr(transitions);
    }

    // tests if getTransitionsStr throws NullPointerException if a transition in transitions param is null
    @Test(expected = NullPointerException.class)
    public void testGetTransitionsStr2() throws IOException {
        Set transitions = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        Transition transition1 = new Transition(state1,state2);
        Transition transition2 = null;
        transitions.add(transition1);
        transitions.add(transition2);
        getTransitionsStr(transitions);
    }

    // tests if getTransitionsStr throws IOError if transition set is empty
    @Test(expected = IOException.class)
    public void testGetTransitionsStr3() throws IOException {
        Set transitions = new HashSet();
        getTransitionsStr(transitions);
    }

    // tests if getTransitionsStr string is correct if there is one transition
    @Test
    public void testGetTransitionsStr4() throws IOException {
        Set transitions = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        Transition transition1 = new Transition(1, state1,state2);
        transitions.add(transition1);
        String transitionStr = getTransitionsStr(transitions);
        assertEquals("t1 : s1 - s2;\n", transitionStr);
    }

    // tests if getTransitionsStr string is correct if there is one transition and it it from a state to itself
    @Test
    public void testGetTransitionsStr5() throws IOException {
        Set transitions = new HashSet();
        State state1 = new State(1);
        Transition transition1 = new Transition(1, state1,state1);
        transitions.add(transition1);
        String transitionStr = getTransitionsStr(transitions);
        assertEquals("t1 : s1 - s1;\n", transitionStr);
    }

    // tests if getTransitionsStr string is correct if there are two transitions
    @Test
    public void testGetTransitionsStr6() throws IOException {
        Set transitions = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        Transition transition1 = new Transition(1, state1,state2);
        Transition transition2 = new Transition(2, state1,state3);
        transitions.add(transition1);
        transitions.add(transition2);
        String transitionStr = getTransitionsStr(transitions);
        assertEquals("t1 : s1 - s2,\nt2 : s1 - s3;\n", transitionStr);
    }



    // no need to test printTransactions - it's tested with the testGetTransitionsStr tests above

    // tests if getLabelsStr gets correct output with one state with one label
    @Test
    public void testGetLabelsStr1() throws IOException {
        Set states = new HashSet();
        Set labels = new HashSet();
        State state1 = new State(1);
        labels.add("p");
        state1.setLabels(labels);
        states.add(state1);
        String labelsStr = getLabelsStr(states);
        assertEquals("s1 : p;", labelsStr);
    }

    // tests if getLabelsStr gets correct output with one state with two labels
    @Test
    public void testGetLabelsStr2() throws IOException {
        Set states = new HashSet();
        Set labels = new HashSet();
        State state1 = new State(1);
        labels.add("p");
        labels.add("q");
        state1.setLabels(labels);
        states.add(state1);
        String labelsStr = getLabelsStr(states);
        assertEquals("s1 : p q;", labelsStr);
    }

    // tests if getLabelsStr gets correct output with two states with two labels each
    @Test
    public void testGetLabelsStr3() throws IOException {
        Set states = new HashSet();
        Set labels1 = new HashSet();
        Set labels2 = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        labels1.add("p");
        labels1.add("q");
        labels2.add("r");
        labels2.add("s");
        state1.setLabels(labels1);
        state2.setLabels(labels2);
        states.add(state1);
        states.add(state2);
        String labelsStr = getLabelsStr(states);
        assertEquals("s1 : p q,\ns2 : r s;", labelsStr);
    }

    // tests if getLabelsStr gets correct output with two states where first state has no labels and second state has two labels
    @Test
    public void testGetLabelsStr4() throws IOException {
        Set states = new HashSet();
        Set labels1 = new HashSet();
        Set labels2 = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        labels2.add("r");
        labels2.add("s");
        state1.setLabels(labels1);
        state2.setLabels(labels2);
        states.add(state1);
        states.add(state2);
        String labelsStr = getLabelsStr(states);
        assertEquals("s1 : ,\ns2 : r s;", labelsStr);
    }

    // tests if contains throws NullPointerException if states is null
    @Test(expected = NullPointerException.class)
    public void testContains1() throws IOException {
        Set states = null;
        State state1 = new State(1);
        contains(states,state1);
    }

    // tests if contains throws NullPointerException if state is null
    @Test(expected = NullPointerException.class)
    public void testContains2() throws IOException {
        Set states = new HashSet();
        State state1 = null;
        contains(states,state1);
    }

    // tests if contains throws ClassCastException if states contains strings
    @Test(expected = ClassCastException.class)
    public void testContains3() throws IOException {
        Set states = new HashSet();
        states.add("a");
        states.add("b");
        State state1 = new State(1);
        contains(states,state1);
    }

    // tests if contains returns false if states is empty
    @Test
    public void testContains4() throws IOException {
        Set states = new HashSet();
        State state1 = new State(1);
        Boolean result = contains(states,state1);
        assertFalse(result);
    }

    // tests if contains returns true if states is has only desired state
    @Test
    public void testContains5() throws IOException {
        Set states = new HashSet();
        State state1 = new State(1);
        states.add(state1);
        Boolean result = contains(states,state1);
        assertTrue(result);
    }

    // tests if contains returns false if states has two states but neither is the desired state
    @Test
    public void testContains6() throws IOException {
        Set states = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        states.add(state1);
        states.add(state2);
        Boolean result = contains(states,state3);
        assertFalse(result);
    }

    // tests if contains returns true if states has two states and first is desired state
    @Test
    public void testContains7() throws IOException {
        Set states = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        states.add(state1);
        states.add(state2);
        Boolean result = contains(states,state1);
        assertTrue(result);
    }

    // tests if contains returns true if states has two states and second is desired state
    @Test
    public void testContains8() throws IOException {
        Set states = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        states.add(state1);
        states.add(state2);
        Boolean result = contains(states,state2);
        assertTrue(result);
    }

    // tests if areEqual throws NullPointer exception if setA is null
    @Test(expected = NullPointerException.class)
    public void testAreEqual1() throws IOException {
        Set setA = null;
        Set setB = new HashSet();
        Boolean result = areEqual(setA,setB);
    }

    // tests if areEqual throws NullPointer exception if setB is null
    @Test(expected = NullPointerException.class)
    public void testAreEqual2() throws IOException {
        Set setA = new HashSet();
        Set setB = null;
        Boolean result = areEqual(setA,setB);
    }

    // tests if areEqual returns false if first set is empty
    @Test
    public void testAreEqual3() throws IOException {
        Set setA = new HashSet();
        Set setB = new HashSet();
        State state1 = new State(1);
        setB.add(state1);
        Boolean result = areEqual(setA,setB);
        assertFalse(result);
    }

    // tests if areEqual returns false if second set is empty
    @Test
    public void testAreEqual4() throws IOException {
        Set setA = new HashSet();
        Set setB = new HashSet();
        State state1 = new State(1);
        setA.add(state1);
        Boolean result = areEqual(setA,setB);
        assertFalse(result);
    }

    // tests if areEqual returns true if both sets have only s1
    @Test
    public void testAreEqual5() throws IOException {
        Set setA = new HashSet();
        Set setB = new HashSet();
        State state1 = new State(1);
        setA.add(state1);
        setB.add(state1);
        Boolean result = areEqual(setA,setB);
        assertTrue(result);
    }

    // tests if areEqual returns false if both sets have s1 and one other state
    @Test
    public void testAreEqual6() throws IOException {
        Set setA = new HashSet();
        Set setB = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        setA.add(state1);
        setA.add(state2);
        setB.add(state1);
        setB.add(state3);
        Boolean result = areEqual(setA,setB);
        assertFalse(result);
    }

    // tests if areEqual returns false if one set has s1 and the second set has s2
    @Test
    public void testAreEqual7() throws IOException {
        Set setA = new HashSet();
        Set setB = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        setA.add(state1);
        setB.add(state2);
        Boolean result = areEqual(setA,setB);
        assertFalse(result);
    }

    // tests if areEqual returns false if one set has s1 and the second set has s2 and s3
    @Test
    public void testAreEqual8() throws IOException {
        Set setA = new HashSet();
        Set setB = new HashSet();
        State state1 = new State(1);
        State state2 = new State(2);
        State state3 = new State(3);
        setA.add(state1);
        setB.add(state2);
        setB.add(state3);
        Boolean result = areEqual(setA,setB);
        assertFalse(result);
    }

    // tests if areEqual returns false if both sets have s10 and s100
    @Test
    public void testAreEqual9() throws IOException {
        Set setA = new HashSet();
        Set setB = new HashSet();
        State state1 = new State(10);
        State state2 = new State(100);
        setA.add(state1);
        setA.add(state2);
        setB.add(state1);
        setB.add(state2);
        Boolean result = areEqual(setA,setB);
        assertTrue(result);
    }

}
