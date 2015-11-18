package models;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import enums.Progression;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Oeuvre extends Commun{
	
	private String n_d_origine,
	               cote_archives,
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
	
	private ObjectId auteur;
	
	private ArrayList<Matiere> matieresUtilisees;
	private ArrayList<Technique> techniquesUtilisees;
	
	private String etat_current;
	
	@JsonIgnore
	private ImageView etat;
//	
//	public Oeuvre(){
//		matieresUtilisees = new ArrayList<>();
//		techniquesUtilisees = new ArrayList<>();
//	}
	
	@Override
	public String toString(){
		return this.cote_archives;
	}
	
	@Override
	public String getNom(){
		
		if(getCote_archives_6s().equals("SN")){
			return String.format("   SN - %s" , titre_de_l_oeuvre);
		}
		else {
			try {
				return String.format("%04d - %s", Integer.parseInt(getCote_archives_6s() != "" ? getCote_archives_6s() : "0") , titre_de_l_oeuvre);
			}
			catch (NumberFormatException nfe) {
				return String.format("%04d - %s", Integer.parseInt(getCote_archives_6s() != "" ? getCote_archives_6s().split("\\.")[0] : "0") , titre_de_l_oeuvre);
			}
		}
		
		
		
	}
	
	public static void update(Oeuvre c){

		MongoAccess.update("oeuvre", c);
	}
	
    public static void save(Oeuvre c){
		
		MongoAccess.save("oeuvre", c);
		
	}
    
    public void addMatiere(Matiere m){
    	
    	if (matieresUtilisees == null){
    		matieresUtilisees = new ArrayList<>();
    	}
    	
    	else if (! matieresUtilisees.contains(m)){
    		matieresUtilisees.add(m);
    	}
    	
    }
    
    public void deleteMatiere(Matiere m){
    	
    	Matiere matiere_ = null;
    	
    	for (Matiere m_ : matieresUtilisees){
    		if (m.getNom().equals(m_.getNom())){
    			matiere_ = m_;
    			matieresUtilisees.remove(m_);
    			break;
    		}
    	} 	
    }
    
    public void addTechnique(Technique t){
    	
    	if (techniquesUtilisees == null){
    		techniquesUtilisees = new ArrayList<>();
    	}
    	
    	else if (! techniquesUtilisees.contains(t)){
    		techniquesUtilisees.add(t);
    	}
    	
    }
    
    public void deleteTechnique(Technique t){
    	
    	Technique technique_ = null;
    	
    	for (Technique t_ : techniquesUtilisees){
    		if (t.getNom().equals(t_.getNom())){
    			technique_ = t_;
    			techniquesUtilisees.remove(t_);
    			break;
    		}
    	} 	
    }

	public String getN_d_origine() {
		return n_d_origine;
	}

	public void setN_d_origine(String n_d_origine) {
		this.n_d_origine = n_d_origine;
	}

	public String getCote_archives_6s() {
		return cote_archives_6s != null ? cote_archives_6s : "0";
	}

	public void setCote_archives_6s(String cote_archives_6s) {
		this.cote_archives_6s = cote_archives_6s;
	}

	public void setEtat(ImageView etat) {
		this.etat = etat;
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

	public ObjectId getAuteur() {
		return auteur;
	}

	public void setAuteur(ObjectId auteur) {
		this.auteur = auteur;
	}

	public String getEtat_current() {
		return etat_current;
	}

	public void setEtat_current(String etat_current) {
		this.etat_current = etat_current;
	}

	public ArrayList<Matiere> getMatieresUtilisees() {
		return matieresUtilisees;
	}

	public void setMatieresUtilisees(ArrayList<Matiere> matieresUtilisees) {
		this.matieresUtilisees = matieresUtilisees;
	}

	public ArrayList<Technique> getTechniquesUtilisees() {
		return techniquesUtilisees;
	}

	public void setTechniquesUtilisees(ArrayList<Technique> techniquesUtilisees) {
		this.techniquesUtilisees = techniquesUtilisees;
	}
	
	

	
}
