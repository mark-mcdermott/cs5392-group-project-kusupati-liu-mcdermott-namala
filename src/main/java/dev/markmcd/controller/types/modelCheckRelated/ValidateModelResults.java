package dev.markmcd.controller.types.modelCheckRelated;

public class ValidateModelResults {
    Boolean passValidation;
    String originalErrorMessage;
    String kripkeFilepath;

    public ValidateModelResults(Boolean passValidation, String originalErrorMessage, String kripkeFilepath) {
        this.passValidation = passValidation;
        this.originalErrorMessage = originalErrorMessage;
        this.kripkeFilepath = kripkeFilepath;
    }

    public Boolean getPassValidation() {
        return passValidation;
    }

    public String getOriginalErrorMessage() {
        return originalErrorMessage;
    }

    public void setOriginalErrorMessage(String originalErrorMessage) {
        this.originalErrorMessage = originalErrorMessage;
    }

    public void setPassValidation(Boolean passValidation) {
        this.passValidation = passValidation;
    }

    public String getKripkeFilepath() {
        return kripkeFilepath;
    }

    public void setKripkeFilepath(String kripkeFilepath) {
        this.kripkeFilepath = kripkeFilepath;
    }

}
