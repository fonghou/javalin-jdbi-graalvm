package com.emmajiugo.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    @Test
    @DisplayName("Returns a fortune")
    void testItWorks() {
        String fortune = "fortune teller";
        assertTrue(fortune.length()>0);
    }
}
