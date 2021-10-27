package dev.markmcd.controller.ctl;

import dev.markmcd.controller.types.misc.Arguments;
import dev.markmcd.controller.types.modelRelated.FormulaInputSource;
import org.junit.Test;

import java.io.IOException;

import static dev.markmcd.controller.types.modelRelated.FormulaInputSource.ARGUMENT;
import static dev.markmcd.controller.types.modelRelated.FormulaInputSource.FILE;

public class ArgumentsTest {

    // checks if the (4 param) Arguments constructor throws NullExceptionError if kripke filename is null
    @Test(expected = NullPointerException.class)
    public void testArguments1() throws NullPointerException, IOException {
        String nullFilename = null;
        new Arguments(nullFilename, "s0", FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws NullExceptionError if stateToCheckStr is null
    @Test(expected = NullPointerException.class)
    public void testArguments2() throws NullPointerException, IOException {
        String nullString = null;
        new Arguments("kripke.txt", nullString, FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws NullExceptionError if modelInputSource is null
    @Test(expected = NullPointerException.class)
    public void testArguments3() throws NullPointerException, IOException {
        FormulaInputSource nullVal = null;
        new Arguments("kripke.txt", "s0", nullVal, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws NullExceptionError if modelInputStr is null
    @Test(expected = NullPointerException.class)
    public void testArguments4() throws NullPointerException, IOException {
        String nullString = null;
        new Arguments("kripke.txt", "s0", FILE, nullString, false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if kripke filename is "a"
    @Test(expected = IOException.class)
    public void testArguments5() throws NullPointerException, IOException {
        new Arguments("a", "s0", FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if kripke filename is "kripke.krp"
    @Test(expected = IOException.class)
    public void testArguments6() throws NullPointerException, IOException {
        new Arguments("kripke.krp", "s0", FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if stateToCheckStr is "1"
    @Test(expected = IOException.class)
    public void testArguments7() throws NullPointerException, IOException {
        new Arguments("kripke.txt", "1", FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if stateToCheckStr is "state1"
    @Test(expected = IOException.class)
    public void testArguments8() throws NullPointerException, IOException {
        new Arguments("kripke.txt", "state1", FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if stateToCheckStr is "state 1"
    @Test(expected = IOException.class)
    public void testArguments9() throws NullPointerException, IOException {
        new Arguments("kripke.txt", "state 1", FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if modelInputSource is FILE but modelInputStr is "a"
    @Test(expected = IOException.class)
    public void testArguments10() throws NullPointerException, IOException {
        new Arguments("kripke.txt", "s0", FILE, "a", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if modelInputSource is FILE but modelInputStr is "model.mdl"
    @Test(expected = IOException.class)
    public void testArguments11() throws NullPointerException, IOException {
        new Arguments("kripke.txt", "1", FILE, "model.mdl", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if modelInputSource is FILE but modelInputStr is "EXp"
    @Test(expected = IOException.class)
    public void testArguments12() throws NullPointerException, IOException {
        new Arguments("kripke.txt", "1", FILE, "EXp", false);
    }

    // checks if the (4 param) Arguments constructor throws IOException if modelInputSource is ARGUMENT but modelInputStr is "formula.txt"
    @Test(expected = IOException.class)
    public void testArguments13() throws NullPointerException, IOException {
        new Arguments("kripke.txt", "1", ARGUMENT, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "kripke.txt", "s0", "FILE", "formula.txt"
    @Test
    public void testArguments14() throws IOException {
        new Arguments("kripke.txt", "s0", FILE, "formula.txt", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "kripke file 1.txt", "s1", "FILE", "model file 1.txt"
    @Test
    public void testArguments15() throws IOException {
        new Arguments("kripke file 1.txt", "s1", FILE, "model file 1.txt", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "k.txt", "s100", "FILE", "m.txt"
    @Test
    public void testArguments16() throws IOException {
        new Arguments("k.txt", "s100", FILE, "m.txt", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "kripke.txt", "s0", "ARGUMENT", "p"
    @Test
    public void testArguments17() throws IOException {
        new Arguments("kripke.txt", "s0", ARGUMENT, "p", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "kripke.txt", "s0", "ARGUMENT", "EXp"
    @Test
    public void testArguments18() throws IOException {
        new Arguments("kripke.txt", "s0", ARGUMENT, "EXp", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "kripke.txt", "s0", "ARGUMENT", "EXp"
    @Test
    public void testArguments19() throws IOException {
        new Arguments("kripke.txt", "s0", ARGUMENT, "EXp", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "kripke.txt", "s0", "ARGUMENT", "A[ p U q ]"
    @Test
    public void testArguments20() throws IOException {
        new Arguments("kripke.txt", "s0", ARGUMENT, "A[ p U q]", false);
    }

    // checks if the (4 param) Arguments constructor works if params are "kripke.txt", "s0", "ARGUMENT", "A[ (p and q) U (a -> b) ]"
    @Test
    public void testArguments21() throws IOException {
        new Arguments("kripke.txt", "s0", ARGUMENT, "A[ (p and q) U (a -> b) ]", false);
    }


    // checks if the (3 param) Arguments constructor throws NullExceptionError if kripke filename is null
    @Test(expected = NullPointerException.class)
    public void testArguments22() throws NullPointerException, IOException {
        String nullFilename = null;
        new Arguments(nullFilename, FILE, "formula.txt", false);
    }


    // checks if the (3 param) Arguments constructor throws NullExceptionError if modelInputSource is null
    @Test(expected = NullPointerException.class)
    public void testArguments23() throws NullPointerException, IOException {
        FormulaInputSource nullVal = null;
        new Arguments("kripke.txt", nullVal, "formula.txt", false);
    }

    // checks if the (3 param) Arguments constructor throws NullExceptionError if modelInputStr is null
    @Test(expected = NullPointerException.class)
    public void testArguments24() throws NullPointerException, IOException {
        String nullString = null;
        new Arguments("kripke.txt", FILE, nullString, false);
    }

    // checks if the (3 param) Arguments constructor throws IOException if kripke filename is "a"
    @Test(expected = IOException.class)
    public void testArguments25() throws NullPointerException, IOException {
        new Arguments("a", FILE, "formula.txt", false);
    }

    // checks if the (3 param) Arguments constructor throws IOException if kripke filename is "kripke.krp"
    @Test(expected = IOException.class)
    public void testArguments26() throws NullPointerException, IOException {
        new Arguments("kripke.krp", FILE, "formula.txt", false);
    }

    // checks if the (3 param) Arguments constructor throws IOException if modelInputSource is FILE but modelInputStr is "a"
    @Test(expected = IOException.class)
    public void testArguments27() throws NullPointerException, IOException {
        new Arguments("kripke.txt", FILE, "a", false);
    }

    // checks if the (3 param) Arguments constructor throws IOException if modelInputSource is FILE but modelInputStr is "model.mdl"
    @Test(expected = IOException.class)
    public void testArguments28() throws NullPointerException, IOException {
        new Arguments("kripke.txt", FILE, "model.mdl", false);
    }

    // checks if the (3 param) Arguments constructor throws IOException if modelInputSource is FILE but modelInputStr is "EXp"
    @Test(expected = IOException.class)
    public void testArguments29() throws NullPointerException, IOException {
        new Arguments("kripke.txt", FILE, "EXp", false);
    }

    // checks if the (3 param) Arguments constructor throws IOException if modelInputSource is ARGUMENT but modelInputStr is "formula.txt"
    @Test(expected = IOException.class)
    public void testArguments30() throws NullPointerException, IOException {
        new Arguments("kripke.txt", ARGUMENT, "formula.txt", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "kripke.txt", "FILE", "formula.txt"
    @Test
    public void testArguments31() throws IOException {
        new Arguments("kripke.txt", FILE, "formula.txt", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "kripke file 1.txt", "FILE", "model file 1.txt"
    @Test
    public void testArguments32() throws IOException {
        new Arguments("kripke file 1.txt", FILE, "model file 1.txt", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "k.txt", "FILE", "m.txt"
    @Test
    public void testArguments33() throws IOException {
        new Arguments("k.txt", FILE, "m.txt", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "kripke.txt", "ARGUMENT", "p"
    @Test
    public void testArguments34() throws IOException {
        new Arguments("kripke.txt", ARGUMENT, "p", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "kripke.txt", "ARGUMENT", "EXp"
    @Test
    public void testArguments35() throws IOException {
        new Arguments("kripke.txt", ARGUMENT, "EXp", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "kripke.txt", "ARGUMENT", "EXp"
    @Test
    public void testArguments36() throws IOException {
        new Arguments("kripke.txt", ARGUMENT, "EXp", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "kripke.txt", "ARGUMENT", "A[ p U q ]"
    @Test
    public void testArguments37() throws IOException {
        new Arguments("kripke.txt", ARGUMENT, "A[ p U q]", false);
    }

    // checks if the (3 param) Arguments constructor works if params are "kripke.txt", "ARGUMENT", "A[ (p and q) U (a -> b) ]"
    @Test
    public void testArguments38() throws IOException {
        new Arguments("kripke.txt", ARGUMENT, "A[ (p and q) U (a -> b) ]", false);
    }


}