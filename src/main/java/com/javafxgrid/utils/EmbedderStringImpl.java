package com.javafxgrid.utils;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;

import java.util.*;

public class EmbedderStringImpl implements EmbeddedStringCoder {

    private String separator;
    private ObservableStringValue current;

    @Override
    public ObservableStringValue codeObservableValue(List<StringExpression> prop) {
        if(separator.isEmpty()) {
            throw new IllegalStateException("Separator has not been defined");
        } else if(prop.stream()
                .map(StringExpression::get)
                .anyMatch(s -> s.contains(separator))
            ) {
            throw new IllegalStateException("Separator has been found in your strings, coding not possible");
        }
        current = prop.stream().reduce((a, b) -> a.concat(separator).concat(b)).orElse(new SimpleStringProperty());
        return current;
    }

    @Override
    public String decode(ObservableStringValue obsString, int index) {
        var decoded = obsString.get().split(this.separator);
        if(index >= decoded.length || index < 0) {
            throw new IllegalArgumentException("Index does not match observable format, Index provided → " + index + " Bounds → " + decoded.length);
        }
        return decoded[index];
    }

    @Override
    public String defineSeparator(String separator) {
        return this.separator = separator;
    }
}
