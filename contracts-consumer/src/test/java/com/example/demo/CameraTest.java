package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CameraTest {

    @Test
    public void empty() {

        var camera= new Camera();
        camera.setFilters();

        final Consumer<String> printedCapture = (filterInfo) ->
                System.out.println(String.format("With %s: %s", filterInfo, camera.capture(new Color(200, 200, 200))));

        printedCapture.accept("XXXX");
    }


    @Test
    public void Full() {

        var camera= new Camera();
        camera.setFilters(Color::brighter);

        final Consumer<String> printedCapture = (filterInfo) ->
                System.out.println(String.format("With %s: %s", filterInfo, camera.capture(new Color(200, 100, 200))));

        printedCapture.accept("Chaind");
    }

    @Test
    public void Mailer() {

        FluentMailer.send(mailer -> mailer.from("xxx").to("zxxxx"));

    }
}