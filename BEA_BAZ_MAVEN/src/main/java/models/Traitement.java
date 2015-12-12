package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;

import utils.MongoAccess;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Traitement extends Commun{

	private String nom_complet;
	
	private Map<String, ObjectId> produits;
	
	public Traitement(){
		this.produits = new HashMap<>();
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
    	
    	if (! produits.keySet().contains(p.getNom())){
    		produits.put(p.getNom(), p.get_id());
    	}
    	
    }
    
    public void addProduit(String p, ObjectId id){
    	
    	if (! produits.keySet().contains(p)){
    		produits.put(p, id);
    	}
    	
    }
    
    public void deleteProduit(Produit p){
    	
    	for (String p_ : produits.keySet()){
    		if (p.getNom().equals(p_)){
    			produits.remove(p_);
    			break;
    		}
    	} 	
    }

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String detail) {
		this.nom_complet = detail;
	}

	public Set<String> getProduits_names() {
		return produits.keySet();
	}

	public Map<String, ObjectId> getProduits() {
		return produits;
	}

	public void setProduits(Map<String, ObjectId> produits) {
		this.produits = produits;
	}
	
}
