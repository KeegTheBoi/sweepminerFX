package com.javafxgrid.utils;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

public interface Coder {

    ObservableStringValue code(StringProperty toBeCoded);

}
