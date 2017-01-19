#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.spring;

import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ${package}.beans.Message;

public class SpringApp {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
        Scanner input = new Scanner(System.in);
        

        input.nextLine();
        System.out.println("--------------------------------------");
        
        Message msg = (Message)ctx.getBean("msg1");
        
        System.out.println(msg);
        
        input.nextLine();
		System.out.println("--------------------------------------");

		System.out.println("done...");
	}





}
