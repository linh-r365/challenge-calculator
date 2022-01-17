package org.calculator;

import org.calculator.components.Calculator;
import org.calculator.components.Settings;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    public CalculatorTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(DIMainConfig.class);
        calculator = context.getBean(Calculator.class);
    }
    @Test
    void stringToNumbers() {
        try {
            calculator.stringToNumbers("1, 2");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("Support a maximum of 2 numbers", e.getMessage());
        }

        try {
            assertArrayEquals(new Integer[] {1,2}, calculator.stringToNumbers("1, 2"));
            assertArrayEquals(new Integer[] {1,2}, calculator.stringToNumbers("1,2"));
            assertArrayEquals(new Integer[] {0}, calculator.stringToNumbers(""));
            assertArrayEquals(new Integer[] {2,0,6}, calculator.stringToNumbers("2,1001,6"));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    void calc() {
        assertEquals(6, calculator.calc(new Integer[]{1,2,3}));
        assertEquals(1, calculator.calc(new Integer[]{4,-3}));
    }

    @Test
    void getFormula() {
        assertEquals("1+2+3", calculator.getFormula(new Integer[]{1,2,3}));
    }
}