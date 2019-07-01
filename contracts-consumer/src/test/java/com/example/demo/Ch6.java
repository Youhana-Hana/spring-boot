package com.example.demo;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.google.common.base.Supplier;
import groovy.transform.Synchronized;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(ConcurrentTestRunner.class)
public class Ch6 {

//    private final static int THREAD_COUNT = 5;
//    //HolderNaive holderNaive = new HolderNaive();
//
//    HolderNaive2 holderNaive2 = new HolderNaive2();
//
////    @Test
////    @ThreadCount(THREAD_COUNT)
////    public void testHeavy() {
////        Heavy heavy = holderNaive.getHeavy();
////        System.out.println(heavy.toString());
////    }
//
//    @Test
//    @ThreadCount(THREAD_COUNT)
//    public void testHeavy2() {
//        Heavy heavy = holderNaive2.getHeavy();
//        System.out.println(heavy.toString());
//    }
//
//    @Test
//    public void testHeavy3() {
//        Heavy heavy = holderNaive2.getHeavy();
//        System.out.println(heavy.toString());
//    }

    @Test
    public void getPrimes() {
        Primes.primes(2, 10);
    }
}


class Primes {
    private static int primeAfter(final int number) {
        if (isPrime(number + 1)) {
            return number + 1;
        } else {
            return primeAfter(number + 1);
        }
    }

    private static boolean isPrime(int number) {
        return number > 1 &&
                IntStream.rangeClosed(2, (int) Math.sqrt(number))
                        .noneMatch(advisor -> number % advisor == 0);
    }

    public static List<Integer> primes(int number, int count) {
        List<Integer> collect = Stream.iterate(primeAfter(number - 1), Primes::primeAfter)
                .limit(count)
                .collect(Collectors.toList());

        System.out.println(collect);
        return collect;
    }
}

class Heavy {
    public Heavy() {
        System.out.println("Heavy created");
    }

    public String toString() {
        return "quite heavy";
    }
}

class HolderNaive {
    private Heavy heavy;

    public HolderNaive() {
        System.out.println("Holder created");
    }

    public synchronized Heavy getHeavy() {
        if (heavy == null) {
            heavy = new Heavy();
        }

        return heavy;
    }
}

class HolderNaive2 {

    private Supplier<Heavy> heavy = () -> createCashedHeavy();

    private synchronized Heavy createCashedHeavy() {

        class HeavyFactory implements Supplier<Heavy> {

            private final Heavy instance = new Heavy();

            public Heavy get() {
                return instance;
            }
        }

        if(!HeavyFactory.class.isInstance(heavy)) {
            heavy = new HeavyFactory();
        }

        return heavy.get();
    }

    public HolderNaive2() {
        System.out.println("HolderNaive 2");
    }

    public Heavy getHeavy() {
        return this.heavy.get();
    }
}

