/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author KAVINDU DILSHAN
 */
public class ValidateTest {
    
    public ValidateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of checkEmail method, of class Validate.
     */
    @Test
    public void testCheckEmail() {
        System.out.println("Check Email");
        String email = "kavindu@gmail.com";
        Validate instance = new Validate();
        boolean expResult = true;
        boolean result = instance.checkEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of checkValidNumber method, of class Validate.
     */
    @Test
    public void testCheckValidNumber() {
        System.out.println("Check Number");
        String mobile = "0701961081";
        Validate instance = new Validate();
        boolean expResult = true;
        boolean result = instance.checkValidNumber(mobile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
