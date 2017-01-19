package com.courtalon.firstJunit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;



@Category(SlowTests.class)
public class CalculatriceTest {
	
	
	private Calculatrice c;

	// appelé une fois avant la suite de test
	// mise en place de l'environnement "global" de cette suite de test
	@BeforeClass
	public static void beforeAlltest() {
		System.out.println("avant tous les tests");
	}
	
	// appelé une fois après la suite de test
	@AfterClass
	public static void afterAlltest() {
		System.out.println("après tous les tests");
	}

	// appelé avant chaque test, pour préparer l'environnement
	@Before
	public void beforeTest() {
		System.out.println("before each test");
		c = new Calculatrice();
	}
	
	// nettoyage après chaque test
	@After
	public void afterTest() {
		System.out.println("after each test");
		c = null;
	}
	
	
	// une methode annotée avec @Test
	// est un test junit (4+)
	// ce test echouera si une des assertions executée pendant ce test échoue
	// ceci pass par le levée d'une exception qui sera détectée par junit
	@Test
	public void testAddition() {
		
		int expected = 36;
		int actual = c.addition(10, 26);
		// cette assertion vérifie un égalité
		assertEquals("l'addition ne marche pas!!!", expected, actual);
	}
	
	@Test
	public void testMultiplication() {
		
		int expected = 12;
		int actual = c.multiplication(3, 4);
		assertEquals("la multiplication ne fonctionne pas!!",expected, actual);
	}
	
	// si j'ai besoin de temporairement désactiver un test, mais je ne veux pas l'effacer
	// @Ignore permet d'indiquer a Junit de "sauter" ce test
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
	
	
	// certain test sont réussi si une erreur est déclenchée
	// l'argument expected permet de spécifier cela (l'exception attendue)
	@Test(expected=ArithmeticException.class)
	public void testDivisionParZero() {
		c.division(10, 0);
	}
	
	
	// on peut aussi spécifier un timeout (un temps d'execution maximal)
	// pour un test
	@Ignore
	@Test(timeout=500)
	public void testCalculLong() {
		c.calculTresLong(3, 4);
	}
	
	
	@Test
	public void testRacine() {
		double expected = 3.0;
		
		double actual = c.calculRacine(9.0);
		// toujours comparer des doubles (egalite) en fournissant un delta
		// c'est a dire la précision minimal pour l'egalite
		// si: abs(expected - actual) < delta -> egal
		assertEquals("racine carrée ne marche pas", expected, actual, 0.00000001);
		
	}
	
	
}
