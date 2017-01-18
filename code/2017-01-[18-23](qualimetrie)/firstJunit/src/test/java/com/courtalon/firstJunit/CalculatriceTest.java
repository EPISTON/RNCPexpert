package com.courtalon.firstJunit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class CalculatriceTest {

	private Calculatrice c;

	
	@BeforeClass
	public static void beforeAlltest() {
		System.out.println("avant tous les tests");
	}

	@AfterClass
	public static void afterAlltest() {
		System.out.println("après tous les tests");
	}

	
	@Before
	public void beforeTest() {
		System.out.println("before each test");
		c = new Calculatrice();
	}
	
	@After
	public void afterTest() {
		System.out.println("after each test");
		c = null;
	}
	
	
	
	@Test
	public void testAddition() {
		
		int expected = 36;
		int actual = c.addition(10, 26);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMultiplication() {
		
		int expected = 12;
		int actual = c.multiplication(3, 4);
		assertEquals(expected, actual);
	}
	
	@Ignore
	@Test
	public void testDivision() {
		int expected = 5;
		int actual = c.division(10, 2);
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testDivisionArrondi() {
		int expected = 3;
		int actual = c.division(10, 3);
		assertEquals(expected, actual);
	}
	
	@Test(expected=ArithmeticException.class)
	public void testDivisionParZero() {
		c.division(10, 0);
	}
	
	@Test(timeout=500)
	public void testCalculLong() {
		c.calculTresLong(3, 4);
	}
	
	
	
}
