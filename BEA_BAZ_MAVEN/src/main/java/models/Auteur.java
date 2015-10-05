package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auteur {
	
	@JsonProperty("_id") 
	@MongoObjectId
    private ObjectId _id;
	
	@JsonProperty("nom") 
	private String nom;
	
	private MongoObjectId adresse;
	
	private ArrayList<MongoObjectId> oeuvres;
	
	public Auteur(){
		super();
	}

	public Auteur(String nom) {
		super();
		this.nom = nom;
	}
	
	@JsonCreator
	public Auteur(@JsonProperty("_id")  ObjectId _id, @JsonProperty("nom") String nom) {
		super();
		this._id = _id;
		this.nom = nom;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public MongoObjectId getAdresse() {
		return adresse;
	}

	public void setAdresse(MongoObjectId adresse) {
		this.adresse = adresse;
	}

	public ArrayList<MongoObjectId> getOeuvres() {
		return oeuvres;
	}

	public void setOeuvres(ArrayList<MongoObjectId> oeuvres) {
		this.oeuvres = oeuvres;
	}
	
	

}
