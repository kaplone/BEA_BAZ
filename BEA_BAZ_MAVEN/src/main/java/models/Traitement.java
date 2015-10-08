package models;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Traitement extends Commun{

	private String detail;
	
	private ArrayList<Complement> complements;
	
	public static void update(Traitement t){

		MongoAccess.update("traitement", t);
	}
	
    public static void save(Traitement t){
		
		MongoAccess.save("traitement", t);
		
	}
    
    public static void insert(Traitement t){
		
		MongoAccess.insert("traitement", t);
		
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public ArrayList<Complement> getComplements() {
		return complements;
	}

	public void setComplements(ArrayList<Complement> complements) {
		this.complements = complements;
	}
}
