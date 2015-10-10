package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auteur extends Commun{
	
	private MongoObjectId adresse;
	
	private ArrayList<MongoObjectId> oeuvres;


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
