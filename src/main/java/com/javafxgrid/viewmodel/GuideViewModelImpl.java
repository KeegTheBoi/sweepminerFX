package com.javafxgrid.viewmodel;

import java.util.*;
import java.util.stream.*;

import com.javafxgrid.model.cells.CellFactory;
import com.javafxgrid.model.cells.CellFactoryImpl;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.util.Pair;

public class GuideViewModelImpl extends AbstractCodedViewModel implements GuideViewModel {

    private static final String SEPARATOR = ":";
    public static final int TAG_INDEX = 0;
    public static final int DESCR_INDEX = 1;
    private static final int MAX_ADJAX = 8;

    private final List<ObservableStringValue> observerList;
    
    public GuideViewModelImpl() {
        super(SEPARATOR);
        CellFactory factory = new CellFactoryImpl();
        observerList = this.buildObserversList(factory);
    }
    
    private List<ObservableStringValue> buildObserversList(CellFactory factory) {
        return Stream.concat(
            IntStream.rangeClosed(0, MAX_ADJAX).boxed().map(factory::unmodifableGround),
            List.of(factory.unmodifableMine(),factory.unmodifableFlagger(), factory.unmodifableEmpty()).stream()
        ).map(u -> new Pair<>(u.fixedTag(), u.toString()))
        .map(this::buildEmbeddedString)
        .toList();
    }

    @Override
    public List<ObservableStringValue> mapObservers() {
        return this.observerList;
    }

    
    private ObservableStringValue buildEmbeddedString(Pair<String, String> embeddedString) {
        StringExpression tagProp = new SimpleStringProperty(embeddedString.getKey());
        StringExpression descrProp = new SimpleStringProperty(embeddedString.getValue());
        return this.embeddedStringCoder().codeObservableValue(List.of(tagProp, descrProp));

    }

    @Override
    public String decoding(int listIndex, int codingIndex) {
        ObservableStringValue embeddedString = this.mapObservers().get(listIndex);
        return this.embeddedStringCoder().decode(embeddedString, codingIndex);
    }

    
    
}
