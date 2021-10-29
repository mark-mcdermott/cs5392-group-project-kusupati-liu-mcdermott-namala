package dev.markmcd.controller.types.modelCheckRelated;

import java.util.List;

public class AllEndToEndTestResults {

    List validateModelResultsList;
    List validateFormulaResultList;
    List endToEndTestResultsList;

    public AllEndToEndTestResults(List validateModelResultsList, List validateFormulaResultList, List endToEndTestResultsList) {
        this.validateModelResultsList = validateModelResultsList;
        this.validateFormulaResultList = validateFormulaResultList;
        this.endToEndTestResultsList = endToEndTestResultsList;
    }

    public List getEndToEndTestResultsList() {
        return endToEndTestResultsList;
    }

    public List getValidateFormulaResultList() {
        return validateFormulaResultList;
    }

    public List getValidateModelResultsList() {
        return validateModelResultsList;
    }

    public void setEndToEndTestResultsList(List endToEndTestResultsList) {
        this.endToEndTestResultsList = endToEndTestResultsList;
    }

    public void setValidateFormulaResultList(List validateFormulaResultList) {
        this.validateFormulaResultList = validateFormulaResultList;
    }

    public void setValidateModelResultsList(List validateModelResultsList) {
        this.validateModelResultsList = validateModelResultsList;
    }

}
