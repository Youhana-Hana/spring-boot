package com.example.demo;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class BaseClass {
    @Autowired
    Controller controller;

    @MockBean
    PersonService personService;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(controller);

        when(this.personService.findById(1L)).thenReturn(new Person(1L, "Richard", "Fin"));
        when(this.personService.getAll())
                .thenReturn(Arrays.asList(new Person(1L, "R", "F"), new Person(2L, "J", "G")));
    }
}
