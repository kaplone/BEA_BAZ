package models;

import utils.MongoAccess;

public class Matiere extends Commun {
	
	private String nom_complet;
	
	public static void update(Matiere t){

		MongoAccess.update("matiere", t);
	}
	
    public static void save(Matiere t){
		
		MongoAccess.save("matiere", t);
		
	}
    
    public static void insert(Matiere t){
		
		MongoAccess.insert("matiere", t);
		
	}
	
	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String nom_complet) {
		this.nom_complet = nom_complet;
	}

}
