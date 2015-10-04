package models;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {
	
	@JsonProperty("_id") 
	@MongoObjectId
    private String _id;
	
	@JsonProperty("nom")
	private String nom;
	
	private String remarques;
	
	private ArrayList<String> commandes;
	
	@JsonCreator
	public Client() {
		super () ;
	}
    
	//@JsonCreator
	public Client(String nom) {
		super () ;
		this.nom = nom;
	}
	
	//@JsonCreator
	public Client(@JsonProperty("nom") String nom_, @JsonProperty("remarques") String remarques ) {
		super (); 
		this.remarques = remarques;
		this.nom = nom_;
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

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getRemarques() {
		return remarques;
	}

	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}

	public ArrayList<String> getCommandes() {
		return commandes;
	}

	public void setCommandes(ArrayList<String> commandes) {
		this.commandes = commandes;
	}
	
	
	

}
