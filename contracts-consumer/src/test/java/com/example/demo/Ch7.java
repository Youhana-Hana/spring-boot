package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StopWatch;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.example.demo.TailCalls.call;
import static com.example.demo.TailCalls.done;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class Ch7 {
    @Test
    public void getFactorial() {
        var result = Factorial.compute(5);
        assertThat(120).isEqualTo(result);
    }

    @Test
    public void getFactorialL() {
        var result = Factorial.compute(20);
        assertThat(result).isEqualTo(2102132736);
    }

    @Test
    public void getFactorialL3() {
        var result = Factorial.compute2(BigInteger.ONE, new BigInteger("20")).invoke();
        assertThat(new BigInteger("2432902008176640000")).isEqualTo(result);
    }

    @Test
    public void testPrices() {
        final List<Integer> pricesValues = Arrays.asList(2, 1, 1, 2, 2, 2, 1, 8, 9, 15);
        RodCutterBasic rodCutterBasic = new RodCutterBasic(pricesValues);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int maxProfit = rodCutterBasic.maxProfit3(200);
        stopWatch.stop();
        System.out.println(maxProfit);
        System.out.println(stopWatch);

        String val = "";
    }
}

class Factorial {
    public static int compute(final int number) {
        if (number == 1) {
            return 1;
        }

        return number * compute(number - 1);
    }

    public static TailCall<BigInteger> compute2(final BigInteger factorial, final BigInteger number) {
        System.out.println(String.format("factorial: %d, number: %d", factorial, number));

        if (number.equals(BigInteger.ONE)) {
            return done(factorial);
        }

        return call(() -> compute2(factorial.multiply(number), number.subtract(BigInteger.ONE)));
    }
}


class RodCutterBasic {
    final List<Integer> prices;


    public RodCutterBasic(List<Integer> prices) {
        this.prices = prices;
    }

    private static int count = 0;

    public int maxProfit(final int length) {
        int profit = (length <= prices.size()) ? prices.get(length - 1) : 0;
        System.out.println(String.format("prices.size(): %d, length: %d, profit: %d", prices.size(), length, profit));
        count++;
        for (int i = 1; i < length; i++) {
            System.out.println(String.format("i: %d, length: %d", i, length));

            int priceWhenCut = maxProfit(i) + maxProfit(length - i);
            System.out.println(String.format("i: %d, length: %d, priceWhenCut: %d", i, length, priceWhenCut));

            if (profit < priceWhenCut) profit = priceWhenCut;
        }

        System.out.println("count: " + count);
        return profit;
    }

    public int maxProfit2(final int rodLength) {
        BiFunction<Function<Integer, Integer>, Integer, Integer> compute =
                (func, length) -> {
                    int profit = (length <= prices.size()) ? prices.get(length - 1) : 0;
                    for (int i = 1; i < length; i++) {
                        int priceWhenCut = func.apply(i) + func.apply(length - i);
                        if (profit < priceWhenCut) profit = priceWhenCut;
                    }
                    return profit;
                };
        return Memoizer.callMemoized(compute, rodLength);
    }

    public int maxProfitMem(final int rodLength) {
        BiFunction<Function<Integer, Integer>, Integer, Integer> compute =
                (func, length) -> {
                    int profit = (length <= prices.size()) ? prices.get(length - 1) : 0;
                    for (int i = 1; i < length; i++) {
                        int priceCut = func.apply(i) + func.apply(length - 1);
                        if (profit < priceCut) profit = priceCut;
                    }

                    return profit;
                };

        return Memoizer.callMemoized(compute, rodLength);
    }


    public int maxProfit3(final int rodLenth) {

        return callMemoized(
                (final Function<Integer, Integer> func, final Integer length) -> {
                    System.out.println("func + " + length);

                    int profit = (length <= prices.size()) ? prices.get(length - 1) : 0;
                    for (int i = 1; i < length; i++) {
                        int priceWhenCut = func.apply(i) + func.apply(length - i);
                        if (profit < priceWhenCut) profit = priceWhenCut;
                    }
                    return profit;
                }, rodLenth);
    }


    public static <T, R> R callMemoized(final BiFunction<Function<T, R>, T, R> function, final T input) {

        Function<T, R> memoized = new Function<T, R>() {
            private final Map<T, R> store = new HashMap<>();

            public R apply(final T input) {
                System.out.println("store " + input);

                var result = store.containsKey(input) ? store.get(input) : function.apply(this, input);
                store.put(input, result);
                return result;
//                return store.computeIfAbsent(input, key -> function.apply(this, key));
            }
        };
        System.out.println("memoized.apply " + input);
        return memoized.apply(input);
    }
}

class Memoizer {
    public static <T, R> R callMemoized(
            final BiFunction<Function<T, R>, T, R> function, final T input) {

        Function<T, R> memoized = new Function<T, R>() {
            private final Map<T, R> store = new ConcurrentHashMap<>();

            public R apply(final T input) {
                return store.computeIfAbsent(input, key -> function.apply(this, key));
            }
        };

        return memoized.apply(input);
    }
}

//class Memoizer {
//    public static <T, R> R callMemorized(final BiFunction<Function<T, R>, T, R> function, final T input) {
//        Function<T, R> memorized = new Function<T, R>() {
//            private final Map<T, R> store = new HashMap<>();
//            public R apply(final T input) {
//                return store.computeIfAbsent(input, key -> function.apply(this, key));
//            }
//        };
//
//        return memorized.apply(input);
//    }
//}