package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@RunWith(MockitoJUnitRunner.class)
public class Ch9 {

    @Test
    public void getAllUsers() {
        var users = new UserRepo().findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        System.out.println(users);
    }

    @Test
    public void getAllOrders() {
        var orders = Arrays.asList(new Order());


        Map<Product, Integer> productIntegerMap = orders.stream()
                .filter(o -> o.getCreationDate().isAfter(LocalDate.now().minusYears(1)))
                .flatMap(o -> o.getLines().stream())
                .collect(groupingBy(OrderLine::getProdcut, summingInt(OrderLine::getItemCount)));

        List<Product> collect = productIntegerMap
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= 10)
                .map(Map.Entry::getKey)
                .filter(p -> !p.isDeleted())
                .filter(p -> p.getId())
                .collect(Collectors.toList());


        System.out.println(orders);
    }

    @Test
    public void testCards() {
        DiscountService discountService = new DiscountService();
        System.out.println(discountService.getDiscountLine(new Customer(new MemberCard(60))));
        System.out.println(discountService.getDiscountLine(new Customer(new MemberCard(10))));
        System.out.println(discountService.getDiscountLine(new Customer(null)));
    }
}

class DiscountService {
    public String getDiscountLine(Customer customer) {
        Optional<Integer> integer = customer.getMemberCard()
                .flatMap(this::getDiscountPercentage);

        Optional<Optional<Integer>> integer1 = customer.getMemberCard()
                .map(this::getDiscountPercentage);


        return customer.getMemberCard()
                .flatMap(this::getDiscountPercentage)
                .map(v -> "discount" + v)
                .orElse("");

    }

    private Optional<Integer> getDiscountPercentage(MemberCard card) {
        if (card.getFidelityPoints() >= 100) {
            return Optional.of(5);
        }
        if (card.getFidelityPoints() >= 50) {
            return Optional.of(3);
        }

        return Optional.empty();
    }
}

class MemberCard {
    private int fidelityPoints;

    public MemberCard(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }
}

class Customer {
    public Customer(MemberCard memberCard) {
        this.memberCard = memberCard;
    }

    private MemberCard memberCard;

    public Optional<MemberCard> getMemberCard() {
        return Optional.ofNullable(memberCard);
    }
}

class Order {
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    private LocalDate creationDate;

    List<OrderLine> lines;
}

class OrderLine {
    public Product getProdcut() {
        return prodcut;
    }

    private Product prodcut;

    private int count;

    public int getItemCount() {
        return count;
    }


}

class Product {
    public boolean isDeleted() {
        return false;
    }

    public boolean getId() {
        return true;
    }
}

class UserDto {

    public UserDto(User user) {
        username = user.getUserName();
        fullName = user.getFirstName() + " " + user.getLastName();
        active = user.getDeactivationDate() == null;
    }

    private String username;
    private String fullName;
    private boolean active;
}

class User {
    public User(String firstName, String lastName, Date deactivationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deactivationDate = deactivationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    private String firstName;
    private String lastName;
    private Date deactivationDate;

    public String getUserName() {
        return "";
    }
}

class UserRepo {
    public List<User> findAll() {
        return Arrays.asList(new User("f", "l", null));
    }
}
