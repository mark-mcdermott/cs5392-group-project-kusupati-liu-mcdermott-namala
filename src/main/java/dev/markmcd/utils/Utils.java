package dev.markmcd.utils;

import dev.markmcd.controller.types.kripke.State;
import dev.markmcd.controller.types.kripke.Transition;
import dev.markmcd.controller.types.misc.Options;

import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Generic utility methods for dealing with {@link State}s, {@link Transition}s, CTL labels and {@link Set}s.
 * String utility methods for used in printing a Kripke structure are here - getStatesStr, getTransitionsStr and getLabelsStr. These are called from model/kripke/Kripke.java.
 * The other State utilities here are 1) isStateName and 2) getState. 1) isStateName used parsing a Kripke text file when checking what a certain line in that file contains and 2) getState is for getting a certain state out of a state set
 * The {@link Set} utils here are contains and areEqual - for checking for states in a state set and for checking state set equality.
 * More specific CTL utils are in CtlUtils in the controller/ctl directory.
 */
public class Utils {
    public Boolean printExceptions;

    // States Utils

    /**
     * Checks if {@link String} is a state name like s1 or s2 and returns true or false
     * Checks that string is 2 or more chars, that first char is "s" and than the char(s) after the "s" are integers
     * Used in the Controller method that parses a Kripke text file
     * @param str a {@link String} that one wants to make sure it's a state name like s1, s2, etc
     * @return
     */
    public static Boolean isStateName(String str) {
        if (str == null) { throw new NullPointerException("null argument in isStateName call"); }
        if (str.length() < 2) {
            return false;
        } else if (str.charAt(0) != 's') {
            return false;
        } else {
            // checks if char(s) after the "s" are numbers
            String charsAfterS = str.substring(1);
            if (!charsAfterS.matches("\\d+")) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Checks if {@link String} ends in .txt and has at least one character before the period.
     * Throws NullPointerException if {@link String} is null.
     * @param str The {@link String} one wants to check if it ends in .txt
     * @return false if string doesn't match the conditions above, otherwise returns true.
     * @throws IOException
     */
    public static Boolean isTxtFile(String str) throws IOException {
        if (str == null) { throw new NullPointerException("isTextFile param is null"); }
        int indexOfPeriod = str.lastIndexOf(".");
        if (indexOfPeriod == -1 || indexOfPeriod == 0) { return false; }
        String extension = str.substring(indexOfPeriod);
        if (!extension.equals(".txt")) {
            return false;
        }
        return true;
    }

    /**
     * Gets the names of states in a Set and returns them in a comma separated string with a colon at the end."
     * The states are sorted ascending by each state's number.
     * This is used for printing out Kripke contents and also for just printing out state set contents.
     * @param states The states that one needs the names of outputted
     * @return a string formatted like this "s1, s2, s3;"
     */
    public static String getStatesStr(Set states) throws IOException {
        if (states == null) { throw new NullPointerException("getStatesStr was passed a null Set param."); }
        if (states.size() == 0) { throw new IOException("State set is empty in getStatesStr"); }
        for (Object stateObj : states) {
            if (stateObj == null) { throw new NullPointerException("one of the states passed to getStatesStr is null"); }
        }
        List statesList = new ArrayList<State>(states);
        Collections.sort(statesList);

        String statesStr = "";
        for (Object stateObj : statesList) {
            State state = (State) stateObj;
            statesStr = statesStr + state.toString() + ", ";
        }
        statesStr = statesStr.trim();
        statesStr = statesStr.substring(0,statesStr.length() - 1); // remove trailing comma
        statesStr = statesStr + ";";
        return statesStr;
    }

    /**
     * prints the states in a set in numerical order in a format like "s1, s2, s3;"
     * This is used for debugging mostly
     * @param states {@link Set} of {@link State}s one wants to print in a format like "s1, s2, s3;"
     */
    public static void printStatesStr(Set states) throws IOException {
        String statesStr = getStatesStr(states);
        System.out.println(statesStr);
    }

    /**
     * Gets a state from a state set, given its state number.
     * Throws IOExceptionError if state is not found. Throws NullPointerError if either param is null.
     * @param stateNum {@link Integer} of state needed from state set
     * @param states The {@link Set} of {@link State}s one needs a particular state from
     * @return the {@link State} from the state set, if found
     */
    public static State getState(Integer stateNum, Set states) throws IOException {
        if (stateNum == null) { throw new NullPointerException("stateNum param in getState is null"); }
        if (states == null) { throw new NullPointerException("states param in getState is null"); }
        for (Object stateObj : states) {
            if (stateObj == null) { throw new NullPointerException("a state in state set in getState is null"); }
            State state = (State) stateObj;
            if (state.getNumber() == stateNum) {
                return state;
            }
        }
        throw new IOException("state number " + stateNum + " not found in state set in getState");
    }

    /**
     * Gets a transition from a transition set, given its transition number.
     * Throws IOExceptionError if state is not found. Throws NullPointerError if either param is null.
     * @param transitionNum {@link Integer} of state needed from state set
     * @param transitions The {@link Set} of {@link Transition}s one needs a particular transition from
     * @return the {@link Transition} from the transition set, if found
     */
    public static Transition getTransition(Integer transitionNum, Set transitions) throws IOException {
        if (transitionNum == null) { throw new NullPointerException("stateNum param in getState is null"); }
        if (transitions == null) { throw new NullPointerException("states param in getState is null"); }
        for (Object transitionObj : transitions) {
            if (transitionObj == null) { throw new NullPointerException("a state in state set in getState is null"); }
            Transition transition = (Transition) transitionObj;
            if (transition.getNumber() == transitionNum) {
                return transition;
            }
        }
        throw new IOException("transition number " + transitionNum + " not found in state set in getState");
    }


    // Transitions Utils

    /**
     * Gets the names of transitions in a Set and returns them in a multiline format like:
     * t1 : s1 - s2,
     * t2 : s1 - s3;
     * The transitions are sorted ascending by each state's number.
     * This is used for printing out Kripke contents.
     * @param {@link Set} of {@link Transition}s that one needs the names of outputted
     * @return returns a multiline {@String} like:
     * t1 : s1 - s2,
     * t2 : s1 - s3;
     */
    public static String getTransitionsStr(Set transitions) throws IOException {
        if (transitions == null) { throw new NullPointerException("Param in getTransitionStr is null"); }
        if (transitions.size() == 0) { throw new IOException("Transition set is empty in getTransitionStr"); }
        List transitionsList = new ArrayList<Transition>(transitions);
        Collections.sort(transitionsList);

        String transitionsStr = "";
        for (Object transitionObj : transitionsList) {
            if (transitionObj == null) { throw new NullPointerException("A transition in transitions set in getTransitionStr is null"); }
            Transition transition = (Transition) transitionObj;
            transitionsStr = transitionsStr + transition.toStringDetailed() + ",\n";
        }
        transitionsStr = transitionsStr.substring(0,transitionsStr.length() - 2); // remove trailing comma and newline
        transitionsStr = transitionsStr + ";\n";
        return transitionsStr;
    }

    /**
     * Prints transitions in transition set in format like:
     * t1 : s1 - s2,
     * t2 : s1 - s3;
     * Used for testing.
     * @param transitions {@link Set} of {@link Transition}s
     */
    public static void printTransitions(Set transitions) throws IOException {
        String transitionsStr = getTransitionsStr(transitions);
        System.out.println(transitionsStr);
    }

    // Label Utils

    /**
     * Gets a label string from a {@link Set} of labels (a label is just a {@link Character} p-z) in format like:
     * s1 : p q, (propositional atom names are separated by a space; a name consists of letters, it is casesensitive)
     * s2 : q t r,
     * s3 : , (i.e. set of propositional atoms for state s3 is empty)
     * s4 : t;
     * @param states A {@link Set} of labels (a label is just a {@link Character}
     * @return Gets a label string from a {@link Set} of labels (a label is just a {@link Character} p-z) in format like:
     * s1 : p q, (propositional atom names are separated by a space; a name consists of letters, it is casesensitive)
     * s2 : q t r,
     * s3 : , (i.e. set of propositional atoms for state s3 is empty)
     * s4 : t;
     */
    public static String getLabelsStr(Set states) {
        List statesList = new ArrayList<State>(states);
        Collections.sort(statesList);

        String labelsStr = "";
        int stateNum = 0;
        int numStates = statesList.size();
        for (Object stateObj : statesList) {
            State state = (State) stateObj;
            labelsStr = labelsStr + state.toStringDetailed();
            if (numStates > 1 && stateNum < numStates - 1) {
                labelsStr = labelsStr + ",\n";
                stateNum++;
            }
        }
        labelsStr = labelsStr + ";";
        return labelsStr;
    }

    // Set Utils

    /**
     * Checks if a set of {@link State}s has a certain state in it.
     * Note that only a state of the same state number is checked for. ie, 1 for s1. So this is not checking for the exact state by memory address. This is a loose implementation - might have to change this when I add Jung GUI back in
     * @param states set of {@link State}s to be searched for the state
     * @param state a {@link State} to be checked if it is in the set of states
     * @return true if state is in set, false otherwise
     */
    public static Boolean contains(Set states, State state) {
        if (states == null) { throw new NullPointerException("States param in contains is null"); }
        if (state == null) { throw new NullPointerException("State param in contains is null"); }
        Integer stateNum = state.getNumber();
        for (Object stateObj : states) {
            State thisState = (State) stateObj;
            if (thisState.getNumber() == stateNum) {
                return true;
            }
        }
        return false;
    }

    // from https://stackoverflow.com/a/31624585
    public static String trimAdvanced(String value) {

        Objects.requireNonNull(value);

        int strLength = value.length();
        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();

        if (strLength == 0) {
            return "";
        }

        while ((st < len) && (val[st] <= ' ') || (val[st] == '\u00A0')) {
            st++;
            if (st == strLength) {
                break;
            }
        }
        while ((st < len) && (val[len - 1] <= ' ') || (val[len - 1] == '\u00A0')) {
            len--;
            if (len == 0) {
                break;
            }
        }


        return (st > len) ? "" : ((st > 0) || (len < strLength)) ? value.substring(st, len) : value;
    }

    public static Boolean containsStateName(Set states, String stateName) {
        if (states == null) { throw new NullPointerException("States param in contains is null"); }
        String stateNumStr = stateName.replace("s","");
        stateNumStr = stateNumStr.replaceAll("\uFEFF", "");
        try {
           int stateNum = Integer.parseInt(stateNumStr);
           for (Object stateObj : states) {
               State thisState = (State) stateObj;
               if (thisState.getNumber() == stateNum) {
                   return true;
               }
           }
        } catch (NumberFormatException nfe) {
          // Handle the condition when str is not a number.
        }
        return false;
    }


    /**
     * Tests if two sets of states are "equal", ie. do they each have the same number of states and does the second set have a state with the same num as a state in the first set
     * Note that only a state of the same state number is checked for. ie, 1 for s1. So this is not checking for the exact state by memory address. This is a loose implementation - might have to change this when I add Jung GUI back in
     * @param setA set of {@link State}s to be compared to setB
     * @param setB set of {@link State}s to be compared to setA
     * @return true if sets are the same size and they have states of the same state numbers.
     */
    public static Boolean areEqual(Set setA, Set setB) {
        if (setA == null || setB == null) { throw new NullPointerException("areEqual param(s) is null."); }
        if (setA.size() > 0 && setA.size() == setB.size()) {
            for (Object stateObj : setA) {
                State state = (State) stateObj;
                if (!contains(setB,state)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }



    // remove byte order mark https://www.postgresql.org/message-id/20180717101246.GA41457%40elch.exwg.net
    public static String removeByteOrderMark(String str) {
        char firstChar = str.toCharArray()[0];
        int asciiNum = (int) firstChar;
        if (asciiNum == 65279) {
            str = str.substring(1);
        }
        return str;
    }

    /**
     * For debugging vs production. For debugging, we want to throw exception and halt program. For production, we want to print the error and keep going
     * @param errorMessage
     * @param printExceptions
     * @throws Exception
     */
    public static void handleError(String errorMessage, Boolean printExceptions) throws Exception {
        if (printExceptions) {
            System.out.println(errorMessage);
        } else {
            throw new Exception(errorMessage);
        }
    }





}
