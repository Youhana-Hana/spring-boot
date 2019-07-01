package com.example.demo;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Camera {

    private Function<Color, Color> filter;


    public void setFilters(Function<Color, Color>...filters) {
        filter = Stream.of(filters)
                .reduce((filter, next) -> filter.compose(next))
                .orElse(Function.identity());
    }

    public Color capture(final Color colorInput) {
        return filter.apply(colorInput);
    }
}
