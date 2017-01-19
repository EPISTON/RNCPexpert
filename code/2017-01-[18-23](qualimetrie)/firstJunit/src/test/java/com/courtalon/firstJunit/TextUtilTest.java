package com.courtalon.firstJunit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;



import static org.junit.Assert.*;
public class TextUtilTest {
	
	private TextUtil tu;
	
	@Before
	public void initTu() {
		tu = new TextUtil();
	}
	

	@After
	public void cleanTu() {
		tu = null;
	}

	@Category(FastTests.class)
	@Test
	public void testChaineBizarre() {
		String expected = "214373687";
		String initial = "214373687";
		String actual = tu.capitalise(initial);
		
		assertEquals("la chaine numerique n'est pas correctement capitalise", expected, actual);
		
		initial = "#'__--/op";
		expected = "#'__--/op";
		actual = tu.capitalise(initial);
		
		assertEquals("la chaine speciale n'est pas correctement capitalise", expected, actual);
		
		initial = "éléanore";
		expected = "Éléanore";
		actual = tu.capitalise(initial);
		
		assertEquals("la chaine accentuée n'est pas correctement capitalise", expected, actual);
		
	}
	

	@Category(FastTests.class)
	@Test
	public void testNomPersonne() {
		String expected = "Marcelus";
		String initial = "marcelus";
		String actual = tu.capitalise(initial);
		
		assertEquals("le nom n'est pas correctement capitalise", expected, actual);
	}

	@Category(FastTests.class)
	@Test
	public void testDejaMajuscule() {
		String expected = "Marcelus";
		String initial = "Marcelus";
		String actual = tu.capitalise(initial);
		
		assertEquals("le nom n'est pas correctement capitalise", expected, actual);
	}

	@Category(FastTests.class)
	@Test
	public void testchaineCourte() {
		String expected = "M";
		String initial = "m";
		String actual = tu.capitalise(initial);
		
		assertEquals("la chaine courte n'est pas correctement capitalise", expected, actual);
	}

	@Category(SlowTests.class)
	@Test
	public void testchaineVide() {
		String expected = "";
		String initial = "";
		String actual = tu.capitalise(initial);
		
		assertEquals("la chaine vide n'est pas correctement capitalise", expected, actual);
	}
	
	@Category(SlowTests.class)
	@Test
	public void testchaineNull() {
		String expected = null;
		String initial = null;
		String actual = tu.capitalise(initial);
		
		assertEquals("la chaine vide n'est pas correctement capitalise", expected, actual);
	}

	
	@Category(SlowTests.class)
	@Test
	public void testInverseSimple() {
		String expected = "ruojnob";
		String initial = "bonjour";
		String actual = tu.inverse(initial);
		assertEquals("la chaine est mal inversée", expected, actual);
	}
	
}
