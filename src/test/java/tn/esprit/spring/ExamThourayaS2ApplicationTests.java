package tn.esprit.spring;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import tn.esprit.spring.entities.Voyageur;

import tn.esprit.spring.services.IVoyageurService;

@SpringBootTest
class ExamThourayaS2ApplicationTests {
    @Autowired
    IVoyageurService ivoyageurservice;


	@Test
	public void testadd(){
	List<Voyageur> voyageurs= ivoyageurservice.recupererAll();
	int voy = voyageurs.size();
	
	Voyageur voyageur = new Voyageur();
	voyageur.setNomVoyageur("mouheb");
	
	 ivoyageurservice.ajouterVoyageur(voyageur);
	 
	 assertEquals(voy+1, ivoyageurservice.recupererAll().size());
	}
	
	@Test
	public void testDeleteVoyageur() {
		Voyageur v = new Voyageur();
		v.setNomVoyageur("ahmed");
		
		ivoyageurservice.ajouterVoyageur(v);
		ivoyageurservice.supprimerVoyageur(v);
		assertNull(ivoyageurservice.recupererVoyageParId(v.getIdVoyageur()));
	}


}

