package com.javafxgrid.viewmodel;

import com.javafxgrid.utils.EmbeddedStringCoder;
import com.javafxgrid.utils.EmbedderStringImpl;
import com.javafxgrid.viewmodel.appmediators.AbstractBackerViewModel;

public abstract class AbstractCodedViewModel extends AbstractBackerViewModel implements CodedViewModel{

    private final String separator;
    private final EmbeddedStringCoder code;
    
    public AbstractCodedViewModel(String separator) {
        this.separator = separator;
        code = new EmbedderStringImpl();
        code.defineSeparator(this.separator);
    }
    
    @Override
    public EmbeddedStringCoder embeddedStringCoder() {
        return code;
    }

     
    
}
