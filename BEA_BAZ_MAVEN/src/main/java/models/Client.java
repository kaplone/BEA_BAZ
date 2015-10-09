package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends Commun{

	private String remarques;
	
	private ArrayList<ObjectId> commandes;

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

	public ArrayList<ObjectId> getCommandes() {
		return commandes;
	}

	public void setCommandes(ArrayList<ObjectId> commandes) {
		this.commandes = commandes;
	}
	
	
	

}
