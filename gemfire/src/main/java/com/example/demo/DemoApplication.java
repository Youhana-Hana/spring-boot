package com.example.demo;

import com.example.demo.hello.Quote;
import com.example.demo.hello.QuoteService;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;

import java.util.Optional;


//@EnableGemfireRepositories
//@EnableEntityDefinedRegions(basePackageClasses = Person.class, clientRegionShortcut = ClientRegionShortcut.LOCAL)

@SpringBootApplication
@EnableGemfireCaching
@ClientCacheApplication(name = "CachingGemFireApplication", logLevel = "error")
@EnableCachingDefinedRegions(clientRegionShortcut = ClientRegionShortcut.LOCAL)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    ApplicationRunner run(QuoteService quoteService) {
        return args -> {

            Quote quote = requestQuote(quoteService, 12L);
            requestQuote(quoteService, quote.getId());
            requestQuote(quoteService, 10L);
            quote  = requestQuote(quoteService, null);
            requestQuote(quoteService, quote.getId());

        };
    }

    private Quote requestQuote(QuoteService quoteService, Long id) {
        long startTime = System.currentTimeMillis();
        Quote quote = Optional.ofNullable(id)
                .map(quoteService::requestQuote)
                .orElseGet(quoteService::requestRandomQuote);

        long elapsedTime = System.currentTimeMillis();

        System.out.printf("\"%1$s\"%nCache Miss [%2$s] - Elapsed Time [%3$s ms]%n", quote,
                quoteService.isCacheMiss(), (elapsedTime - startTime));

        return quote;
    }
//    @Bean
//    ApplicationRunner run(PersonRepository personRepository) {
//        return args -> {
//            Person alice = new Person("Adult Alice", 40);
//            Person bob = new Person("Baby Bob", 1);
//            Person carol = new Person("Teen Carol", 13);
//
//            System.out.println("Before accessing data in Gemfire");
//
//            asList(alice, bob, carol).forEach(person -> System.out.println("\t" + person));
//
//            System.out.println("Saving Alice, Bob and Carol");
//
//            asList(alice, bob, carol).forEach(personRepository::save);
////
//            System.out.println("Lookup each person by name...");
//
//            asList(alice.getName(), bob.getName(), carol.getName()).forEach(name -> System.out.println("\t" + personRepository.findByName(name)));
//
//            System.out.println("Query adults (over 18):");
//
//            stream(personRepository.findByAgeGreaterThan(18).spliterator(), false).forEach(person -> System.out.println("\t "+ person));
//
//            System.out.println("Query babies (less than 5):");
//            stream(personRepository.findByAgeLessThan(5).spliterator(), false).forEach(person -> System.out.println("\t "+ person));
//
//            System.out.println("Query teens (between 12 and 20):");
//            stream(personRepository.findByAgeGreaterThanAndAgeLessThan(12, 20).spliterator(), false).forEach(person -> System.out.println("\t "+ person));
//        };
//    }

}
