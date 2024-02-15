package com.javafxgrid.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public record Coord(int x, int y) 
{
    public boolean isAdjax(Coord d) {
        return Math.abs(x - d.x()) <= 1 && Math.abs(y - d.y()) <= 1;
    }

    public static Coord fromString(String coordString) {
        Matcher match = Pattern.compile("(?<=\\=)\\d+").matcher(coordString);
        List<Integer> coord = IntStream.range(0, 2).boxed()
            .takeWhile(u -> match.find()).map(u -> Integer.parseInt(match.group())).toList();
        return new Coord(coord.get(0), coord.get(1));
    }

    public long hashIncremental() {
        long comb = (long)y << 32 | (x & 0xFFFFFFFFL);
        return comb >= 0 ? comb : -comb;
    }
}

