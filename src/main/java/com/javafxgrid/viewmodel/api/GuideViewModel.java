package com.javafxgrid.viewmodel.api;

import javafx.beans.value.ObservableStringValue;

import java.util.List;

import com.javafxgrid.viewmodel.ViewModel;
import com.javafxgrid.viewmodel.appmediators.BackerViewModel;
import com.javafxgrid.viewmodel.appmediators.CodedViewModel;

public interface GuideViewModel extends ViewModel, BackerViewModel, CodedViewModel{

    List<ObservableStringValue> mapObservers();

    String decoding(int listIndex, int codingIndex);
    
}
