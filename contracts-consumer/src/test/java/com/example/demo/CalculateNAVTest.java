package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculateNAVTest {

    @Test
    public void CalculateStocktest() {

        var calculateNAV = new CalculateNAV(s -> BigDecimal.valueOf(6.01));
        BigDecimal actual = calculateNAV.CalculateStock("GO", 1000);

        assertThat(BigDecimal.valueOf(6010)).isEqualByComparingTo(actual);
    }

    @Test
    public void CalculateStocktest2() {

        var calculateNAV = new CalculateNAV(YahooFinance::getPrice);
        BigDecimal actual = calculateNAV.CalculateStock("GO", 1000);

        assertThat(BigDecimal.valueOf(0)).isEqualByComparingTo(actual);
    }

}