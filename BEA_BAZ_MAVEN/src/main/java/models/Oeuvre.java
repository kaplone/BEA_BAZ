package models;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Oeuvre {
	
	@JsonProperty("_id") 
	@MongoObjectId
    private String _id;
	
	private String n_d_origine,
	               cote_archives_6s,
	               ville,
	               quartier,
	               titre_de_l_oeuvre,
	               date, 
	               dimensions,
	               _observations,
	               field_25,
	               inscriptions_au_verso,
	               format_de_conditionnement;
	
	private String auteur,
	               client;
	
	private ArrayList<MongoObjectId> etats,
                                     jobs,
                                     traitements,
                                     fichiers,
                                     rapports;

	public String get_id() {
		return _id;
	}

	public void set_id( String _id) {
		this._id = _id;
	}

	public String getN_d_origine() {
		return n_d_origine;
	}

	public void setN_d_origine(String n_d_origine) {
		this.n_d_origine = n_d_origine;
	}

	public String getCote_archives_6s() {
		return cote_archives_6s;
	}

	public void setCote_archives_6s(String cote_archives_6s) {
		this.cote_archives_6s = cote_archives_6s;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getTitre_de_l_oeuvre() {
		return titre_de_l_oeuvre;
	}

	public void setTitre_de_l_oeuvre(String titre_de_l_oeuvre) {
		this.titre_de_l_oeuvre = titre_de_l_oeuvre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String get_observations() {
		return _observations;
	}

	public void set_observations(String _observations) {
		this._observations = _observations;
	}

	public String getField_25() {
		return field_25;
	}

	public void setField_25(String field_25) {
		this.field_25 = field_25;
	}

	public String getInscriptions_au_verso() {
		return inscriptions_au_verso;
	}

	public void setInscriptions_au_verso(String inscriptions_au_verso) {
		this.inscriptions_au_verso = inscriptions_au_verso;
	}

	public String getFormat_de_conditionnement() {
		return format_de_conditionnement;
	}

	public void setFormat_de_conditionnement(String format_de_conditionnement) {
		this.format_de_conditionnement = format_de_conditionnement;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public ArrayList<MongoObjectId> getEtats() {
		return etats;
	}

	public void setEtats(ArrayList<MongoObjectId> etats) {
		this.etats = etats;
	}

	public ArrayList<MongoObjectId> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<MongoObjectId> jobs) {
		this.jobs = jobs;
	}

	public ArrayList<MongoObjectId> getTraitements() {
		return traitements;
	}

	public void setTraitements(ArrayList<MongoObjectId> traitements) {
		this.traitements = traitements;
	}

	public ArrayList<MongoObjectId> getFichiers() {
		return fichiers;
	}

	public void setFichiers(ArrayList<MongoObjectId> fichiers) {
		this.fichiers = fichiers;
	}

	public ArrayList<MongoObjectId> getRapports() {
		return rapports;
	}

	public void setRapports(ArrayList<MongoObjectId> rapports) {
		this.rapports = rapports;
	}
	
	
	
	                      
	                   

}
