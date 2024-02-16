package com.javafxgrid.viewmodel;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.util.Pair;

import java.util.List;

import com.javafxgrid.viewmodel.appmediators.BackerViewModel;

public interface GuideViewModel extends ViewModel, BackerViewModel, CodedViewModel{

    List<ObservableStringValue> mapObservers();

    String decoding(int listIndex, int codingIndex);
    
}
