package models;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Traitement extends Commun{
	
	@JsonProperty("_id") 
	@MongoObjectId
    private String _id;
	
	private String nom;
	
	private String detail;
	
	private ArrayList<Complement> complements;

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
	
	public String toString(){
		return this.nom;
	}

}
