package com.courtalon.firstSpringJunitForm.spring;

import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.courtalon.firstSpringJunitForm.beans.Message;
import com.courtalon.firstSpringJunitForm.services.GazouilleService;

public class SpringApp {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
        Scanner input = new Scanner(System.in);
        

        System.out.println("--------------------------------------");
        
        System.out.println("saisissez un titre de gazouille");
        String titre = input.nextLine();
        System.out.println("saisissez le corps de la gazouille");
        String corps = input.nextLine();
        
        GazouilleService gs = ctx.getBean(GazouilleService.class);
        
        Message m = gs.publish(new Message(0, titre, corps));
        System.out.println("publication de " + m);
        
 		System.out.println("--------------------------------------");

		System.out.println("done...");
	}





}
