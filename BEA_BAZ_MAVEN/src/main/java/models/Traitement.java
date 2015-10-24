package models;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Traitement extends Commun{

	private String nom_complet;
	
	private ArrayList<Produit> produits;
	
	private ArrayList<Complement> complements;
	
	public Traitement(){
		this.produits = new ArrayList<>();
	}
	
	public static void update(Traitement t){

		MongoAccess.update("traitement", t);
	}
	
    public static void save(Traitement t){
		
		MongoAccess.save("traitement", t);
		
	}
    
    public static void insert(Traitement t){
		
		MongoAccess.insert("traitement", t);
		
	}
    
    public void addProduit(Produit p){
    	
    	if (! produits.contains(p)){
    		produits.add(p);
    	}
    	
    }
    
    public void deleteProduit(Produit p){
    	
    	Produit produit_ = null;
    	
    	for (Produit p_ : produits){
    		if (p.getNom().equals(p_.getNom())){
    			produit_ = p_;
    			break;
    		}
    	}
    	produits.remove(produit_);

		

    	
    }

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String detail) {
		this.nom_complet = detail;
	}

	public ArrayList<Produit> getProduits() {
		return produits;
	}

	public void setProduits(ArrayList<Produit> complements) {
		this.produits = complements;
	}
}
