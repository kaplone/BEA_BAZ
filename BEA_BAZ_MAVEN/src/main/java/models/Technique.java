package models;

import utils.MongoAccess;

public class Technique extends Commun{
	
	private String nom_complet;
	
	public static void update(Technique t){

		MongoAccess.update("technique", t);
	}
	
    public static void save(Technique t){
		
		MongoAccess.save("technique", t);
		
	}
    
    public static void insert(Technique t){
		
		MongoAccess.insert("technique", t);
		
	}

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String nom_complet) {
		this.nom_complet = nom_complet;
	}
	
	

}
