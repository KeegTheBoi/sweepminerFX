package com.javafxgrid.utils;

import javafx.beans.binding.StringExpression;
import javafx.beans.value.ObservableStringValue;

import java.util.*;

public interface EmbeddedStringCoder {

    /**
     * * Codes a stream of string expression into a singel concatenated Observable string value
     * @param listExpressions
     * @return
     * @throws IllegalStateException throws an expetion if the separator is not defined
     */
    ObservableStringValue codeObservableValue(List<StringExpression> listExpressions) throws IllegalStateException;
    
    /**
     * Decodes given embedded string from the index
     * @param coded coded string observable, it should be extracted from A proprety
     * @param index rightful index
     * @return
     * @throws IllegalArgumentException throws an exception if the index does not meet coder format
     */
    String decode(ObservableStringValue coded, int index) throws IllegalArgumentException;
    /**
     * Defines the separator for the coding, first you define it then you code
     * @param separator
     * @return
     */
    String defineSeparator(String separator);

}
