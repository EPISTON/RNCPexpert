package com.courtalon.firstJunit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        Calculatrice c = new Calculatrice();
        
        System.out.println(c.addition(10, 15));
        
        System.out.println(c.division(10, 4));
    }
}
