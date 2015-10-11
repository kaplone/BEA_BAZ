package models;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Traitement extends Commun{

	private String nom_Complet;
	
	private ArrayList<Produit> produits;
	
	public static void update(Traitement t){

		MongoAccess.update("traitement", t);
	}
	
    public static void save(Traitement t){
		
		MongoAccess.save("traitement", t);
		
	}
    
    public static void insert(Traitement t){
		
		MongoAccess.insert("traitement", t);
		
	}

	public String getNom_complet() {
		return nom_Complet;
	}

	public void setNom_complet(String detail) {
		this.nom_Complet = detail;
	}

	public ArrayList<Produit> getProduits() {
		return produits;
	}

	public void setProduits(ArrayList<Produit> complements) {
		this.produits = complements;
	}
}
