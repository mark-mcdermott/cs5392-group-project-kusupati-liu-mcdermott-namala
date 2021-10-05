package dev.markmcd.controller.types.misc;

import java.util.List;

/**
 * Class for sorting and storing the names of the test file.
 * The test file directory has kripkes and models (both valid and invalid versions of both).
 * The test files are ackwardly named -
 *  - valid kripkes: Model <num>.txt
 *  - invalid kripkes: Broken MOdel <num>.txt
 *  - models: Model <num>.txt (contain valid and invalid models)
 */
public class TestFiles {
    List kripkesValid;
    List kripkesInvalid;
    List models;

    public TestFiles(List kripkesValid, List kripkesInvalid, List models) {
        this.kripkesValid = kripkesValid;
        this.kripkesInvalid = kripkesInvalid;
        this.models = models;
    }

    public List getKripkesInvalid() {
        return kripkesInvalid;
    }

    public List getKripkesValid() {
        return kripkesValid;
    }

    public List getModels() {
        return models;
    }
}
