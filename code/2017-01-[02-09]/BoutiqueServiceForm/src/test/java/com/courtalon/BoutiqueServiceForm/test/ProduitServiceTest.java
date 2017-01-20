package com.courtalon.BoutiqueServiceForm.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.courtalon.BoutiqueServiceForm.services.ProduitServiceMetier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:testContext.xml")
public class ProduitServiceTest {
	
	@Autowired
	private ProduitServiceMetier produitServiceMetier;
	

}
