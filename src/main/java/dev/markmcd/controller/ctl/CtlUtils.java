package dev.markmcd.controller.ctl;

import dev.markmcd.controller.ctl.Parser.Token;
import dev.markmcd.controller.ctl.Validator.ParseException;
import dev.markmcd.controller.types.kripke.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dev.markmcd.utils.Utils.*;

public class CtlUtils {

        public static Boolean validateCtl(String ctlFormula) throws ParseException, IOException {
            ctlFormula = ctlFormula.trim();
            if (isAtom(ctlFormula) || isUnaryPathTempOp(ctlFormula)) {
                return true;
            } else {
                return false;
            }
        }

    public static Boolean isAtom(String ctlFormula) throws IOException {
        if (ctlFormula.length() == 1) {
            if (ctlFormula.matches("[p-z]")) {
                return true;
            } else {
                throw new IOException("Error in CTL Formula at character: " + ctlFormula + ". Atomic labels must be a letter from p to z.");
            }
        } else {
            return false;
        }
    }

    public static Boolean isUnaryPathTempOp(String ctlFormula) throws IOException, ParseException {
        String firstChar = ctlFormula.substring(0,1);
        String secondChar = ctlFormula.substring(1,2);
        if (firstChar.matches("[EA]")) {
            if (secondChar.matches("[XFG]")) {
                if (ctlFormula.length() > 2) {
                    String phi = ctlFormula.substring(2);
                    validateCtl(phi);
                    return true;
                } else {
                    throw new IOException("Error in CTL formula. Path/temporal operator \"" + ctlFormula + "\" found, but no atomic label (p-z) found after.");
                }
            } else {
                if (!secondChar.matches("\\[")) {
                    throw new IOException("Error in CTL formula. Path operator \"" + firstChar + "\" found, but the following \"" + secondChar + "\" character is not a valid temporal operator (X, F or G)");
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public static Boolean isBinaryPathTempOp(String ctlFormula) throws IOException {
        String firstChar = ctlFormula.substring(0,1);
        String secondChar = ctlFormula.substring(1,2);
        if (firstChar.matches("[EA]")) {
            if (secondChar.matches("\\[")) {

            }
        }
        return false;
    }

    public static Set statesWithLabel(Set states, String label) throws IOException {
           Set statesWithLabel = new HashSet();
           for (Object stateObj : states) {
               State state = (State) stateObj;
               // System.out.println(state);
               if (state.hasLabel(label)) {
                   statesWithLabel.add(state);
               }
           }
           return statesWithLabel;
    }

        public static Set statesWithLabel(Set states, Token t) throws IOException {
           Set statesWithLabel = new HashSet();
           // String labelStr = t.toString();
           // Character label = labelStr.charAt(0);
           String label = t.toString();
           for (Object stateObj : states) {
               State state = (State) stateObj;
               // System.out.println(state);
               if (state.hasLabel(label)) {
                   // System.out.println(state);
                   statesWithLabel.add(state);
               }
           }
           return statesWithLabel;
    }

//    public static FormulaObj statesWithLabelFormulaObj(Set states, Token t) {
//        String labelStr = t.toString();
//        Character label = labelStr.charAt(0);
//        Set statesWithLabel = statesWithLabel(states,label);
//        return new FormulaObj(statesWithLabel);
//    }

    public static Set intersection(Set a, Set b) {
        Set intersection = new HashSet();
        for (Object stateObj : a) {
            State state = (State) stateObj;
            if (contains(b,state)) {
                intersection.add(state);
            }
        }
        return intersection;
    }

    public static Set union(Set a, Set b) {
        Set union = new HashSet();
        for (Object stateObj : a) {
            union.add((State) stateObj);
        }
        for (Object stateObj : b) {
            State state = (State) stateObj;
            if (!contains(union,state)) {
                union.add(state);
            }
        }
        return union;
    }

    public static Set subtract(Set a, Set b) throws IOException {
        Set aCopy = copy(a);
        for (Object stateObj : b) {
            State thisState = (State) stateObj;
            Integer stateNumToRemove = thisState.getNumber();
            State stateToRemove = getState(stateNumToRemove,aCopy);
            aCopy.remove(stateToRemove);
        }
        return aCopy;
    }

}
