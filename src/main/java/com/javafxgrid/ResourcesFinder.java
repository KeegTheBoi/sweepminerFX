package com.javafxgrid;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ResourcesFinder {

    private static final List<String> fxmlFiles = List.of(
        "primary.fxml",
        "settings.fxml",
        "grid.fxml",
        "guide.fxml"
    );

    private static final List<String> imagesStreams = List.of(
        "flag",
        "mine",
        "empty"
    );

    private static final int MAX_ADJAX_CELLS = 8;
    private static final String NUMBER_PREFIX = "_";
    private static final String EXTENSION = ".png"; 
    private static final String IMAGE_DIR = "images/";

    public static List<URL> FXMLfiles() {
        return fxmlFiles.stream().map(ResourcesFinder.class::getResource).toList();
        
    }

    public static Map<String, InputStream> allImages() {
        
        return Stream.concat(
            imagesStreams.stream()
            , IntStream.rangeClosed(0, MAX_ADJAX_CELLS)
                .mapToObj(String::valueOf)
                .map(NUMBER_PREFIX::concat)
            ).map(IMAGE_DIR::concat)
            .map(t -> t.concat(EXTENSION))
            .collect(
                Collectors.toMap(
                    i -> i.split("\\/")[1].split("\\.")[0],
                    ResourcesFinder.class::getResourceAsStream
                )
            );
                
    }
}
