package models;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Produit extends Commun {
	
	public static void update(Produit t){

		MongoAccess.update("produit", t);
	}
	
    public static void save(Produit t){
		
		MongoAccess.save("produit", t);
		
	}
    
    public static void insert(Produit t){
		
		MongoAccess.insert("produit", t);
		
	}
	
	private String nom_complet;

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String detail) {
		this.nom_complet = detail;
	}
}
