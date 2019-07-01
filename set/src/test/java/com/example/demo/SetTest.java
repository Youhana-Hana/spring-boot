package com.example.demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetTest {

    private Set<String> set;

    @Test
    public void test() {
        set = new HashSet<String>();

        var result = set.add("Youhana");
        System.out.println(result);

        result = set.add("Youhana");
        System.out.println(result);

        result = set.isEmpty();
        System.out.println(result);

        result = set.contains("Youhana");
        System.out.println(result);

        result = set.addAll(Arrays.asList("Carl", "Youhana"));
        System.out.println(result);
        System.out.println(set.size());


        result = set.remove("Carl");
        System.out.println(result);

        result = set.remove("Carl");
        System.out.println(result);
        System.out.println(set.size());

        set.forEach(System.out::println);

        for(var e : set) {
            System.out.println(e);
        }

    }

    @Test
    public void Test2() {
        var set = new MySet<String>();
        var result = set.add("Youhana");
        System.out.println(result);

        result = set.add("Youhana");
        System.out.println(result);

        result = set.isEmpty();
        System.out.println(result);

        result = set.contains("Youhana");
        System.out.println(result);

        result = set.addAll(Arrays.asList("Carl", "Youhana"));

        System.out.println(result);
        System.out.println(set.size());
        for(var e : set) {
            System.out.println(e);
        }

        result = set.remove("Carl");
        System.out.println(result);

        result = set.remove("Carl");
        System.out.println(result);
        System.out.println(set.size());

        set.forEach(System.out::println);

        for(var e : set) {
            System.out.println(e);
        }


        System.out.println(set.toString());
        System.out.println(32 >> 4);
    }
}
