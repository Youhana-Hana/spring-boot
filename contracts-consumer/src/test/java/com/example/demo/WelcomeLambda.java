package com.example.demo;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.springframework.cglib.core.Block;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import static com.example.demo.WelcomeLambda.Asset.AssetType.BOND;
import static com.example.demo.WelcomeLambda.Asset.AssetType.STOCK;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class WelcomeLambda {

    @Test
    public void welcomeList() {
        final List<BigDecimal> prices = Arrays.asList(
                new BigDecimal(10), new BigDecimal(30), new BigDecimal(17),
                new BigDecimal(20), new BigDecimal(15), new BigDecimal(18),
                new BigDecimal(45), new BigDecimal(12)
        );

        BigDecimal totalPrices = BigDecimal.ZERO;
        for (BigDecimal price : prices) {
            if (price.compareTo(BigDecimal.valueOf(20)) > 0)
                totalPrices = totalPrices.add(price.multiply(BigDecimal.valueOf(0.9)));
        }

        System.out.println("Total of discounted prices: " + totalPrices);

        var total = prices.stream()
                .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, (bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2));

        System.out.println("Total of discounted prices: " + total);
    }

    @Test
    public void list() {
        final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        for (String s : names)
            System.out.println(s);

        System.out.println("------------------------------------------");
        for (int i = 0; i < names.size(); i++)
            System.out.println(names.get(i));

        System.out.println("------------------------------------------");
        names.forEach(System.out::println);
    }

    @Test
    public void upperCaselist() {
        final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        final List<String> upperNames = new ArrayList<>();
        for (String s : names)
            upperNames.add(s.toUpperCase());


        System.out.println("------------------------------------------");
        upperNames.forEach(System.out::println);

        System.out.println("------------------------------------------");
        names.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    @Test
    public void findlist() {
        final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        final List<String> upperNames = new ArrayList<>();
        for (String s : names) {
            if (s.startsWith("N"))
                upperNames.add(s);
        }

        System.out.println("------------------------------------------");
        List<String> n = names.stream()
                .filter(s -> s.startsWith("N"))
                .collect(toList());

        n.forEach(System.out::println);
    }


    private Predicate<String> check(final String letter) {
        return s -> s.startsWith(letter);
    }

    @Test
    public void predicatelist() {
        final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        Predicate<String> find = s -> s.startsWith("N");

        System.out.println("------------------------------------------");
        List<String> n = names.stream()
                .filter(check("N"))
                .collect(toList());

        n.forEach(System.out::println);

        System.out.println("------------------------------------------");
        List<String> n2 = names.stream()
                .filter(check("N"))
                .collect(toList());

        n2.forEach(System.out::println);

        final Function<String, Predicate<String>> checkF1 =
                (String letter) -> {
                    Predicate<String> xx = (String name) -> name.startsWith(letter);
                    return xx;
                };

        final Function<String, Predicate<String>> x = letter -> name -> name.contains(letter);

        final Function<String, Predicate<String>> checkF2 = (String letter) -> (String name) -> name.startsWith(letter);

        final Function<String, Predicate<String>> checkFunction =
                letter -> name -> name.startsWith(letter);

        System.out.println("------------------------------------------");
        List<String> n3 = names.stream()
                .filter(checkFunction.apply("N"))
                .collect(toList());

        n3.forEach(System.out::println);

    }

    @Test
    public void findInlist() {
        final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        String foundName = null;
        for (final String name : names) {
            if (name.startsWith("N")) {
                foundName = name;
                break;
            }
        }

        if (foundName == null) {
            System.out.println("No name found");
        } else {
            System.out.println(foundName);
        }


        Optional<String> found = names.stream()
                .filter(s -> s.startsWith("xxx"))
                .findFirst();


        System.out.println(String.format("a name started with %s, %s", "xxx", found.orElse("not found")));
    }

    @Test
    public void findLongestName() {
        final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");


        String s1 = names.stream()
                .reduce("Steve", (s, s2) -> s.length() > s2.length() ? s : s2);

        var total = names.stream()
                .mapToInt(s -> s.length())
                .reduce(0, (l1, l2) -> l1 + l2);


        System.out.println(String.format("a name started with %s, %s", "xxx", s1));
        System.out.println(String.format("a name started with %s, %s", "xxx", total));
    }


    @Test
    public void join() {
        final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");


        String collect = names.stream()
                .map(String::toUpperCase)
                .collect(joining(","));

        System.out.println(String.format("a name started with %s, %s", "xxx", collect));
    }


    @Test
    public void comparator() {
        final List<Person> persons = Arrays.asList(
                new Person("John", 20),
                new Person("Sara", 21),
                new Person("Jane", 21),
                new Person("Greg", 35));


        Comparator<Person> ascendingOrder = (p1, p2) -> p1.ageDifference(p2);

        List<Person> ascendingAge = persons.stream()
                .sorted(ascendingOrder)
                .collect(toList());

        printPeople("Sorted in ascending order by age: ", ascendingAge);

        List<Person> descendingAge = persons.stream()
                .sorted(ascendingOrder.reversed())
                .collect(toList());

        printPeople("Sorted in ascending order by age: ", descendingAge);

        System.out.println("----------------------------------");
        persons.stream()
                .min(ascendingOrder)
                .ifPresent(y -> System.out.println(y));
        System.out.println("----------------------------------");

        final Function<Person, String> byName = person -> person.getName();
        final Function<Person, Integer> byAge = person -> person.getAge();

        List<Person> collect = persons.stream()
                .sorted(comparing(byAge).thenComparing(byName))
                .collect(toList());

        printPeople("Sorted in ascending order by age: ", collect);
    }

    @Test
    public void collectors() {
        final List<Person> persons = Arrays.asList(
                new Person("John", 20),
                new Person("Sara", 21),
                new Person("Jane", 21),
                new Person("Greg", 35));


        Map<Integer, List<Person>> collect = persons.stream()
                .collect(groupingBy(Person::getAge));

        System.out.println(collect);

        Map<Integer, List<String>> collect1 = persons.stream()
                .collect(groupingBy(Person::getAge, mapping(Person::getName, toList())));

        System.out.println(collect1);


        Map<Character, Optional<Person>> collect2 = persons.stream()
                .collect(groupingBy(p -> p.getName().charAt(0),
                        reducing(BinaryOperator.maxBy(comparing(Person::getAge)))));

        System.out.println(collect2);

        Map<Character, Optional<Person>> collect3 = persons.stream()
                .collect(groupingBy(p -> p.getName().charAt(0),
                        reducing((o1, o2) -> o1.getAge() > o2.getAge() ? o1 : o2)));

        System.out.println(collect3);
    }

    @Test
    public void assets() {
        final List<Asset> assets = Arrays.asList(
                new Asset(BOND, 1000),
                new Asset(BOND, 2000),
                new Asset(STOCK, 3000),
                new Asset(STOCK, 4000));

        final Function<Asset.AssetType, Predicate<Asset>> assetTypePredicateFunction = type -> asset -> asset.getAssetType() == type;

        var total = getTotalAssets(assets, asset -> true);
        var total1 = getTotalAssets(assets, assetTypePredicateFunction.apply(BOND));
        var total2 = getTotalAssets(assets, assetTypePredicateFunction.apply(STOCK));

        System.out.println(total);
        System.out.println(total1);
        System.out.println(total2);
    }

    private int getTotalAssets(final List<Asset> assets, final Predicate<Asset> assetPredicate) {
        return assets.stream()
                .filter(assetPredicate)
                .mapToInt(Asset::getAssetValue)
                .sum();
    }

    private static void printChar(int aChar) {
        System.out.println((char) (aChar));
    }

    public static void printPeople(
            final String message, final List<Person> people) {
        System.out.println(message);
        people.forEach(System.out::println);
    }

    public class Person {

        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public int ageDifference(final Person p) {
            return age - p.getAge();
        }

        public String toString() {
            return String.format("%s - %d", name, age);
        }
    }

    public static class Asset {
        public enum AssetType {BOND, STOCK}

        private final AssetType assetType;
        private final int assetValue;

        public AssetType getAssetType() {
            return assetType;
        }

        public int getAssetValue() {
            return assetValue;
        }

        public Asset(final AssetType assetType, final int assetValue) {
            this.assetType = assetType;
            this.assetValue = assetValue;
        }
    }


}

