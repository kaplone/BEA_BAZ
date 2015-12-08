package models;

import utils.MongoAccess;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auteur extends Commun{
	
	private String nom_complet;
	
	public static void update(Auteur c){

		MongoAccess.update("auteur", c);
	}
	
    public static void save(Auteur c){
		
		MongoAccess.save("auteur", c);
		
	}

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String nom_complet) {
		this.nom_complet = nom_complet;
	}
    
    

}
