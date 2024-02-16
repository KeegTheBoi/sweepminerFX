package com.javafxgrid.model.cells;

import java.util.function.*;

public class CellsUtils {
    
    public static boolean isBomb(Cell c) {
        return c.getType() == Type.MINE;
    }

    public static boolean isValuable(Cell c) {
        return c.getCount().isPresent(); //or c.getType == Type.GROUND
    }

    public static boolean isEmpty(Cell c) {
        return c.getCount().isPresent() && c.getCount().get() == 0;
    }

    public static boolean isVeiled(Cell c) {
        return c.visibilityObservable().getValue() == false;
    }

    public static Function<Integer, String> groundMapper() {
        return x -> CellFactoryImpl.GROUND_PREFIX.concat(String.valueOf(x));
    }
}
