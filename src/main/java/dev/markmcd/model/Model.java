package dev.markmcd.model;

import dev.markmcd.controller.types.kripke.Kripke;
import dev.markmcd.controller.types.kripke.State;
import dev.markmcd.controller.types.misc.Options;
import dev.markmcd.controller.types.modelCheckRelated.AllEndToEndTestResults;
import dev.markmcd.controller.types.modelCheckRelated.ModelCheckResults;
import dev.markmcd.controller.types.modelCheckRelated.ValidationResults;

import java.io.IOException;
import java.util.Set;

/**
 * Holds model checking info - the kripke, the kripke file, the model and the state to check.
 * The setters here all check for null parameters and throw a NullPointerException if found
 */
public class Model {

    ValidationResults validationResults;
    ModelCheckResults modelCheckResults;
    AllEndToEndTestResults allEndToEndTestResults;

    public ValidationResults getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(ValidationResults validationResults) {
        this.validationResults = validationResults;
    }

    public ModelCheckResults getModelCheckResults() {
        return modelCheckResults;
    }

    public void setModelCheckResults(ModelCheckResults modelCheckResults) {
        this.modelCheckResults = modelCheckResults;
    }

    public AllEndToEndTestResults getAllEndToEndTestResults() {
        return allEndToEndTestResults;
    }

    public void setAllEndToEndTestResults(AllEndToEndTestResults allEndToEndTestResults) {
        this.allEndToEndTestResults = allEndToEndTestResults;
    }

}
