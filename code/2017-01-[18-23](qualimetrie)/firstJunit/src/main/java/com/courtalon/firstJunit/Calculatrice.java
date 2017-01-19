package com.courtalon.firstJunit;

public class Calculatrice {
	
	public int addition(int x, int y) {
		return x + y;
	}
	
	public int multiplication(int x, int y) {
		return x * y;
	}
	
	public int division(int x, int y) {
		return x / y;
	}
	
	public int calculTresLong(int x, int y) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
		return x + y;
	}
	
	public double calculRacine(double d) {
		return Math.sqrt(d);
	}
	
	
}
