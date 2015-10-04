package models;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Etat {
	
	@MongoObjectId
    private String _id;
	private ArrayList<String> alterations;
	private String oeuvre_id;
	
	public Etat(Oeuvre o) {
		
		oeuvre_id = o.get_id();
		alterations = new ArrayList<>();

	}
	
	public void addAlteration(String s){
		alterations.add(s);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public ArrayList<String> getAlterations() {
		return alterations;
	}

	public void setAlterations(ArrayList<String> alterations) {
		this.alterations = alterations;
	}

	public String getOeuvre_id() {
		return oeuvre_id;
	}

	public void setOeuvre_id(String oeuvre_id) {
		this.oeuvre_id = oeuvre_id;
	}
	
	

}
