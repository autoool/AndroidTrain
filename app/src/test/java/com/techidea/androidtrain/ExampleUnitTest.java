package com.techidea.androidtrain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void tryCatchTest() {
        try {
            throw  new RuntimeException("error");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace().clone());
            System.out.println();
        }
    }
}