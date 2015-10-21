package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import enums.EtatFinal;

public class Etat {
	
	private ArrayList<String> alterations;
	
	private ObjectId oeuvre_id,
	                 commande_id;
	
	private EtatFinal etatFinal;

	public ArrayList<String> getAlterations() {
		return alterations;
	}

	public void setAlterations(ArrayList<String> alterations) {
		this.alterations = alterations;
	}

	public ObjectId getOeuvre_id() {
		return oeuvre_id;
	}

	public void setOeuvre_id(ObjectId oeuvre_id) {
		this.oeuvre_id = oeuvre_id;
	}

	public ObjectId getCommande_id() {
		return commande_id;
	}

	public void setCommande_id(ObjectId commande_id) {
		this.commande_id = commande_id;
	}

	public EtatFinal getEtatFinal() {
		return etatFinal;
	}

	public void setEtatFinal(EtatFinal etatFinal) {
		this.etatFinal = etatFinal;
	}
	
	
	

}
