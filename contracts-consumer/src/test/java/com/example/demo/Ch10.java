package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.print.DocFlavor;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class Ch10 {

    @Test
    public void testMovies() {
        PriceService priceService = new PriceService();
        System.out.println(priceService.computePrice(Movie.Type.REGULAR, 2));
        System.out.println(priceService.computePrice(Movie.Type.NEW_RELEASE, 2));
        System.out.println(priceService.computePrice(Movie.Type.CHILDREN, 2));
    }

    @Test
    public void tt() {
        BiFunction<Integer, Integer, Integer> concat = (a, b) -> a*b;
        assertThat(12).isEqualTo(concat.apply(3, 4));
    }

    @Test
    public void dd2() {
        DoubleFunction<String> doubleFunction = d -> Double.toString(d);

        assertThat("123.456").isEqualTo(doubleFunction.apply(123.456));

        DoubleToIntFunction doubleToIntFunction = d-> (int) d;

        assertThat(123).isEqualTo(doubleToIntFunction.applyAsInt(123.456));
    }

    @Test
    public void Function_combine_TwoFunctions() {
        Function<String, String> loginUser = u -> u.toUpperCase();
        Function<Integer, String> toInteger = u -> String.valueOf(u);

        //loginUser.andThen(toInteger);
    }
}

class PriceService {
    public int computeNewReleasePrice(int days) {
        return (int) (days * 1);
    }

    public int computeRegularPrice(int days) {
        return days + 1;
    }

    public int computeChildrenPrice(int days) {
        return 5;
    }

    public int computePrice(Movie.Type type, int days) {
        return type.priceAlgo.apply(this, days);
    }
}

class Movie {
    enum Type {
        REGULAR(PriceService::computeRegularPrice),
        CHILDREN(PriceService::computeChildrenPrice),
        NEW_RELEASE(PriceService::computeNewReleasePrice);

        public final BiFunction<PriceService, Integer, Integer> priceAlgo;

        Type(BiFunction<PriceService, Integer, Integer> priceAlgo) {
            this.priceAlgo = priceAlgo;
        }
    }

    private final Type type;

    public Movie(Type type) {
        this.type = type;
    }

}


