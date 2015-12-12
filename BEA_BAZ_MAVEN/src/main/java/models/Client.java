package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;
import utils.MongoAccess;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends Commun{
    
	
	private String nom_complet;
	private String adresse_rue;
	private String adresse_cp;
	private String adresse_ville;
	private String remarques;

	private Map<String, ObjectId> commandes_id;
	
	public Client(){
		commandes_id = new HashMap<>();
	}

	public static void update(Client c){
		MongoAccess.update("client", c);
	}
	
    public static void save(Client c){	
		MongoAccess.save("client", c);	
	}
    
    public Client get(){
		return this;	
	}
    
    public String toString(){	
    	return this.getNom();
    }

	public String getRemarques() {
		return remarques;
	}

	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}

	public Set<String> getCommandes_names() {
		return commandes_id.keySet();
	}

	public void add_commande_id(Commande commande) {
		this.commandes_id.put(commande.getNom_affichage(), commande.get_id());
	}

	public String getAdresse_rue() {
		return adresse_rue;
	}

	public void setAdresse_rue(String adresse_rue) {
		this.adresse_rue = adresse_rue;
	}

	public String getAdresse_cp() {
		return adresse_cp;
	}

	public void setAdresse_cp(String adresse_cp) {
		this.adresse_cp = adresse_cp;
	}

	public String getAdresse_ville() {
		return adresse_ville;
	}

	public void setAdresse_ville(String adresse_ville) {
		this.adresse_ville = adresse_ville;
	}

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String nom_complet) {
		this.nom_complet = nom_complet;
	}

	public Map<String, ObjectId> getCommandes_id() {
		return commandes_id;
	}

	public void setCommandes_id(Map<String, ObjectId> commandes_id) {
		this.commandes_id = commandes_id;
	}
	
	
	

}
