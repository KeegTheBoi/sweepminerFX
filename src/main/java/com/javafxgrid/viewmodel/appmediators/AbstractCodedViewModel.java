package com.javafxgrid.viewmodel.appmediators;

import com.javafxgrid.utils.EmbeddedStringCoder;
import com.javafxgrid.utils.EmbedderStringImpl;

public abstract class AbstractCodedViewModel extends AbstractBackerViewModel implements CodedViewModel{

    private final String separator;
    private final EmbeddedStringCoder code;
    
    protected AbstractCodedViewModel(String separator) {
        this.separator = separator;
        code = new EmbedderStringImpl();
        code.defineSeparator(this.separator);
    }
    
    @Override
    public EmbeddedStringCoder embeddedStringCoder() {
        return code;
    }

     
    
}
